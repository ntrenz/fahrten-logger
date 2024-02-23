package ntrp.fahrtenlogger.adapters.interpreter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ntrp.fahrtenlogger.application.DataHandlerInterface;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

class TripInterpreterTest {
    TripInterpreter tripInterpreter;
    DataHandlerInterface dataHandler;

    @BeforeEach
    void setUp() {
        this.tripInterpreter = new TripInterpreter(List.of("new", "a", "b", "-d", "22.11.2023", "-di", "55.3"), dataHandler);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void interpretCommandsTooFewArguments() {
        TripInterpreter tI = new TripInterpreter(List.of("new", "a"), dataHandler);

        assertThrows(IllegalArgumentException.class, () -> tI.parseCommands());
    }

    @Test
    void interpretCommandsCorrectWith3() {
        tripInterpreter.parseCommands();

        assertEquals(tripInterpreter.getDistance().distance(), 55.3);
        // assertEquals(tripInterpreter.getFrom_place(), new Place("a"));
        // assertEquals(tripInterpreter.getTo_place(), new Place("b"));
        assertEquals(tripInterpreter.getDate(), LocalDate.of(2023, 11, 22));
    }

    @Test
    void interpretCommandsWithWrongDate() {
        TripInterpreter tI = new TripInterpreter(List.of("new", "a", "b", "-d", "HALLO"), dataHandler);

        assertThrows(DateTimeParseException.class, tI::parseCommands);
    }

    @Test
    void parseOptionalArguments() {
        // int index = 3;
        // this.tripInterpreter.parseOptionalArguments(index);
    }

    @Test
    void executeCommands() {
    }

    @Test
    void correctHelp() {
        assertEquals(TripInterpreter.getHelp(), "TRIP: creates, updates or deletes a trip. A trip is a traveled distance between two places.\n---- arguments:\n\t<new> <from> <to> <-di <distance:?>> <-d <date:?>>\n\t<modify>\n\t<delete>\n----");
    }
}