package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.TripRepository;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

class TripInterpreterParsingTest {
    @Mock(name = "dataHandler")
    static DataHandlerInterface mockedDataHandler;
    @Mock(name = "tripRepository")
    static TripRepository mockedTripRepository;
    @Mock
    static List<Trip> mockList;
    @Mock(name = "action")
    static Actions mockedAction;
    public static MockedStatic<ArgumentsParser> mockedStaticArgumentsParser;

    @InjectMocks
    TripInterpreter tripInterpreter;

    @BeforeAll
    static void testSetup() {
        mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
        Mockito.when(mockedDataHandler.readAllTrips()).thenReturn(new ArrayList<Trip>());
        try (MockedStatic<TripRepository> mockedStaticTripRepository = mockStatic(TripRepository.class)) {
            mockedStaticTripRepository.when(() -> TripRepository.getInstance(mockedDataHandler)).thenReturn(mockedTripRepository);
        }
        mockedStaticArgumentsParser = mockStatic(ArgumentsParser.class);
    }

    @AfterAll
    static void testTeardown() {
        mockedStaticArgumentsParser.close();
    }

    @BeforeEach
    void methodSetup() {
        // mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
    }

    @AfterEach
    void methodTeardown() {
        reset(mockedDataHandler);
        tripInterpreter = null;
        mockList = null;
    }

    @Test
    void parseCommandWithNotEnoughArgs() {
        List<String> commandsList = new ArrayList<>();
        mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
        when(mockedDataHandler.readAllTrips()).thenReturn(new ArrayList<>());
        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Not enough Parameters!", exception.getMessage());
    }

