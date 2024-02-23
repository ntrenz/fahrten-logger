package ntrp.fahrtenlogger.domain.Entities;

/**
 * The Place class represents a place with a specific name.
 */
public class Place {

    /**
     * The name of the place.
     */
    private String name;

    /**
     * Constructs a new Place with the specified name.
     *
     * @param name the name of the place
     */
    public Place(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * Gets the name of this place.
     *
     * @return the name of this place
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this place.
     *
     * @param name the new name of this place
     */
    public void setName(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();;
    }

    /**
     * Returns a string representation of this Place.
     *
     * @return a string representation of this Place
     */
    @Override
    public String toString() {
        return name;
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
        Place other = (Place) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}