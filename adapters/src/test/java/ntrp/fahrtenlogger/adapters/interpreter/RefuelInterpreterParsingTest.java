package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

class RefuelInterpreterParsingTest {
    @Mock(name = "dataHandler")
    static DataHandlerInterface mockedDataHandler;
    @Mock(name = "refuelRepository")
    static RefuelRepository mockedRefuelRepository;
    @Mock
    static List<Refuel> mockList;
    @Mock(name = "action")
    static Actions mockedAction;

    @InjectMocks
    RefuelInterpreter refuelInterpreter;

    @BeforeAll
    static void testSetup() {
        mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
        Mockito.when(mockedDataHandler.readAllRefuels()).thenReturn(new ArrayList<Refuel>());
        Mockito.when(RefuelRepository.getInstance(mockedDataHandler)).thenReturn(mockedRefuelRepository);
    }

    @BeforeEach
    void methodSetup() {
        
    }

    @AfterEach
    void methodTeardown() {
        reset(mockedDataHandler);
        refuelInterpreter = null;
        mockList = null;
    }

    @Test
    void parseCommandWithNotEnoughArguments() {
        List<String> commandsList = new ArrayList<>();
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

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

        assertEquals(action, refuelInterpreter.getAction());
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

        assertEquals(Actions.DELETE, refuelInterpreter.getAction());
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

        assertEquals(Actions.DELETE, refuelInterpreter.getAction());
        assertEquals(date, refuelInterpreter.getDate());
        assertEquals(id, refuelInterpreter.getId());
    }

    @Test
    void parseCommandRead() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("read");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, mockedDataHandler);

        refuelInterpreter.parseCommands();

        assertEquals(Actions.READ, refuelInterpreter.getAction());
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
        
        assertEquals(Actions.READ, refuelInterpreter.getAction());
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
}