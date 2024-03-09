package ntrp.fahrtenlogger.adapters.interpreter;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.RefuelRepository;
import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class RefuelInterpreterTest {
    // Test Cases:
    //
    // - Not enough arguments
    // - NEW
    // - MODIFY
    // - DELETE
    // - READ
    // - Unknown Action

    @Mock
    DataHandlerInterface mockedDataHandler;
    @Mock
    RefuelRepository refuelRepository;
    @Mock
    List<Refuel> mockList;
    @Mock(name="action")
    Actions action;

    @InjectMocks
    RefuelInterpreter refuelInterpreter;

    @Before
    void testSetup() {
        Mockito.when(RefuelRepository.getInstance(mockedDataHandler)).thenReturn(refuelRepository);
    }

    @BeforeEach
    void methodSetup() {
        this.mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
    }

    @AfterEach
    void methodTeardown() {
        refuelInterpreter = null;
        mockList = null;
    }

    @Test
    void parseCommandWithNotEnoughArguments() {
        List<String> commandsList = new ArrayList<>();
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Not enough Parameters!", exception.getMessage());
    }

    @Test
    void parseCommandNew() {
        Actions action = Actions.NEW;
        Liter liter = new Liter(50);
        Euro pricePerLiter = new Euro(1.7);
        
        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add(liter.toString());
        commandsList.add(pricePerLiter.toString());

        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();

        assertEquals(action, refuelInterpreter.action);
        assertEquals(liter, refuelInterpreter.getLiters());
        assertEquals(pricePerLiter, refuelInterpreter.getPricePerLiter());
    }

    @Test
    void parseCommandNewToFewArguments() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("new");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Nicht genügend Parameter!", exception.getMessage());
    }

    @Test
    void parseCommandModify() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("modify");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Unimplemented method 'parseModifyCommands'", exception.getMessage());
    }

    @Test
    void parseCommandDelete() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("delete");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();

        assertEquals(Actions.DELETE, refuelInterpreter.action);
        assertNull(refuelInterpreter.getDate());
        assertEquals(0, refuelInterpreter.getId());
    }

    @Test
    void parseCommandDeleteWithDateAndId() {
        String dateString = "01.03.2024";
        int id = 1;
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));

        List<String> commandsList = new ArrayList<>();
        commandsList.add("delete");
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-id");
        commandsList.add(String.valueOf(id));
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();

        assertEquals(Actions.DELETE, refuelInterpreter.action);
        assertEquals(date, refuelInterpreter.getDate());
        assertEquals(id, refuelInterpreter.getId());
    }

    @Test
    void parseCommandRead() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("read");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();

        assertEquals(Actions.READ, refuelInterpreter.action);
        assertNull(refuelInterpreter.getDate());
        assertEquals(0, refuelInterpreter.getId());
    }

    @Test
    void parseCommandReadWithArgs() {
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        FuelType fuelType = FuelType.E5;
        GasStation gasStation = new GasStation("Tanke");

        List<String> commandsList = new ArrayList<>();
        commandsList.add("read");
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-ft");
        commandsList.add(fuelType.toString());
        commandsList.add("-gs");
        commandsList.add(gasStation.getName());
        refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();
        
        assertEquals(Actions.READ, refuelInterpreter.action);
        assertEquals(date, refuelInterpreter.getDate());
        assertEquals(fuelType, refuelInterpreter.getFuelType());
        assertEquals(gasStation, refuelInterpreter.getGasStation());
        assertEquals(0, refuelInterpreter.getId());
    }

    @Test
    void parseCommandReadWithWrongFT() {
        String wrongFuelType = "DISEL";

        List<String> commandsList = new ArrayList<>();
        commandsList.add("read");
        commandsList.add("-ft");
        commandsList.add(wrongFuelType);
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Fuel Type is not defined!", exception.getMessage());
    }

    @Test
    void parseCommandUnknownAction() {
        String action = "UNKNOWN";
        List<String> commandsList = new ArrayList<>();
        commandsList.add(action);
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Action nicht definiert: " + action, exception.getMessage());
    }


    // read
    // new
    // delete
    // modify

    @Test
    void executeNewCommand() {       
        this.action = Mockito.mock(Actions.class);
        Mockito.when(action.ordinal()).thenReturn(Actions.NEW.ordinal());
        
        Refuel refuel = Mockito.mock(Refuel.class);
        Mockito.doNothing().when(refuelRepository).writeRefuel(refuel);

        refuelInterpreter = new RefuelInterpreter(new ArrayList<String>(), mockedDataHandler);
        refuelInterpreter.executeCommands();
        
        verify(refuelRepository, times(1)).writeRefuel(refuel);
        
        // mockList.add("one");
        // Mockito.verify(mockList).add("one");
        // assertEquals(0, mockList.size());

        // Mockito.when(mockList.size()).thenReturn(100);
        // assertEquals(100, mockList.size());
    }

    @Test
    void executeReadCommand() {
        mockList.add(new Refuel(0, null, null, null, null, null));
        // Mockito.when(mockedRefuelRepository.readRefuels(null, null).thenReturn(mockList));
    }

    @Test
    void executeDeleteCommand() {}

    @Test
    void executeModifyCommand() {}
}