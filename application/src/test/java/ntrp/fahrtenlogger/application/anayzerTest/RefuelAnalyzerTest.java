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
import ntrp.fahrtenlogger.application.analyzer.RefuelAnalyzer;
import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

@ExtendWith(MockitoExtension.class)
class RefuelAnalyzerTest {
    @Mock( name = "print" )
    static PrintInterface printMock;

    @InjectMocks
    RefuelAnalyzer refuelAnalyzer;

    @BeforeAll
    static void testSetup() {
        printMock = Mockito.mock(PrintInterface.class);
    }

    @BeforeEach
    void methodSetup() throws Exception {
        MockitoAnnotations.openMocks(this); //.close();
    }

    @AfterEach
    void methodTeardown() {
        reset(printMock);
        refuelAnalyzer = null;
    }

    @Test
    void getTotalVolume() {
        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));

        refuelAnalyzer = new RefuelAnalyzer(refuels, printMock);

        assertEquals(new Liter(23.4+56.4), refuelAnalyzer.getTotalVolume(refuels));
        assertEquals(new Liter(0), refuelAnalyzer.getTotalVolume(new ArrayList<>()));
    }

    @Test
    void getAveragePricePerLiter() {
        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));

        refuelAnalyzer = new RefuelAnalyzer(refuels, printMock);

        assertEquals(new Euro((1.68+1.75)/2), refuelAnalyzer.getAveragePricePerLiter(refuels));
        assertEquals(new Euro(0), refuelAnalyzer.getAveragePricePerLiter(new ArrayList<>()));
    }

    @Test
    void getAnalysis() {
        // Mock Setup
        Mockito.when(printMock.bold(anyString())).thenAnswer(invocation -> (String) invocation.getArguments()[0]);
        Mockito.when(printMock.color(anyString(), any())).thenAnswer(invocation -> (String) invocation.getArguments()[0]);

        List<Refuel> refuels = new ArrayList<>();
        refuels.add(new Refuel(1, new Liter(23.4), new Euro(1.68), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23)));
        refuels.add(new Refuel(2, new Liter(56.4), new Euro(1.75), FuelType.E10, new GasStation("Mannheim"), LocalDate.of(2023, 12, 12)));
        refuels.add(new Refuel(3, new Liter(45.2), new Euro(1.82), FuelType.E10, new GasStation("Speyer"), LocalDate.of(2024, 3, 11)));
        refuels.add(new Refuel(4, new Liter(77.1), new Euro(1.66), FuelType.E5, new GasStation("Karlsruhe"), LocalDate.of(2023, 1, 4)));
        refuels.add(new Refuel(5, new Liter(24.9), new Euro(1.77), FuelType.DIESEL, new GasStation("Mannheim"), LocalDate.of(2023, 11, 23)));
        refuels.add(new Refuel(6, new Liter(65.2), new Euro(1.67), FuelType.E5, new GasStation("Hamburg"), LocalDate.of(2024, 1, 7)));
        refuels.add(new Refuel(7, new Liter(33.3), new Euro(1.71), FuelType.DIESEL, new GasStation("Mannheim"), LocalDate.of(2024, 1, 7)));

        refuelAnalyzer = new RefuelAnalyzer(refuels, printMock);

        String query1 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), null);
        String query2 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), LocalDate.of(2024, 2, 23));
        String query3 = refuelAnalyzer.getAnalysis(new GasStation("Karlsruhe"), LocalDate.of(2027, 1, 1));
        String query4 = refuelAnalyzer.getAnalysis(null, LocalDate.of(2024, 1, 7));
        String query5 = refuelAnalyzer.getAnalysis(null, null);

        assertEquals("2 refuels recorded at a gas station in Karlsruhe with a total volume of 100,50 l at an average price of 1,67 €/l", query1);
        assertEquals("1 refuel recorded at a gas station in Karlsruhe on 23.02.2024 with a total volume of 23,40 l at an average price of 1,68 €/l", query2);
        assertEquals("0 refuels recorded at a gas station in Karlsruhe on 01.01.2027 with a total volume of 0,00 l at an average price of 0,00 €/l", query3);
        assertEquals("2 refuels recorded on 07.01.2024 with a total volume of 98,50 l at an average price of 1,69 €/l", query4);
        assertEquals("7 refuels recorded with a total volume of 325,50 l at an average price of 1,72 €/l", query5);
    }
}