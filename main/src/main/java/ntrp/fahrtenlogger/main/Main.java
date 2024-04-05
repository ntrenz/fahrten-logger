package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.adapters.interpreter.CommandInterpreter;
import ntrp.fahrtenlogger.adapters.interpreter.ExitInterpreter;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.Print;
import ntrp.fahrtenlogger.plugins.io.UserInputHandler;

public class Main {
    private static DataHandler dataHandler;
    private static Print print;

    public static void main(String[] args) {
        // - init -
        dataHandler = new DataHandler();
        print = new Print();

        // - main loop -
        mainLoop();
    }

    @SuppressWarnings("static-access")
    private static void mainLoop() {
        UserInputHandler u = new UserInputHandler();
        CommandInterpreter interpreter;
        do {
            String input = u.receiveUserInput();
            interpreter = u.handleUserInput(input, dataHandler, print);
            try {
                interpreter.parseCommands();
                interpreter.executeCommands();
            } catch (Exception e) {
                System.err.println("Im oben ausgeführten Befehl ist ein Fehler aufgetreten: " + e.getMessage() + "\n" + interpreter.getHelp());
            }
        } while (!(interpreter instanceof ExitInterpreter));
    }
}
