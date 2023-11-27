package ntrp.fahrtenlogger.adapters.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefuelInterpreterTest {

    @Test
    void interpretCommands() {
    }

    @Test
    void executeCommands() {
    }

    @Test
    void getHelp() {
        assertEquals(RefuelInterpreter.getHelp(), "REFUEL: creates, updates or deletes a refuel. A refuel is a specific action taken when refueling a car on a specific date.\n---- arguments:\n\t<new> <amount> <price> <-d <date:?>> <-ft <fuel_type:?>>\n\t<modify>\n\t<delete>\n----");
    }
}