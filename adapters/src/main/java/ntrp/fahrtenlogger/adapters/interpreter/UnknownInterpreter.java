package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class UnknownInterpreter extends CommandInterpreter {
    private String input;
    public UnknownInterpreter(String input) {
        super(List.of(input));
    }

    @Override
    public void parseCommands() {
        this.input = this.arguments_list.get(0);
    }

    @Override
    public void executeCommands() {
        System.err.println("Des häm ma net! (" + this.input + ")");
    }
}
