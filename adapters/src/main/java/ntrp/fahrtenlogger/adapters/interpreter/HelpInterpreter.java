package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class HelpInterpreter implements CommandInterpreter {
    private final List<String> commands;
    private Actions action;

    public HelpInterpreter(List<String> args) {
        System.out.println("My arguments are:");
        args.forEach(System.out::println);
        this.commands = args;
    }

    @Override
    public void interpretCommands() {

    }

    @Override
    public void executeCommands() {

    }
}
