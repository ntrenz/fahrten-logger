package ntrp.fahrtenlogger.plugins;

import com.opencsv.bean.CsvBindByName;

public class FuelRecordBean extends CsvBean {
    @CsvBindByName
    private int id;
    @CsvBindByName(column = "fuel_type")
    private String fuelType;
    @CsvBindByName
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuel_type) {
        this.fuelType = fuel_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
