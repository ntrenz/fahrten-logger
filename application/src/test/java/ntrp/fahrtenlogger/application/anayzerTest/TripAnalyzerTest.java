package ntrp.fahrtenlogger.application.anayzerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ntrp.fahrtenlogger.application.analyzer.PrintInterface;
import ntrp.fahrtenlogger.application.analyzer.TripAnalyzer;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

@ExtendWith(MockitoExtension.class)
public class TripAnalyzerTest {
    @Mock( name = "print" )
    static PrintInterface printMock;

    @InjectMocks
    TripAnalyzer tripAnalyzer;

    @BeforeAll
    static void testSetup() {
        printMock = Mockito.mock(PrintInterface.class);
    }

    @BeforeEach
    void methodSetup() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @AfterEach
    void methodTeardown() {
        reset(printMock);
        tripAnalyzer = null;
    }

    @Test
    void getTotalDistance() {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, null, null, new Kilometer(45.2), null));
        trips.add(new Trip(2, null, null, new Kilometer(89.3), null));

        tripAnalyzer = new TripAnalyzer(trips, printMock);

        assertEquals(new Kilometer(45.2+89.3), tripAnalyzer.getTotalDistance(trips));
        assertEquals(new Kilometer(0), tripAnalyzer.getTotalDistance(new ArrayList<>()));
    }

    @Test
    void getAnalysis() {
        // Mock Setup
        Mockito.when(printMock.bold(anyString())).thenAnswer(invocation -> (String) invocation.getArguments()[0]);
        Mockito.when(printMock.color(anyString(), any())).thenAnswer(invocation -> (String) invocation.getArguments()[0]);

        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(1, new Place("Karlsruhe"), new Place("Speyer"), new Kilometer(45.2), LocalDate.of(2024, 2, 23)));
        trips.add(new Trip(2, new Place("Geislitz"), new Place("Hamburg"), new Kilometer(89.3), LocalDate.of(2023, 9, 3)));
        trips.add(new Trip(3, new Place("Mannheim"), new Place("Speyer"), new Kilometer(12.2), LocalDate.of(2023, 11, 6)));
        trips.add(new Trip(4, new Place("Hamburg"), new Place("Mannheim"), new Kilometer(134.5), LocalDate.of(2023, 12, 9)));
        trips.add(new Trip(5, new Place("Speyer"), new Place("Hamburg"), new Kilometer(78.5), LocalDate.of(2024, 4, 12)));
        trips.add(new Trip(6, new Place("Karlsruhe"), new Place("Geislitz"), new Kilometer(55.5), LocalDate.of(2024, 1, 14)));
        trips.add(new Trip(6, new Place("Geislitz"), new Place("Köln"), new Kilometer(55.5), LocalDate.of(2024, 1, 14)));

        tripAnalyzer = new TripAnalyzer(trips, printMock);

        String query1 = tripAnalyzer.getAnalysis(new Place("Karlsruhe"), null, null);
        String query2 = tripAnalyzer.getAnalysis(new Place("Karlsruhe"), null, LocalDate.of(2024, 2, 23));
        String query3 = tripAnalyzer.getAnalysis(null, new Place("Hamburg"), null);
        String query4 = tripAnalyzer.getAnalysis(null, new Place("Hamburg"), LocalDate.of(2024, 4, 12));
        String query5 = tripAnalyzer.getAnalysis(new Place("Speyer"), new Place("Hamburg"), null);
        String query6 = tripAnalyzer.getAnalysis(new Place("Speyer"), new Place("Hamburg"), LocalDate.of(2024, 2, 23));
        String query7 = tripAnalyzer.getAnalysis(null,null, LocalDate.of(2024, 1, 14));

        assertEquals("2 trips recorded from Karlsruhe with a total distance of 100,70 km", query1);
        assertEquals("1 trip recorded from Karlsruhe on 23.02.2024 with a total distance of 45,20 km", query2);
        assertEquals("2 trips recorded to Hamburg with a total distance of 167,80 km", query3);
        assertEquals("1 trip recorded to Hamburg on 12.04.2024 with a total distance of 78,50 km", query4);
        assertEquals("1 trip recorded from Speyer to Hamburg with a total distance of 78,50 km", query5);
        assertEquals("0 trips recorded from Speyer to Hamburg on 23.02.2024 with a total distance of 0,00 km", query6);
        assertEquals("2 trips recorded on 14.01.2024 with a total distance of 111,00 km", query7);
    }
}