package ntrp.fahrtenlogger.domain.Entities;

import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Refuel class represents a refueling event with specific details.
 */
public class Refuel {

    /**
     * The id of the refueling event.
     */
    private int id;

    /**
     * The amount of fuel in liters.
     */
    private Liter liters;

    /**
     * The price per liter of fuel.
     */
    private Euro pricePerLiter;

    /**
     * The type of fuel used.
     */
    private FuelType fuelType;

    /**
     * The gas station of the refueling event.
     */
    private GasStation gasStation;

    /**
     * The date of the refueling event.
     */
    private LocalDate date;

    /**
     * Constructs a new Refuel with the specified details.
     *
     * @param id the id of the refueling event
     * @param liters the amount of fuel in liters
     * @param pricePerLiter the price per liter of fuel
     * @param fuelType the type of fuel used
     * @param gasStation the gas station of the refueling event
     * @param date the date of the refueling event
     */
    public Refuel(int id, Liter liters, Euro pricePerLiter, FuelType fuelType, GasStation gasStation, LocalDate date) {
        this.id = id;
        this.liters = liters;
        this.pricePerLiter = pricePerLiter;
        this.fuelType = fuelType;
        this.gasStation = gasStation;
        this.date = date;
    }

    /**
     * Gets the id of this refueling event.
     *
     * @return the id of this refueling event
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this refueling event.
     *
     * @param id the new id of this refueling event
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the amount of fuel in liters.
     *
     * @return the amount of fuel in liters
     */
    public Liter getLiters() {
        return liters;
    }

    /**
     * Sets the amount of fuel in liters.
     *
     * @param liters the new amount of fuel in liters
     */
    public void setLiters(Liter liters) {
        this.liters = liters;
    }

    /**
     * Gets the price per liter of fuel.
     *
     * @return the price per liter of fuel
     */
    public Euro getPricePerLiter() {
        return pricePerLiter;
    }

    /**
     * Sets the price per liter of fuel.
     *
     * @param pricePerLiter the new price per liter of fuel
     */
    public void setPricePerLiter(Euro pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    /**
     * Gets the type of fuel used.
     *
     * @return the type of fuel used
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets the type of fuel used.
     *
     * @param fuelType the new type of fuel used
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets the gas station of the refueling event.
     *
     * @return the gas station of the refueling event
     */
    public GasStation getGasStation() {
        return gasStation;
    }

    /**
     * Sets the gas station of the refueling event.
     *
     * @param gasStation the gas station of the refueling event
     */
    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
    }

    /**
     * Gets the date of the refueling event.
     *
     * @return the date of the refueling event
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the refueling event.
     *
     * @param date the new date of the refueling event
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns a string representation of this Refuel.
     *
     * @return a string representation of this Refuel
     */
    @Override
    public String toString() {
        return "Refuel (" + id +"): " + liters.format() + ", " + pricePerLiter.format() + " (" + fuelType + ") on " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}