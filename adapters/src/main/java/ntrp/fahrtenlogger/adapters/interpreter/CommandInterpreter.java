package ntrp.fahrtenlogger.adapters.interpreter;

/**
 * Interface for all Interpreter Classes. Defines an action attribute {@code action} and a method {@code handleCommands} to handle the commands.
 *
 * @implNote Each class should implement each applicable attribute and its getters.
 */
public interface CommandInterpreter {
    /**
     * Handles the input commands an executes the appropriate methods of the application.
     */
    void interpretCommands();

    void executeCommands();
}
