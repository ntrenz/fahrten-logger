package ntrp.fahrtenlogger.domain.Entities;

/**
 * Class representing a trip.
 * <p>
 * This class includes information about the id of the trip, the date of the trip, the starting location, the destination, and the distance of the trip.
 */
public class Trip {

    private final int id;
    private final int date;
    private final String from;
    private final String to;
    private final float distance;

    /**
     * Constructor for the Trip class.
     *
     * @param id       The id of the trip.
     * @param date     The date of the trip.
     * @param from     The starting location of the trip.
     * @param to       The destination of the trip.
     * @param distance The distance of the trip.
     */
    public Trip(int id, int date, String from, String to, float distance) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    /**
     * Returns the id of the trip.
     *
     * @return The id of the trip.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the date of the trip.
     *
     * @return The date of the trip.
     */
    public int getDate() {
        return date;
    }

    /**
     * Returns the starting location of the trip.
     *
     * @return The starting location of the trip.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the destination of the trip.
     *
     * @return The destination of the trip.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the distance of the trip.
     *
     * @return The distance of the trip.
     */
    public float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("Trip %d on %d from %s to %s (%.2f km)", id, date, from, to, distance);
    }
}