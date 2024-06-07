package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * This class is a repository for trips. It provides methods to read, write, and delete trips.
 * It uses a DataHandlerInterface to interact with the data source.
 */
public class TripRepository {

    /**
     * The data handler used for reading and writing trips.
     */
    private final DataHandlerInterface dataHandler;

    /**
     * The list of trips managed by this repository.
     */
    private final List<Trip> trips;

    /**
     * The singleton instance of the TripRepository.
     */
    private static TripRepository instance = null;

    /**
     * Private constructor to prevent direct instantiation.
     * @param dataHandler The data handler to be used for reading and writing trips.
     */
    private TripRepository(DataHandlerInterface dataHandler) {
        this.dataHandler = dataHandler;
        this.trips = dataHandler.readAllTrips();
    }

    /**
     * Returns the singleton instance of the TripRepository.
     * @param dataHandler The data handler to be used for reading and writing trips.
     * @return The singleton instance of the TripRepository.
     */
    public static TripRepository getInstance(DataHandlerInterface dataHandler) {
        if (instance == null) instance = new TripRepository(dataHandler);
        return instance;
    }

    /**
     * Returns the ID for the next trip.
     * @return The ID for the next trip.
     */
    public int getNextTripId() {
        return trips.stream().mapToInt(Trip::getId).max().orElse(0) + 1;
    }

    /**
     * Returns a list of trips that match the given parameters.
     * @param from The starting place of the trip.
     * @param to The destination of the trip.
     * @param date The date of the trip.
     * @return A list of trips that match the given parameters.
     */
    public List<Trip> readTrips(Place from, Place to, LocalDate date) {
        return trips.stream()
                .filter(t -> (from == null || t.getFrom().equals(from)) && (to == null || t.getTo().equals(to))
                        && (date == null || t.getDate().equals(date))
                ).collect(Collectors.toList());
    }

    /**
     * Adds a trip to the repository and saves the trips.
     * @param trip The trip to be added.
     */
    public void writeTrip(Trip trip) {
        trips.add(trip);
        saveTrips();
    }

    /**
     * Deletes a trip from the repository and saves the trips.
     * @param trip The trip to be deleted.
     */
    public void deleteTrip(Trip trip) {
        if (trip.getId() == 0) {
            trips.removeIf(t -> (t.getDate().equals(trip.getDate()) || trip.getDate() == null) && (t.getFrom().equals(trip.getFrom()) || trip.getFrom() == null) && (t.getTo().equals(trip.getTo()) || trip.getTo() == null));
        } else {
            trips.removeIf(t -> t.getId() == trip.getId());
        }
        saveTrips();
    }

    /**
     * Saves the trips to the data source using the data handler.
     */
    public void saveTrips() {
        dataHandler.saveTrips(trips);
    }
}