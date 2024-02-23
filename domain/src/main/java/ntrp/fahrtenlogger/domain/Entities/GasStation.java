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
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();;
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
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GasStation other = (GasStation) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}