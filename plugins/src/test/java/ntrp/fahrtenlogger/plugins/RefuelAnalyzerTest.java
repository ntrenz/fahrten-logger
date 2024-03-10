package ntrp.fahrtenlogger.plugins;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefuelAnalyzerTest {

    @Test
    void getTotalVolume() {
        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));

        RefuelAnalyzer refuelAnalyzer = new RefuelAnalyzer(refuels);

        assertEquals(new Liter(23.4+56.4), refuelAnalyzer.getTotalVolume(refuels));
        assertEquals(new Liter(0), refuelAnalyzer.getTotalVolume(new ArrayList<>()));
    }

    @Test
    void getAveragePricePerLiter() {
        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));

        RefuelAnalyzer refuelAnalyzer = new RefuelAnalyzer(refuels);

        assertEquals(new Euro((1.68+1.75)/2), refuelAnalyzer.getAveragePricePerLiter(refuels));
        assertEquals(new Euro(0), refuelAnalyzer.getAveragePricePerLiter(new ArrayList<>()));
    }

    @Test
    void getAnalysis() {
        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));
        refuels.add(new Refuel(3, new Liter(45.2), new Euro(1.82), FuelType.E10, new GasStation("Speyer"), LocalDate.of(2024, 3, 11)));
        refuels.add(new Refuel(4, new Liter(77.1), new Euro(1.66), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2023, 1, 4)));
        refuels.add(new Refuel(5, new Liter(24.9), new Euro(1.77), FuelType.DIESEL, new GasStation("Mannheim"), LocalDate.of(2023, 11, 23)));
        refuels.add(new Refuel(6, new Liter(65.2), new Euro(1.67), FuelType.E5, new GasStation("Hamburg"), LocalDate.of(2024, 1, 7)));
        refuels.add(new Refuel(7, new Liter(33.3), new Euro(1.71), FuelType.DIESEL, new GasStation("Mannheim"), LocalDate.of(2024, 1, 7)));

        RefuelAnalyzer refuelAnalyzer = new RefuelAnalyzer(refuels);

        String query1 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), null);
        String query2 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23));
        String query3 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), LocalDate.of(2027, 1, 1));
        String query4 = refuelAnalyzer.getAnalysis(null, LocalDate.of(2024, 1, 7));
        String query5 = refuelAnalyzer.getAnalysis(null, null);

        assertEquals("\u001B[1m\u001B[38;2;255;200;0m2\u001B[0m\u001B[0m refuels recorded at a gas station in \u001B[1m\u001B[38;2;255;200;0mKarlsruhe\u001B[0m\u001B[0m with a total volume of \u001B[1m\u001B[38;2;255;200;0m100,50 l\u001B[0m\u001B[0m at an average price of \u001B[1m\u001B[38;2;255;200;0m1,67 €/l\u001B[0m\u001B[0m", query1);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m1\u001B[0m\u001B[0m refuel recorded at a gas station in \u001B[1m\u001B[38;2;255;200;0mKarlsruhe\u001B[0m\u001B[0m on \u001B[1m\u001B[38;2;255;200;0m23.02.2024\u001B[0m\u001B[0m with a total volume of \u001B[1m\u001B[38;2;255;200;0m23,40 l\u001B[0m\u001B[0m at an average price of \u001B[1m\u001B[38;2;255;200;0m1,68 €/l\u001B[0m\u001B[0m", query2);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m0\u001B[0m\u001B[0m refuels recorded at a gas station in \u001B[1m\u001B[38;2;255;200;0mKarlsruhe\u001B[0m\u001B[0m on \u001B[1m\u001B[38;2;255;200;0m01.01.2027\u001B[0m\u001B[0m with a total volume of \u001B[1m\u001B[38;2;255;200;0m0,00 l\u001B[0m\u001B[0m at an average price of \u001B[1m\u001B[38;2;255;200;0m0,00 €/l\u001B[0m\u001B[0m", query3);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m2\u001B[0m\u001B[0m refuels recorded on \u001B[1m\u001B[38;2;255;200;0m07.01.2024\u001B[0m\u001B[0m with a total volume of \u001B[1m\u001B[38;2;255;200;0m98,50 l\u001B[0m\u001B[0m at an average price of \u001B[1m\u001B[38;2;255;200;0m1,69 €/l\u001B[0m\u001B[0m", query4);
        assertEquals("\u001B[1m\u001B[38;2;255;200;0m7\u001B[0m\u001B[0m refuels recorded with a total volume of \u001B[1m\u001B[38;2;255;200;0m325,50 l\u001B[0m\u001B[0m at an average price of \u001B[1m\u001B[38;2;255;200;0m1,72 €/l\u001B[0m\u001B[0m", query5);
    }
}