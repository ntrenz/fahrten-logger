package ntrp.fahrtenlogger.plugins.io;

import ntrp.fahrtenlogger.adapters.interpreter.*;
import ntrp.fahrtenlogger.plugins.DataHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler() {
        scanner = new Scanner(System.in);
    }

    /**
     * Returns the current user input.
     *
     * @return {@link String} scanned line
     */
    public String receiveUserInput() {
        return scanner.nextLine();
    }

    /**
     * Receives a string of commands and parses them into the corresponding class.
     *
     * @param commands String of commands received by user input
     * @return {@link CommandInterpreter} Implementation of an Interpreter
     */
    public CommandInterpreter handleUserInput(String commands) {
        List<String> commands_list = Arrays.stream(commands.split("[\\s*]"))
                .filter(s -> !Objects.equals(s, ""))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        if (commands_list.isEmpty()) {
            return new UnknownInterpreter("Input cannot be empty!");
        }
        switch (commands_list.get(0)) {
            case "trip" -> {
                return new TripInterpreter(commands_list.subList(1, commands_list.size()));
            }
            case "refuel" -> {
                return new RefuelInterpreter(commands_list.subList(1, commands_list.size()));
            }
            case "help" -> {
                return new HelpInterpreter(commands_list.subList(1, commands_list.size()));
            }
            case "exit" -> {
                return new ExitInterpreter(commands_list.subList(1, commands_list.size()), new DataHandler()); //TODO: use exisitng data Handler object
            }
            default -> {
                return new UnknownInterpreter(commands_list.get(0));
            }
        }
    }
}
