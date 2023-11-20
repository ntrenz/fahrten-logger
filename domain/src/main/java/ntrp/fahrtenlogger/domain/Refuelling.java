package ntrp.fahrtenlogger.domain;

import ntrp.fahrtenlogger.domain.data.FuelTypes;

public class Refuelling {
    private final int id;
    private final FuelTypes fuelType;
    private final double amount;

    public int getId() {
        return id;
    }

    public FuelTypes getFuelType() {
        return fuelType;
    }

    public double getAmount() {
        return amount;
    }

    public Refuelling(int id, FuelTypes fuelType, double amount) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
    }

    public Refuelling(FuelTypes fuelType, double amount) {
        this.id = 1;
        this.fuelType = fuelType;
        this.amount = amount;
    }
}
