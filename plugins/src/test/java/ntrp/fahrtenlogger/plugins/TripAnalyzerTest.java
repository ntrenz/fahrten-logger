package ntrp.fahrtenlogger.plugins;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripAnalyzerTest {

    @Test
    void getTotalDistance() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, null, null, new Kilometer(45.2), null));
        trips.add(new Trip(2, null, null, new Kilometer(89.3), null));

        TripAnalyzer tripAnalyzer = new TripAnalyzer(trips);

        assertEquals(new Kilometer(45.2+89.3), tripAnalyzer.getTotalDistance(trips));
        assertEquals(new Kilometer(0), tripAnalyzer.getTotalDistance(new ArrayList<>()));
    }

    @Test
    void getAnalysis() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, new Place("Karlsruhe"), new Place("Speyer"), new Kilometer(45.2), LocalDate.of(2024, 2, 23)));
        trips.add(new Trip(2, new Place("Geislitz"), new Place("Hamburg"), new Kilometer(89.3), LocalDate.of(2023, 9, 3)));
        trips.add(new Trip(3, new Place("Mannheim"), new Place("Speyer"), new Kilometer(12.2), LocalDate.of(2023, 11, 6)));
        trips.add(new Trip(4, new Place("Hamburg"), new Place("Mannheim"), new Kilometer(134.5), LocalDate.of(2023, 12, 9)));
        trips.add(new Trip(5, new Place("Speyer"), new Place("Hamburg"), new Kilometer(78.5), LocalDate.of(2024, 4, 12)));
        trips.add(new Trip(6, new Place("Karlsruhe"), new Place("Geislitz"), new Kilometer(55.5), LocalDate.of(2024, 1, 14)));
        trips.add(new Trip(6, new Place("Geislitz"), new Place("Köln"), new Kilometer(55.5), LocalDate.of(2024, 1, 14)));

        TripAnalyzer tripAnalyzer = new TripAnalyzer(trips);

        String query1 = tripAnalyzer.getAnalysis(new Place("Karlsruhe"), null, null);
        String query2 = tripAnalyzer.getAnalysis(new Place("Karlsruhe"), null, LocalDate.of(2024, 2, 23));
        String query3 = tripAnalyzer.getAnalysis(null, new Place("Hamburg"), null);
        String query4 = tripAnalyzer.getAnalysis(null, new Place("Hamburg"), LocalDate.of(2024, 4, 12));
        String query5 = tripAnalyzer.getAnalysis(new Place("Speyer"), new Place("Hamburg"), null);
        String query6 = tripAnalyzer.getAnalysis(new Place("Speyer"), new Place("Hamburg"), LocalDate.of(2024, 2, 23));
        String query7 = tripAnalyzer.getAnalysis(null,null, LocalDate.of(2024, 1, 14));

        assertEquals("\u001B[1m\u001B[38;2;255;200;0m2\u001B[0m\u001B[0m trips recorded from \u001B[1m\u001B[38;2;255;200;0mKarlsruhe\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m100,70 km\u001B[0m\u001B[0m", query1);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m1\u001B[0m\u001B[0m trip recorded from \u001B[1m\u001B[38;2;255;200;0mKarlsruhe\u001B[0m\u001B[0m on \u001B[1m\u001B[38;2;255;200;0m23.02.2024\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m45,20 km\u001B[0m\u001B[0m", query2);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m2\u001B[0m\u001B[0m trips recorded to \u001B[1m\u001B[38;2;255;200;0mHamburg\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m167,80 km\u001B[0m\u001B[0m", query3);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m1\u001B[0m\u001B[0m trip recorded to \u001B[1m\u001B[38;2;255;200;0mHamburg\u001B[0m\u001B[0m on \u001B[1m\u001B[38;2;255;200;0m12.04.2024\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m78,50 km\u001B[0m\u001B[0m", query4);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m1\u001B[0m\u001B[0m trip recorded from \u001B[1m\u001B[38;2;255;200;0mSpeyer\u001B[0m\u001B[0m to \u001B[1m\u001B[38;2;255;200;0mHamburg\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m78,50 km\u001B[0m\u001B[0m", query5);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m0\u001B[0m\u001B[0m trips recorded from \u001B[1m\u001B[38;2;255;200;0mSpeyer\u001B[0m\u001B[0m to \u001B[1m\u001B[38;2;255;200;0mHamburg\u001B[0m\u001B[0m on \u001B[1m\u001B[38;2;255;200;0m23.02.2024\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m0,00 km\u001B[0m\u001B[0m", query6);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m2\u001B[0m\u001B[0m trips recorded on \u001B[1m\u001B[38;2;255;200;0m14.01.2024\u001B[0m\u001B[0m with a total distance of \u001B[1m\u001B[38;2;255;200;0m111,00 km\u001B[0m\u001B[0m", query7);
    }
}