package ntrp.fahrtenlogger.domain.Entities;

/**
 * The GasStation class represents a gas station with a specific name.
 */
public class GasStation {

    /**
     * The name of the gas station.
     */
    private String name;

    /**
     * Constructs a new GasStation with the specified name.
     *
     * @param name the name of the gas station
     */
    public GasStation(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this gas station.
     *
     * @return the name of this gas station
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this gas station.
     *
     * @param name the new name of this gas station
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of this GasStation.
     *
     * @return a string representation of this GasStation
     */
    @Override
    public String toString() {
        return "GasStation{" +
                "name='" + name + '\'' +
                '}';
    }
}