    @Test
    void parseCommandNew() {
        Actions action = Actions.NEW;
        Place fromPlace = new Place("FROM_PLACE");
        Place toPlace = new Place("TO_PLACE");

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(fromPlace.toString())).thenReturn(fromPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(toPlace.toString())).thenReturn(toPlace);

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add(fromPlace.toString());
        commandsList.add(toPlace.toString());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.getAction());
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
    }

    @Test
    void parseCommandNewWithOptionalArgs() {
        Actions action = Actions.NEW;
        Place fromPlace = new Place("FROM_PLACE");
        Place toPlace = new Place("TO_PLACE");
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        Kilometer distance = new Kilometer(5);

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(fromPlace.toString())).thenReturn(fromPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(toPlace.toString())).thenReturn(toPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenReturn(date);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseKilometerFrom(distance.toString())).thenReturn(distance);

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add(fromPlace.toString());
        commandsList.add(toPlace.toString());
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-di");
        commandsList.add(distance.toString());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.getAction());
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
        assertEquals(date, tripInterpreter.getDate());
        assertEquals(distance, tripInterpreter.getDistance());
    }

    @Test
    void parseCommandNewToFewArgs() {
        Actions action = Actions.NEW;

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });

        assertEquals("Nicht genügend Parameter!", exception.getMessage());
    }

    @Test
    void parseCommandModify() {
        Actions action = Actions.MODIFY;

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Unimplemented method 'parseModifyCommands'", exception.getMessage());
    }

    @Test
    void parseCommandDelete() {
        Actions action = Actions.DELETE;

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.getAction());
        assertNull(tripInterpreter.getDate());
        assertEquals(0, tripInterpreter.getId());
    }

    @Test
    void parseCommandDeleteWithArgs() {
        Actions action = Actions.DELETE;
        Place fromPlace = new Place("FROM_PLACE");
        Place toPlace = new Place("TO_PLACE");
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        int id = 1;

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(fromPlace.toString())).thenReturn(fromPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(toPlace.toString())).thenReturn(toPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenReturn(date);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseIdFrom(String.valueOf(id))).thenReturn(id);

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-fp");
        commandsList.add(fromPlace.toString());
        commandsList.add("-tp");
        commandsList.add(toPlace.toString());
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-id");
        commandsList.add(String.valueOf(id));

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.getAction());
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
        assertEquals(date, tripInterpreter.getDate());
        assertEquals(id, tripInterpreter.getId());
    }

    @Test
    void parseCommandDeleteWithInvalidId() {
        Actions action = Actions.DELETE;
        String id = "ID";

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseIdFrom(String.valueOf(id))).thenThrow(new IllegalArgumentException("ID must be an integer!"));

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-id");
        commandsList.add(id);

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("ID must be an integer!", exception.getMessage());
    }

    @Test
    void parseCommandDeleteWithInvalidDate() {
        Actions action = Actions.DELETE;
        String dateString = "01-03-2024";

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenThrow(new IllegalArgumentException("Date could not be parsed! (Date format must be: DD.MM.YYYY)"));

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-d");
        commandsList.add(dateString);

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Date could not be parsed! (Date format must be: DD.MM.YYYY)", exception.getMessage());
    }

    @Test
    void parseCommandRead() {
        Actions action = Actions.READ;
        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();
        assertEquals(action, tripInterpreter.getAction());
        assertNull(tripInterpreter.getDate());
        assertEquals(0, tripInterpreter.getId());
    }

    @Test
    void parseCommandReadWithArgs() {
        Actions action = Actions.READ;
        Place fromPlace = new Place("FROM_PLACE");
        Place toPlace = new Place("TO_PLACE");
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        Kilometer distance = new Kilometer(5);

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(fromPlace.toString())).thenReturn(fromPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(toPlace.toString())).thenReturn(toPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenReturn(date);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseKilometerFrom(distance.toString())).thenReturn(distance);

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-fp");
        commandsList.add(fromPlace.toString());
        commandsList.add("-tp");
        commandsList.add(toPlace.toString());
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-di");
        commandsList.add(distance.toString());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();
        assertEquals(action, tripInterpreter.getAction());
        assertEquals(0, tripInterpreter.getId());
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
        assertEquals(date, tripInterpreter.getDate());
        assertEquals(distance, tripInterpreter.getDistance());
    }

    @Test
    void parseCommandReadWithWrongDateFormat() {
        Actions action = Actions.READ;
        String dateString = "01-03-2024";

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenThrow(new IllegalArgumentException("Date could not be parsed! (Date format must be: DD.MM.YYYY)"));

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-d");
        commandsList.add(dateString);

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Date could not be parsed! (Date format must be: DD.MM.YYYY)", exception.getMessage());
    }

    @Test
    void parseCommandReadWithWrongDistanceFormat() {
        Actions action = Actions.READ;
        String distance = "5km";

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseKilometerFrom(distance)).thenThrow(new IllegalArgumentException("Distance must be a number! (Number format for Distances is: X.X)"));

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-di");
        commandsList.add(distance);

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Distance must be a number! (Number format for Distances is: X.X)", exception.getMessage());
    }

    @Test
    void parseCommandUnknownAction() {
        String action = "UNKNOWN";

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action);

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Action nicht definiert: " + action, exception.getMessage());
    }

    @Test
    void parseCommandAnalyze() {
        Actions action = Actions.ANALYZE;
        Place fromPlace = new Place("FROM_PLACE");
        Place toPlace = new Place("TO_PLACE");
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        Kilometer distance = new Kilometer(5);

        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(fromPlace.toString())).thenReturn(fromPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parsePlaceFrom(toPlace.toString())).thenReturn(toPlace);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseDateFrom(dateString)).thenReturn(date);
        mockedStaticArgumentsParser.when(() -> ArgumentsParser.parseKilometerFrom(distance.toString())).thenReturn(distance);

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-fp");
        commandsList.add(fromPlace.toString());
        commandsList.add("-tp");
        commandsList.add(toPlace.toString());
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-di");
        commandsList.add(distance.toString());

        tripInterpreter = new TripInterpreter(commandsList, mockedDataHandler);

        tripInterpreter.parseCommands();
        assertEquals(action, tripInterpreter.getAction());
        assertEquals(0, tripInterpreter.getId());
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
        assertEquals(date, tripInterpreter.getDate());
        assertEquals(distance, tripInterpreter.getDistance());
    }
}