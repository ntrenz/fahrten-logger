package ntrp.fahrtenlogger.adapters.interpreter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.TripRepository;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

public class TripInterpreter extends CommandInterpreter {
    private final int num_of_mandatory_arguments = 3;
    private Place from_place;
    private Place to_place;
    private Kilometer distance = new Kilometer(0);
    private LocalDate date = LocalDate.now();
    private final DataHandlerInterface dataHandler;
    private TripRepository tripRepository;
    private int id;

    public TripInterpreter(List<String> args, DataHandlerInterface dataHandler) {
        super(args);
        this.dataHandler = dataHandler;
    }

    public void setFrom_place(Place from_place) {
        this.from_place = from_place;
    }

    public void setTo_place(Place to_place) {
        this.to_place = to_place;
    }

    public void setDistance(Kilometer distance) {
        this.distance = distance;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTripRepoIfIsNull() {
        if (tripRepository == null) {
            this.tripRepository = TripRepository.getInstance(dataHandler);
        }
    }

    public Place getFrom_place() {
        return from_place;
    }

    public Place getTo_place() {
        return to_place;
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

    @Override
    public void parseCommands() {
        // command-structure:
        // trip <new:modify:delete> <from> <to> <-di <distance:?>> <-d <date:?>>
        if (arguments_list.isEmpty())
            throw new IllegalArgumentException("Not enough Parameters!");
        setTripRepoIfIsNull();
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
        if (arguments_list.size() < num_of_mandatory_arguments)
            throw new IllegalArgumentException("Nicht genügend Parameter!");
        
        this.from_place = new Place(arguments_list.get(1));
        this.to_place = new Place(arguments_list.get(2));
        this.id = tripRepository.getNextTripId();
        
        int index = num_of_mandatory_arguments;
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
            if (arguments_list.get(index).equals("-d")) {
                try {
                    this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Date could not be parsed! (Date format must be: DD.MM.YYYY)");
                }
            }
            else if (arguments_list.get(index).equals("-fp"))
                this.from_place = new Place(arguments_list.get(++ index));
            else if (arguments_list.get(index).equals("-tp"))
                this.to_place = new Place(arguments_list.get(++ index));
            else if (arguments_list.get(index).equals("-id")) {
                try {
                    this.id = Integer.parseInt(arguments_list.get(++ index));       
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("ID must be an integer!");
                }
            }
            index += 2;
        }
    }

    @Override
    protected void parseReadCommands() throws IllegalArgumentException {
        this.date = null;
        int index = 1;
        while (arguments_list.size() > index) {
            if (arguments_list.get(index).equals("-fp"))
                this.from_place = new Place(arguments_list.get(++ index));
            else if (arguments_list.get(index).equals("-tp"))
                this.to_place = new Place(arguments_list.get(++ index));
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
        if (arguments_list.get(index).equals("-d")) {
            try {
                this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
            } catch (Exception e) {
                throw new IllegalArgumentException("Date could not be parsed! (Date format must be: DD.MM.YYYY)");
            }
        }
        else if (arguments_list.get(index).equals("-di")) {
            try {
                this.distance = new Kilometer(Double.parseDouble(arguments_list.get(++ index)));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Distance must be a number! (Number format for Distances is: X.X)");
            }
        }
    }

    @Override
    public String toString() {
        return "TripInterpreter{from_place=" + from_place +
                ", to_place=" + to_place +
                ", distance=" + distance +
                ", date=" + date +
                ", action=" + action +
                '}';
    }

    @Override
    public void executeCommands() {
        Trip trip = new Trip(
            id,
            from_place,
            to_place,
            distance,
            date
        );

        switch (this.action) {
            case READ -> {
                List<Trip> readTrips = tripRepository.readTrips(from_place, to_place, date);
                readTrips.forEach(System.out::println);
            }
            case NEW -> tripRepository.writeTrip(trip);
            case DELETE -> tripRepository.deleteTrip(trip);
            case MODIFY -> throw new UnsupportedOperationException("Unimplemented case: " + this.action);
            default -> throw new IllegalArgumentException("Unexpected value: " + this.action);
        }
    }

    public static String getHelp() {
        return "TRIP: creates, updates or deletes a trip. A trip is a traveled distance between two places.\n---- arguments:\n\tnew <from place> <to place> <-di <distance>:?> <-d <date>:?>\n\tdelete\n\tread <-fp <from place>:?> <-tp <to place>:?> <-d <date>:?>\n----";
    }
}
