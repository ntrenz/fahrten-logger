package ntrp.fahrtenlogger.adapters.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class HelpInterpreterTest {
    HelpInterpreter helpInterpreter;
    @BeforeEach
    void setUp() {
        this.helpInterpreter = new HelpInterpreter(List.of());
    }

    @Test
    void testInterpretCommands() {
        helpInterpreter.parseCommands();

        assertTrue(true);
    }

    @Test
    void testInterpretCommandsWithCommand() {
        HelpInterpreter helpInterpreter = new HelpInterpreter(List.of("refuel"));
        helpInterpreter.parseCommands();

        assertTrue(helpInterpreter.getCommand().equals("refuel"));
    }

    @Test
    void testExecuteCommands() {

    }
}
