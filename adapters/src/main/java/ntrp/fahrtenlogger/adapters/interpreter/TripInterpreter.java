package ntrp.fahrtenlogger.adapters.interpreter;

import java.time.LocalDate;
import java.util.List;

import ntrp.fahrtenlogger.application.AnalyzerWrapper;
import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.TripRepository;
import ntrp.fahrtenlogger.application.analyzer.PrintInterface;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

public class TripInterpreter extends CommandInterpreter {
    private final int num_of_mandatory_arguments = 3;
    private Place fromPlace;
    private Place toPlace;
    private Kilometer distance = new Kilometer(0);
    private LocalDate date = LocalDate.now();
    private final DataHandlerInterface dataHandler;
    private PrintInterface print;
    private TripRepository tripRepository;
    private int id;

    public TripInterpreter(List<String> args, DataHandlerInterface dataHandler) {
        super(args);
        this.dataHandler = dataHandler;
        this.tripRepository = TripRepository.getInstance(dataHandler);
    }

    public TripInterpreter(List<String> args, DataHandlerInterface dataHandler, PrintInterface print) {
        super(args);
        this.dataHandler = dataHandler;
        this.tripRepository = TripRepository.getInstance(dataHandler);
        this.print = print;
    }

    public Place getFromPlace() {
        return fromPlace;
    }

    public Place getToPlace() {
        return toPlace;
    }

    public Kilometer getDistance() {
        return distance;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public Actions getAction() {
        return action;
    }

    @Override
    public void parseCommands() {
        // command-structure:
        // trip <new:modify:delete> <from> <to> <-di <distance:?>> <-d <date:?>>
        if (arguments_list.isEmpty())
            throw new IllegalArgumentException("Not enough Parameters!");
        this.action = parseAction(arguments_list.get(0));
        switch (this.action) {
            case NEW -> parseNewCommands();
            case MODIFY -> parseModifyCommands();
            case DELETE -> parseDeleteCommands();
            case READ -> parseReadCommands();
            case ANALYZE -> parseAnalyzeCommands();
            default -> throw new IllegalArgumentException("Unexpected value: " + this.action);
        }

    }

    @Override
    protected void parseNewCommands() throws IllegalArgumentException {
        if (arguments_list.size() < num_of_mandatory_arguments)
        throw new IllegalArgumentException("Nicht genügend Parameter!");
        
        this.fromPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(1));
        this.toPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(2));
        this.id = tripRepository.getNextTripId();
        
        int index = num_of_mandatory_arguments;
        while (arguments_list.size() > index) {
            if (arguments_list.get(index).equals("-d"))
            this.date = ArgumentsParser.parseDateFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-di"))
            this.distance = ArgumentsParser.parseKilometerFrom(arguments_list.get(++index));
            index++;
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
            if (arguments_list.get(index).equals("-d")) {
                this.date = ArgumentsParser.parseDateFrom(arguments_list.get(++index));
            } else if (arguments_list.get(index).equals("-fp"))
            this.fromPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-tp"))
            this.toPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-id")) {
                this.id = ArgumentsParser.parseIdFrom(arguments_list.get(++index));
            }
            index++;
        }
    }
    
    @Override
    protected void parseReadCommands() throws IllegalArgumentException {
        this.date = null;
        int index = 1;
        while (arguments_list.size() > index) {
            if (arguments_list.get(index).equals("-fp"))
            this.fromPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-tp"))
            this.toPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-d"))
            this.date = ArgumentsParser.parseDateFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-di"))
            this.distance = ArgumentsParser.parseKilometerFrom(arguments_list.get(++index));
            index++;
        }
    }

    @Override
    protected void parseAnalyzeCommands() throws IllegalArgumentException {
        this.date = null;
        int index = 1;
        while (arguments_list.size() > index) {
            if (arguments_list.get(index).equals("-fp"))
            this.fromPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-tp"))
            this.toPlace = ArgumentsParser.parsePlaceFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-d"))
            this.date = ArgumentsParser.parseDateFrom(arguments_list.get(++index));
            else if (arguments_list.get(index).equals("-di"))
            this.distance = ArgumentsParser.parseKilometerFrom(arguments_list.get(++index));
            index++;
        }
    }

    @Override
    public String toString() {
        return "TripInterpreter{from_place=" + fromPlace +
                ", to_place=" + toPlace +
                ", distance=" + distance +
                ", date=" + date +
                ", action=" + action +
                '}';
    }

    @Override
    public void executeCommands() {
        Trip trip = new Trip(
                id,
                fromPlace,
                toPlace,
                distance,
                date);

        switch (this.action) {
            case READ -> {
                List<Trip> readTrips = tripRepository.readTrips(fromPlace, toPlace, date);
                readTrips.forEach(System.out::println);
            }
            case NEW -> tripRepository.writeTrip(trip);
            case DELETE -> tripRepository.deleteTrip(trip);
            case MODIFY -> throw new UnsupportedOperationException("Unimplemented case: " + this.action);
            case ANALYZE -> AnalyzerWrapper.analyzeFor(trip, tripRepository, print);
            default -> throw new IllegalArgumentException("Unexpected value: " + this.action);
        }
    }

    public static String getHelp() {
        return "TRIP: creates, updates or deletes a trip. A trip is a traveled distance between two places.\n---- arguments:\n\tnew <from place> <to place> <-di <distance>:?> <-d <date>:?>\n\tdelete\n\tread <-fp <from place>:?> <-tp <to place>:?> <-d <date>:?>\n----";
    }
}
