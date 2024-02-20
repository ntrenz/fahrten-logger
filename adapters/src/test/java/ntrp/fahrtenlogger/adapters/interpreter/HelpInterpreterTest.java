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

    @Test
    void testGetHelp() {
        assertEquals(HelpInterpreter.getHelp(), "Das ist der Hilfe-Befehl. Schreibe 'help <command>', um mehr Informationen zu einem bestimmten Befehl zu erhalten. Vorhandene Befehle: \ntrip\nrefuel\nexit\nhelp");
    }
}
