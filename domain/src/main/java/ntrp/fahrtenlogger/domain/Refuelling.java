package ntrp.fahrtenlogger.domain;

import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelTypes;

public class Refuelling {
    private final int id;
    private final FuelTypes fuelType;
    private final Liter amount;
    private final Euro pricePerLiter;
    private Euro totalPrice;

    @Override
    public String toString() {
        return "Refuelling{" +
                "id=" + id +
                ", fuelType=" + fuelType +
                ", amount=" + amount +
                ", pricePerLiter=" + pricePerLiter +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public int getId() {
        return id;
    }

    public FuelTypes getFuelType() {
        return fuelType;
    }

    public Liter getAmount() {
        return amount;
    }

    public Refuelling(int id, FuelTypes fuelType, Liter amount, Euro pricePerLiter) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
        this.totalPrice = new Euro(this.pricePerLiter.getAmount() * this.amount.getVolume());
    }

    public Refuelling(FuelTypes fuelType, Liter amount, Euro pricePerLiter) {
        this.id = 1;
        this.fuelType = fuelType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
        this.totalPrice = new Euro(this.pricePerLiter.getAmount() * this.amount.getVolume());
    }
}
