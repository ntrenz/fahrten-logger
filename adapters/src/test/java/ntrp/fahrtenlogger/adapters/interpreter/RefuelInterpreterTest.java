package ntrp.fahrtenlogger.adapters.interpreter;

import org.junit.jupiter.api.Test;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;

class RefuelInterpreterTest {
    // Test Cases:
    // 
    // - Not enough arguments
    // - NEW
    // - MODIFY
    // - DELETE
    // - READ
    // - Unknown Action

    RefuelInterpreter refuelInterpreter;

    @AfterEach
    void methodTeardown() {
        refuelInterpreter = null;
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
        
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        refuelInterpreter.parseCommands();

        assertEquals(action, refuelInterpreter.action);
        assertEquals(liter, refuelInterpreter.getLiters());
        assertEquals(pricePerLiter, refuelInterpreter.getPricePerLiter());
    }

    @Test
    void parseCommandNewToFewArguments() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("new");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Nicht genügend Parameter!", exception.getMessage());
    }
    
    @Test
    void parseCommandModify() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("modify");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Unimplemented method 'parseModifyCommands'", exception.getMessage());
    }

    @Test
    void parseCommandDelete() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("delete");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        refuelInterpreter.parseCommands();
        assertEquals(Actions.DELETE, refuelInterpreter.action);
        assertNull(refuelInterpreter.getDate());
        assertEquals(0, refuelInterpreter.getId());
    }

    @Test
    void parseCommandDeleteWithDateAndId() {
        String dateString = "01.03.2024";
        int id = 1;
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));;

        List<String> commandsList = new ArrayList<>();
        commandsList.add("delete");
        commandsList.add("-d");
        commandsList.add(dateString);
        commandsList.add("-id");
        commandsList.add(String.valueOf(id));
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        refuelInterpreter.parseCommands();
        assertEquals(Actions.DELETE, refuelInterpreter.action);
        assertEquals(date, refuelInterpreter.getDate());
        assertEquals(id, refuelInterpreter.getId());
    }
    
    @Test
    void parseCommandRead() {
        List<String> commandsList = new ArrayList<>();
        commandsList.add("read");
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        refuelInterpreter.parseCommands();
        assertEquals(Actions.READ, refuelInterpreter.action);
        assertNull(refuelInterpreter.getDate());
        assertEquals(0, refuelInterpreter.getId());
    }

    @Test
    void parseCommandReadWithArgs() {
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));;
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
        refuelInterpreter = new RefuelInterpreter(commandsList, null);

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
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

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
        this.refuelInterpreter = new RefuelInterpreter(commandsList, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            refuelInterpreter.parseCommands();
        });
        assertEquals("Action nicht definiert: " + action, exception.getMessage());
    }
}