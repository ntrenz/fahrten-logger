package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class HelpInterpreter extends CommandInterpreter {
    private String command = "";

    public HelpInterpreter(List<String> args) {
        super(args);
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void parseCommands() {
        if (arguments_list.size() > 0) {
            this.command = arguments_list.get(0);
        }
    }

    @Override
    public void executeCommands() {
        if (command.isEmpty()) {
            System.out.println(HelpInterpreter.getHelp());
        } else {
            switch (command) {
                case "refuel" -> {
                    System.out.println(RefuelInterpreter.getHelp());
                }
                case "trip" -> {
                    System.out.println(TripInterpreter.getHelp());
                }
                case "exit" -> {
                    System.out.println(ExitInterpreter.getHelp());
                }
                case "help" -> {
                    System.out.println(HelpInterpreter.getHelp());
                }
                default -> {
                    System.out.println(CommandInterpreter.getHelp());
                }
            }
        }
    }

    public static String getHelp() {
        return "Das ist der Hilfe-Befehl. Schreibe 'help <command>', um mehr Informationen zu einem bestimmten Befehl zu erhalten. Vorhandene Befehle: \ntrip\nrefuel\nexit\nhelp\nDatumsformat: DD.MM.YYYY\nZahlenformat: x.x";
    }

    @Override
    protected void parseNewCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseNewCommands'");
    }

    @Override
    protected void parseModifyCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseModifyCommands'");
    }

    @Override
    protected void parseDeleteCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseDeleteCommands'");
    }

    @Override
    protected void parseReadCommands() throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseReadCommands'");
    }
}
