package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

class TripInterpreterTest {
    // Test Cases:
    //
    // - Not enough Arguments
    // - NEW
    // - MODIFY
    // - DELETE
    // - READ
    // - Unknown Action

    TripInterpreter tripInterpreter;

    @AfterEach
    void methodTeardown() {
        this.tripInterpreter = null;
    }

    @Test
    void parseCommandWithNotEnoughArgs() {
        List<String> commandsList = new ArrayList<>();
        tripInterpreter = new TripInterpreter(commandsList, null);

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

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add(fromPlace.toString());
        commandsList.add(toPlace.toString());

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.action);
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

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add(fromPlace.toString());
        commandsList.add(toPlace.toString());
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-di");
        commandsList.add(distance.toString());

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.action);
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

        tripInterpreter = new TripInterpreter(commandsList, null);

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

        tripInterpreter = new TripInterpreter(commandsList, null);

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

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.action);
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

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();

        assertEquals(action, tripInterpreter.action);
        assertEquals(fromPlace, tripInterpreter.getFromPlace());
        assertEquals(toPlace, tripInterpreter.getToPlace());
        assertEquals(date, tripInterpreter.getDate());
        assertEquals(id, tripInterpreter.getId());
    }

    @Test
    void parseCommandDeleteWithInvalidId() {
        Actions action = Actions.DELETE;
        String id = "ID";

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-id");
        commandsList.add(id);

        tripInterpreter = new TripInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("ID must be an integer!", exception.getMessage());
    }

    @Test
    void parseCommandDeleteWithInvalidDate() {
        Actions action = Actions.DELETE;
        String dateString = "01-03-2024";

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-d");
        commandsList.add(dateString);

        tripInterpreter = new TripInterpreter(commandsList, null);

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

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();
        assertEquals(Actions.READ, tripInterpreter.action);
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

        tripInterpreter = new TripInterpreter(commandsList, null);

        tripInterpreter.parseCommands();
        assertEquals(Actions.READ, tripInterpreter.action);
        assertNull(tripInterpreter.getDate());
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

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-d");
        commandsList.add(dateString);

        tripInterpreter = new TripInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Date could not be parsed! (Date format must be: DD.MM.YYYY)", exception.getMessage());
    }

    @Test
    void parseCommandReadWithWrongDistanceFormat() {
        Actions action = Actions.READ;
        String distance = "5km";

        List<String> commandsList = new ArrayList<>();
        commandsList.add(action.toString().toLowerCase());
        commandsList.add("-di");
        commandsList.add(distance);

        tripInterpreter = new TripInterpreter(commandsList, null);

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

        tripInterpreter = new TripInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            tripInterpreter.parseCommands();
        });
        assertEquals("Action nicht definiert: " + action, exception.getMessage());
    }
}