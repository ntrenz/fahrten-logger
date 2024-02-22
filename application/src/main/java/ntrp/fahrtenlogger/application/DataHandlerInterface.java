package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.Entities.Trip;

import java.util.List;

/**
 * This interface defines the methods that a data handler should implement for reading and writing refuels and trips.
 */
public interface DataHandlerInterface {

    /**
     * Reads all refuels from the data source.
     * @return A list of all refuels.
     */
    List<Refuel> readAllRefuels();

    /**
     * Reads all trips from the data source.
     * @return A list of all trips.
     */
    List<Trip> readAllTrips();

    /**
     * Saves a list of refuels to the data source.
     * @param refuels The list of refuels to be saved.
     */
    void saveRefuels(List<Refuel> refuels);

    /**
     * Saves a list of trips to the data source.
     * @param trips The list of trips to be saved.
     */
    void saveTrips(List<Trip> trips);
}