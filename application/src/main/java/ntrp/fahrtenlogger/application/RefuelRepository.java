package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a repository for refuels. It provides methods to read, write, and delete refuels.
 * It uses a DataHandlerInterface to interact with the data source.
 */
public class RefuelRepository {

    /**
     * The data handler used for reading and writing refuels.
     */
    private final DataHandlerInterface dataHandler;

    /**
     * The list of refuels managed by this repository.
     */
    private final List<Refuel> refuels;

    /**
     * The singleton instance of the RefuelRepository.
     */
    private static RefuelRepository instance = null;

    /**
     * Private constructor to prevent direct instantiation.
     * @param dataHandler The data handler to be used for reading and writing refuels.
     */
    private RefuelRepository(DataHandlerInterface dataHandler) {
        this.dataHandler = dataHandler;
        this.refuels = dataHandler.readAllRefuels();
    }

    /**
     * Returns the singleton instance of the RefuelRepository.
     * @param dataHandler The data handler to be used for reading and writing refuels.
     * @return The singleton instance of the RefuelRepository.
     */
    public static RefuelRepository getInstance(DataHandlerInterface dataHandler) {
        if (instance == null) instance = new RefuelRepository(dataHandler);
        return instance;
    }

    /**
     * Returns the ID for the next refuel.
     * @return The ID for the next refuel.
     */
    public int getNextRefuelId() {
        return refuels.get(refuels.size()-1).getId() + 1;
    }

    /**
     * Returns a list of refuels that match the given parameters.
     * @param date The date of the refuel.
     * @param gasStation The gas station where the refuel took place.
     * @return A list of refuels that match the given parameters.
     */
    public List<Refuel> readRefuels(LocalDate date, GasStation gasStation) {
        return refuels.stream()
                .filter(
                        r -> (gasStation == null || r.getGasStation().equals(gasStation))
                                && (date == null || r.getDate().equals(date))
                ).collect(Collectors.toList());
    }

    /**
     * Adds a refuel to the repository and saves the refuels.
     * @param refuel The refuel to be added.
     */
    public void writeRefuel(Refuel refuel) {
        refuels.add(refuel);
        saveRefuels();
    }

    /**
     * Deletes a refuel from the repository and saves the refuels.
     * @param refuel The refuel to be deleted.
     */
    public void deleteRefuel(Refuel refuel) {
        // TODO: Currently deletes all refuels on the date of the input refuel. In the future we might want to print
        //  a list of all refuels on that date to the user with their IDs and have the user reenter the delete prompt
        //  with the ID to only delete one specific refuel.
        refuels.forEach(r -> {
            if (r.getDate().equals(refuel.getDate())) refuels.remove(r);
        });
        saveRefuels();
    }

    /**
     * Saves the refuels to the data source using the data handler.
     */
    public void saveRefuels() {
        dataHandler.saveRefuels(refuels);
    }
}