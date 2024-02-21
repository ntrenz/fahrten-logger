package ntrp.fahrtenlogger.domain;

import ntrp.fahrtenlogger.domain.data.FuelType;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

/**
 * Class representing a refuelling event.
 * <p>
 * This class includes information about the id of the refuelling event, the type of fuel used, and the amount of fuel.
 */
public class Refuelling {

    private final int id;
    private final FuelType fuelType;
    private final Liter amount;
    private final Euro pricePerLiter;
    private final Euro totalPrice;

    /**
     * Constructor for the Refuelling class.
     *
     * @param id        The id of the refuelling event.
     * @param fuelType  The type of fuel used.
     * @param amount    The amount of fuel.
     */
    public Refuelling(int id, FuelType fuelType, Liter amount, Euro pricePerLiter) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
        this.totalPrice = new Euro(this.pricePerLiter.getAmount() * this.amount.getVolume());
    }

    /**
     * Constructor for the Refuelling class with default id.
     *
     * @param fuelType  The type of fuel used.
     * @param amount    The amount of fuel.
     */
    public Refuelling(FuelType fuelType, Liter amount, Euro pricePerLiter) {
        this(1, fuelType, amount, pricePerLiter);
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

    public Liter getAmount() {
        return amount;
    }

    /**
     * Returns the string representation of the Refuelling object.
     *
     * @return A string representation of the Refuelling object.
     */
    @Override
    public String toString() {
        return "Refuelling{id=" + id + ", fuelType=" + fuelType + ", pricePerLiter=" + pricePerLiter + ", totalPrice=" + totalPrice + ", amount=" + amount + "}";
    }
}