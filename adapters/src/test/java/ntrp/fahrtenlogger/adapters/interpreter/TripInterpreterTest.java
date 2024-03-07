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

    }

    @Test
    void parseCommandDelete() {

    }
    
    @Test
    void parseCommandDeleteWithArgs() {

    }
    
    @Test
    void parseCommandRead() {

    }

    @Test
    void parseCommandReadWithArgs() {

    }

    @Test
    void parseCommandUnknownAction() {

    }
}