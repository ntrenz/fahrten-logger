package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.adapters.interpreter.CommandInterpreter;
import ntrp.fahrtenlogger.adapters.interpreter.ExitInterpreter;
import ntrp.fahrtenlogger.plugins.io.UserInputHandler;

public class Main {
    public static void main(String[] args) {
        // - init -


        // - main loop -
        mainLoop();

        // speichere Daten
    }

    private static void mainLoop() {
        UserInputHandler u = new UserInputHandler();
        CommandInterpreter interpreter;
        do {
            String input = u.receiveUserInput();
            interpreter = u.handleUserInput(input);
            interpreter.interpretCommands();
            interpreter.executeCommands();
        } while (!(interpreter instanceof ExitInterpreter));
    }
}
