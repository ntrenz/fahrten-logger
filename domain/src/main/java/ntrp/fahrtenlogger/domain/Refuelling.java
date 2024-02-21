package ntrp.fahrtenlogger.domain;

import ntrp.fahrtenlogger.domain.data.FuelType;

/**
 * Class representing a refuelling event.
 * <p>
 * This class includes information about the id of the refuelling event, the type of fuel used, and the amount of fuel.
 */
public class Refuelling {

    private final int id;
    private final FuelType fuelType;
    private final double amount;

    /**
     * Constructor for the Refuelling class.
     *
     * @param id        The id of the refuelling event.
     * @param fuelType  The type of fuel used.
     * @param amount    The amount of fuel.
     */
    public Refuelling(int id, FuelType fuelType, double amount) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
    }

    /**
     * Constructor for the Refuelling class with default id.
     *
     * @param fuelType  The type of fuel used.
     * @param amount    The amount of fuel.
     */
    public Refuelling(FuelType fuelType, double amount) {
        this(1, fuelType, amount);
    }

    /**
     * Returns the id of the refuelling event.
     *
     * @return The id of the refuelling event.
     */
    public int getId() {
        return id;
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
     * Returns the amount of fuel.
     *
     * @return The amount of fuel.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the string representation of the Refuelling object.
     *
     * @return A string representation of the Refuelling object.
     */
    @Override
    public String toString() {
        return "Refuelling{id=" + id + ", fuelType=" + fuelType + ", amount=" + amount + "}";
    }
}