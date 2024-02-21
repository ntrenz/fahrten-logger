package ntrp.fahrtenlogger.domain.Entities;

import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

import java.time.LocalDate;

/**
 * The Trip class represents a trip with specific details.
 */
public class Trip {

    /**
     * The id of the trip.
     */
    private int id;

    /**
     * The starting place of the trip.
     */
    private Place from;

    /**
     * The destination of the trip.
     */
    private Place to;

    /**
     * The distance of the trip.
     */
    private Kilometer distance;

    /**
     * The date of the trip.
     */
    private LocalDate date;

    /**
     * Constructs a new Trip with the specified details.
     *
     * @param id the id of the trip
     * @param from the starting place of the trip
     * @param to the destination of the trip
     * @param distance the distance of the trip
     * @param date the date of the trip
     */
    public Trip(int id, Place from, Place to, Kilometer distance, LocalDate date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.date = date;
    }

    /**
     * Gets the id of this trip.
     *
     * @return the id of this trip
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this trip.
     *
     * @param id the new id of this trip
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the starting place of this trip.
     *
     * @return the starting place of this trip
     */
    public Place getFrom() {
        return from;
    }

    /**
     * Sets the starting place of this trip.
     *
     * @param from the new starting place of this trip
     */
    public void setFrom(Place from) {
        this.from = from;
    }

    /**
     * Gets the destination of this trip.
     *
     * @return the destination of this trip
     */
    public Place getTo() {
        return to;
    }

    /**
     * Sets the destination of this trip.
     *
     * @param to the new destination of this trip
     */
    public void setTo(Place to) {
        this.to = to;
    }

    /**
     * Gets the distance of this trip.
     *
     * @return the distance of this trip
     */
    public Kilometer getDistance() {
        return distance;
    }

    /**
     * Sets the distance of this trip.
     *
     * @param distance the new distance of this trip
     */
    public void setDistance(Kilometer distance) {
        this.distance = distance;
    }

    /**
     * Gets the date of this trip.
     *
     * @return the date of this trip
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of this trip.
     *
     * @param date the new date of this trip
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns a string representation of this Trip.
     *
     * @return a string representation of this Trip
     */
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                ", date=" + date +
                '}';
    }
}