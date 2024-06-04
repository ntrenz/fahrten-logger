package ntrp.fahrtenlogger.application.analyzer;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * 'TripAnalyzer' is a class for analyzing trips.
 * It includes functionality such as determining the total distance of a list of trips,
 * and providing a detailed analysis of trips between specific places on certain dates.
 */
public class TripAnalyzer {

    /**
     * List of trips to analyze.
     */
    private final List<Trip> trips;

    /**
     * Print instance used for pretty-printing output.
     */
    private final PrintInterface print;

    /**
     * Formatter for dates.
     */
    private final DateTimeFormatter dateTimeFormatter;

    /**
     * Constructs a new TripAnalyzer with a list of trips.
     *
     * @param trips the list of trips to be analyzed.
     */
    public TripAnalyzer(List<Trip> trips, PrintInterface printObject) {
        this.trips = trips;
        print = printObject;
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    }

    /**
     * Calculates the total distance covered in the provided list of trips.
     *
     * @param trips the list of trips.
     * @return the total distance covered.
     */
    public Kilometer getTotalDistance(List<Trip> trips) {
        return new Kilometer(
                trips.stream()
                        .map(Trip::getDistance)
                        .map(Kilometer::distance)
                        .reduce(0., Double::sum)
        );
    }

    /**
     * Provides a detailed analysis of trips between specified 'from' and 'to' places on a certain date.
     * If 'from', 'to', or 'date' are null, this function doesn't filter for that parameter.
     * <p>
     * The analysis includes the total number of trips, their start and end locations (if provided),
     * the date (if provided), and the total distance covered.
     *
     * @param from the start point of the trips.
     * @param to the endpoint of the trips.
     * @param date the date on which the trips occurred.
     * @return a string containing the analysis result.
     */
    public String getAnalysis(Place from, Place to, LocalDate date) {
        List<Trip> filteredTrips = trips.stream().filter(trip ->
                (from == null || trip.getFrom().equals(from)) &&
                (to == null || trip.getTo().equals(to)) &&
                (date == null || trip.getDate().equals(date))
        ).toList();

        boolean fromPlaceProvided = from != null;
        boolean toPlaceProvided = to != null;
        boolean dateProvided = date != null;

        int count = filteredTrips.size();
        Kilometer totalDistance = getTotalDistance(filteredTrips);

        StringBuilder analysis = new StringBuilder();
        analysis.append(print.bold(print.color(String.valueOf(count), PrintColors.ORANGE)));
        analysis.append(count == 1 ? " trip" : " trips");
        analysis.append(" recorded");

        if (fromPlaceProvided) {
            analysis.append(" from ");
            analysis.append(print.bold(print.color(from.toString(), PrintColors.ORANGE)));
        }

        if (toPlaceProvided) {
            analysis.append(" to ");
            analysis.append(print.bold(print.color(to.toString(), PrintColors.ORANGE)));
        }

        if (dateProvided) {
            analysis.append(" on ");
            analysis.append(print.bold(print.color(dateTimeFormatter.format(date), PrintColors.ORANGE)));
        }

        analysis.append(" with a total distance of ");
        analysis.append(print.bold(print.color(totalDistance.format(), PrintColors.ORANGE)));

        return analysis.toString();
    }
}