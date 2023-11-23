package ntrp.fahrtenlogger.adapters.interpreter;

import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelTypes;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RefuelInterpreter implements CommandInterpreter {
    private List<String> commands;
    private Actions action;
    private Liter amount;
    private Euro price_per_liter;
    private FuelTypes fuel_type; // Todo: choose some default value
    private LocalDate date = LocalDate.now();

    public Actions getAction() {
        return action;
    }

    public Liter getAmount() {
        return amount;
    }

    public Euro getPrice_per_liter() {
        return price_per_liter;
    }

    public FuelTypes getFuel_type() {
        return fuel_type;
    }

    public LocalDate getDate() {
        return date;
    }

    public RefuelInterpreter(List<String> args) {
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
