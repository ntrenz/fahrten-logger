package ntrp.fahrtenlogger.adapters.interpreter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

public class TripInterpreter extends CommandInterpreter {
    private final int num_of_mandatory_arguments = 3;
    private Place from_place;
    private Place to_place;
    private Kilometer distance = new Kilometer(0);
    private LocalDate date = LocalDate.now();

    public TripInterpreter(List<String> args) {
        super(args);
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

    @Override
    public void parseCommands() {
        // command-structure:
        // trip <new:modify:delete> <from> <to> <-di <distance:?>> <-d <date:?>>
        // TODO: Error-Behandlung
        if (arguments_list.size() < num_of_mandatory_arguments)
            throw new IllegalArgumentException("Nicht genügend Parameter!");
        parseAction(arguments_list.get(0));
        this.from_place = new Place(arguments_list.get(1));
        this.to_place = new Place(arguments_list.get(2));

        int index = num_of_mandatory_arguments;
        while (arguments_list.size() > index) {
            parseOptionalArguments(index);
            index += 2;
        }
    }

    private void parseOptionalArguments(int index) {
        if (arguments_list.get(index).equals("-d"))
            this.date = LocalDate.parse(arguments_list.get(++ index), DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        else if (arguments_list.get(index).equals("-di"))
            this.distance = new Kilometer(Double.parseDouble(arguments_list.get(++ index)));
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
        System.out.println(this);
    }

    public static String getHelp() {
        return "TRIP: creates, updates or deletes a trip. A trip is a traveled distance between two places.\n---- arguments:\n\t<new> <from> <to> <-di <distance:?>> <-d <date:?>>\n\t<modify>\n\t<delete>\n----";
    }
}
