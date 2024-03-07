package ntrp.fahrtenlogger.adapters.interpreter;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.RefuelRepository;
import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RefuelInterpreter extends CommandInterpreter {
    private final int NUM_OF_MANDATORY_ARGS = 3;
    private int id;
    private Liter liters;
    private Euro pricePerLiter;
    private FuelType fuelType = FuelType.E5;
    private LocalDate date = LocalDate.now();
    private GasStation gasStation;
    private final DataHandlerInterface dataHandler;
    private RefuelRepository refuelRepository;

    public RefuelInterpreter(List<String> args, DataHandlerInterface dataHandler) {
        super(args);
        this.dataHandler = dataHandler;
        this.refuelRepository = RefuelRepository.getInstance(dataHandler);
        this.id = refuelRepository.getNextRefuelId();
    }

    public Liter getLiters() {
        return liters;
    }

    public Euro getPricePerLiter() {
        return pricePerLiter;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public LocalDate getDate() {
        return date;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    @Override
    public void parseCommands() {
        // command structure:
        // refuel <new:modify:delete> <amount> <price> <-d <date:?>> <-ft <fuel_type:?>>
        if (arguments_list.isEmpty())
            throw new IllegalArgumentException("Not enough Parameters!");
        parseAction(arguments_list.get(0));
        switch (this.action) {
            case NEW -> parseNewCommands();
            case MODIFY -> parseModifyCommands(); 
            case DELETE -> parseDeleteCommands();
            case READ -> parseReadCommands();
            default -> throw new IllegalArgumentException("Unexpected value: " + this.action);
        }
    }

    @Override
    protected void parseNewCommands() throws IllegalArgumentException {
        if (arguments_list.size() < NUM_OF_MANDATORY_ARGS)
            throw new IllegalArgumentException("Nicht genügend Parameter!");
            
            this.liters = new Liter(Double.parseDouble(arguments_list.get(1)));
            this.pricePerLiter = new Euro(Double.parseDouble(arguments_list.get(2)));

            int index = NUM_OF_MANDATORY_ARGS;
            while (arguments_list.size() > index) {
                parseOptionalArguments(index);
                index += 2;
            }
    }
    
    @Override
    protected void parseModifyCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseModifyCommands'");
    }

    @Override
    protected void parseDeleteCommands() throws IllegalArgumentException {
        this.date = null;
        int index = 1;
        while (arguments_list.size() > index) {
            if (arguments_list.get(index).equals("-d"))
                this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
            else if (arguments_list.get(index).equals("-id"))
                this.id = Integer.parseInt(arguments_list.get(++ index));
            index += 2;
        }
    }

    @Override
    protected void parseReadCommands() throws IllegalArgumentException {
        this.date = null;
        int index = 1;
        while (arguments_list.size() > index) {
            parseOptionalArguments(index);
            index += 2;
        }
    }
    
    /**
     * Parses the optional arguments for the action 'NEW'
     * 
     * @param index - the starting index of optional arguments
     */
    private void parseOptionalArguments(int index) {
        if (arguments_list.get(index).equals("-d"))
            this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        else if (arguments_list.get(index).equals("-ft")) {
            try {
            this.fuelType = FuelType.valueOf(arguments_list.get(++ index).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Fuel Type is not defined!");
            }
        } else if (arguments_list.get(index).equals("-gs"))
            this.gasStation = new GasStation(arguments_list.get(++ index));
    }

    @Override
    public void executeCommands() {
        Refuel refuel = new Refuel(
                this.id,
                liters,
                pricePerLiter,
                fuelType,
                gasStation,
                date
        );

        switch (this.action) {
            case READ -> {
                List<Refuel> readRefuels = refuelRepository.readRefuels(date, gasStation);
                readRefuels.forEach(System.out::println);
            }
            case NEW -> refuelRepository.writeRefuel(refuel);
            case DELETE -> refuelRepository.deleteRefuel(refuel);
            case MODIFY -> throw new UnsupportedOperationException("Unimplemented case: " + this.action);
            default -> throw new IllegalArgumentException("Unexpected value: " + this.action);
        }
    }

    public static String getHelp() {
        return "REFUEL: creates, updates or deletes a refuel. A refuel is a specific action taken when refueling a car on a specific date.\n---- arguments:\n\tnew <amount> <price> <-d <date:?>> <-ft <fuel_type:?>>\n\tdelete\n\tread <-d <date>:?> <-gs <gas station>:?>\n----";
    }

    @Override
    public String toString() {
        return "RefuelInterpreter{" +
                "amount=" + liters +
                ", price_per_liter=" + pricePerLiter +
                ", fuel_type=" + fuelType +
                ", date=" + date +
                ", action=" + action +
                '}';
    }
}
