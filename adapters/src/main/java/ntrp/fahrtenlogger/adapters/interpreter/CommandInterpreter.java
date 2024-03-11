package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

/**
 * Superclass for all Interpreter Classes. Defines an action attribute
 * {@code action} and a method {@code handleCommands} to handle the commands.
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
     * @throws IllegalArgumentException - Action or Arguments wrong or missing
     */
    public abstract void parseCommands() throws IllegalArgumentException;

    /**
     * Parses arguments for the command 'NEW'
     * 
     * @throws IllegalArgumentException - Not enough arguments
     */
    protected abstract void parseNewCommands() throws IllegalArgumentException;

    /**
     * Parses arguments for the command 'MODIFY'
     * 
     * @throws IllegalArgumentException - Not enough arguments
     */
    protected abstract void parseModifyCommands() throws IllegalArgumentException;

    /**
     * Parses arguments for the command 'DELETE'
     * 
     * @throws IllegalArgumentException - Not enough arguments
     */
    protected abstract void parseDeleteCommands() throws IllegalArgumentException;

    /**
     * Parses arguments for the command 'READ'
     * 
     * @throws IllegalArgumentException - Not enough arguments
     */
    protected abstract void parseReadCommands() throws IllegalArgumentException;

    /**
     * Handles the input commands an executes the appropriate methods of the
     * application.
     */
    public abstract void executeCommands();

    /**
     * Returns the help information of the corresponding command.
     * 
     * @return help description
     */
    public static String getHelp() {
        return "Hilfe! 😱";
    };

    /**
     * Sets the attribute {@code action} to the corresponding action. If argument
     * cannot be parsed, an {@link IllegalArgumentException} is thrown.
     *
     * @throws IllegalArgumentException when action is not defined
     * @param command the command holding Action information
     */
    public Actions parseAction(String command) throws IllegalArgumentException {
        switch (command) {
            case "n", "new" -> {
                return Actions.NEW;
            }
            case "m", "modify" -> {
                return Actions.MODIFY;
            }
            case "d", "delete" -> {
                return Actions.DELETE;
            }
            case "r", "read" -> {
                return Actions.READ;
            }
            default -> throw new IllegalArgumentException("Action nicht definiert: " + command);
        }
    }
}
