package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class UnknownInterpreter implements CommandInterpreter {
    private final String input;

    public UnknownInterpreter(String input) {
        this.input = input;
    }

    @Override
    public void interpretCommands() {

    }

    @Override
    public void executeCommands() {
        System.err.println("Des häm ma net! (" + this.input + ")");
    }
}
