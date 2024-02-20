package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

/**
 * Superclass for all Interpreter Classes. Defines an action attribute {@code action} and a method {@code handleCommands} to handle the commands.
 */
public abstract class CommandInterpreter {
    final List<String> arguments_list;
    Actions action;

    protected CommandInterpreter(List<String> arguments_list) {
        if (arguments_list.equals(null))
            this.arguments_list = List.of();
        else
            this.arguments_list = arguments_list;
    }

    /**
     * Parses all arguments given to a command.
     *
     * @throws IllegalArgumentException - Argument not found
     */
    public abstract void parseCommands() throws IllegalArgumentException;

    /**
     * Handles the input commands an executes the appropriate methods of the application.
     */
    public abstract void executeCommands();

    /**
     * Returns the help information of the corresponding command.
     * @return help description
     */
    public static String getHelp() {
        return "Hilfe! 😱";
    };

    /**
     * Sets the attribute {@code action} to the corresponding action. If argument cannot be parsed, an {@link IllegalArgumentException} is thrown.
     *
     * @throws IllegalArgumentException when action is not defined
     * @param command the command holding Action information
     */
    public void parseAction(String command) throws IllegalArgumentException {
        switch (command) {
            case "n", "new" -> this.action = Actions.NEW;
            case "m", "modify" -> this.action = Actions.MODIFY;
            case "d", "delete" -> this.action = Actions.DELETE;
            default -> throw new IllegalArgumentException("Action nicht definiert: " + command);
        }
    }
}
