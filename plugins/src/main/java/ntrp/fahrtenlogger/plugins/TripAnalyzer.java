package ntrp.fahrtenlogger.plugins;

import ntrp.fahrtenlogger.domain.Entities.Trip;

import java.util.List;

/**
 * This class is used to analyze a list of trips.
 */
public class TripAnalyzer {

    /**
     * A list of trips to be analyzed.
     */
    private final List<Trip> trips;

    /**
     * Constructs a new TripAnalyzer with a list of trips.
     *
     * @param trips the list of trips to be analyzed
     */
    public TripAnalyzer(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * Returns the count of trips.
     *
     * @return the number of trips
     */
    public int getCount() {
        return trips.size();
    }

    /**
     * Calculates and returns the total distance of all trips.
     *
     * @return the total distance of all trips
     */
    public float getTotalDistance() {
        return trips.stream()
                .map(Trip::getDistance)
                .reduce(0f, Float::sum);
    }
}