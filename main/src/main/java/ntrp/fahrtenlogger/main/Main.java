package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.adapters.interpreter.CommandInterpreter;
import ntrp.fahrtenlogger.adapters.interpreter.ExitInterpreter;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.io.UserInputHandler;

public class Main {
    private static DataHandler dataHandler;

    public static void main(String[] args) {
        // - init -
        dataHandler = new DataHandler();

        // - main loop -
        mainLoop();
    }

    @SuppressWarnings("static-access")
    private static void mainLoop() {
        UserInputHandler u = new UserInputHandler();
        CommandInterpreter interpreter;
        do {
            String input = u.receiveUserInput();
            interpreter = u.handleUserInput(input, dataHandler);
            try {
                interpreter.parseCommands();
                interpreter.executeCommands();
            } catch (Exception e) {
                System.err.println("Im oben ausgeführten Befehl ist ein Fehler aufgetreten: " + e.getMessage() + "\n" + interpreter.getHelp());
            }
        } while (!(interpreter instanceof ExitInterpreter));
    }
}
