package ntrp.fahrtenlogger.adapters.interpreter;

import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RefuelInterpreter extends CommandInterpreter {
    private final int num_of_mandatory_arguments = 3;
    private Liter amount;
    private Euro price_per_liter;
    private FuelTypes fuel_type = FuelTypes.E5; // TODO: sollte das global definiert werden?
    private LocalDate date = LocalDate.now();

    public RefuelInterpreter(List<String> args) {
        super(args);
    }

    @Override
    public void parseCommands() {
        // command structure:
        // refuel <new:modify:delete> <amount> <price> <-d <date:?>> <-ft <fuel_type:?>>
        // TODO: Error-Behandlung
        if (arguments_list.size() < num_of_mandatory_arguments)
            throw new IllegalArgumentException("Nicht genügend Parameter!");
        parseAction(arguments_list.get(0));
        this.amount = new Liter(Double.parseDouble(arguments_list.get(1)));
        this.price_per_liter = new Euro(Double.parseDouble(arguments_list.get(2)));

        int index = num_of_mandatory_arguments;
        while (arguments_list.size() > index) {
            parseOptionalArguments(index);
            index += 2;
        }
    }

    private void parseOptionalArguments(int index) {
        if (arguments_list.get(index).equals("-d"))
            this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        else if (arguments_list.get(index).equals("-ft"))
            this.fuel_type = FuelTypes.valueOf(arguments_list.get(++ index).toUpperCase());
    }

    @Override
    public void executeCommands() {
        System.out.println(this);
    }

    public static String getHelp() {
        return "REFUEL: creates, updates or deletes a refuel. A refuel is a specific action taken when refueling a car on a specific date.\n---- arguments:\n\t<new> <amount> <price> <-d <date:?>> <-ft <fuel_type:?>>\n\t<modify>\n\t<delete>\n----";
    }

    @Override
    public String toString() {
        return "RefuelInterpreter{" +
                "amount=" + amount +
                ", price_per_liter=" + price_per_liter +
                ", fuel_type=" + fuel_type +
                ", date=" + date +
                ", action=" + action +
                '}';
    }
}
