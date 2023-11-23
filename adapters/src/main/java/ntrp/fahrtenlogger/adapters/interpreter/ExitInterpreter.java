package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class ExitInterpreter implements CommandInterpreter {
    private final List<String> commands;
    private Actions action;

    public ExitInterpreter(List<String> args) {
        System.out.println("My arguments are:");
        args.forEach(System.out::println);
        this.commands = args;
    }
    @Override
    public void interpretCommands() {
        // Todo: should be empty or something like exit has no commands
    }

    @Override
    public void executeCommands() {
        // Todo: save all data. After this, the application is stopped.
    }
}
