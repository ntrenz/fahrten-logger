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
        this.name = name;
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
        this.name = name;
    }

    /**
     * Returns a string representation of this Place.
     *
     * @return a string representation of this Place
     */
    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }
}