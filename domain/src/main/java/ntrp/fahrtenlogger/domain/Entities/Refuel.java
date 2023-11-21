package ntrp.fahrtenlogger.domain.Entities;

import ntrp.fahrtenlogger.domain.data.FuelType;

/**
 * Class representing a refuel event.
 * <p>
 * This class includes information about the id of the refuel event, the amount of liters refueled, the price per liter, the type of fuel used, the gas station where the refuel took place, and the date of the refuel.
 */
public class Refuel {

    private final int id;
    private final float liters;
    private final float pricePerLiter;
    private final FuelType fuelType;
    private final String gasStation;
    private final int date;

    /**
     * Constructor for the Refuel class.
     *
     * @param id            The id of the refuel event.
     * @param liters        The amount of liters refueled.
     * @param pricePerLiter The price per liter.
     * @param fuelType      The type of fuel used.
     * @param gasStation    The gas station where the refuel took place.
     * @param date          The date of the refuel.
     */
    public Refuel(int id, float liters, float pricePerLiter, FuelType fuelType, String gasStation, int date) {
        this.id = id;
        this.liters = liters;
        this.pricePerLiter = pricePerLiter;
        this.fuelType = fuelType;
        this.gasStation = gasStation;
        this.date = date;
    }

    /**
     * Returns the id of the refuel event.
     *
     * @return The id of the refuel event.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the amount of liters refueled.
     *
     * @return The amount of liters refueled.
     */
    public float getLiters() {
        return liters;
    }

    /**
     * Returns the price per liter.
     *
     * @return The price per liter.
     */
    public float getPricePerLiter() {
        return pricePerLiter;
    }

    /**
     * Returns the type of fuel used.
     *
     * @return The type of fuel used.
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Returns the gas station where the refuel took place.
     *
     * @return The gas station where the refuel took place.
     */
    public String getGasStation() {
        return gasStation;
    }

    /**
     * Returns the date of the refuel.
     *
     * @return The date of the refuel.
     */
    public int getDate() {
        return date;
    }
}