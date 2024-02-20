package ntrp.fahrtenlogger.plugins;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelTypes;
import ntrp.fahrtenlogger.plugins.converter.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FuelRecordBean extends CsvBean {
    static Path path = Paths.get("plugins/src/main/resources/FUEL_DATA.csv");

    @CsvBindByName
    private int id;
    @CsvCustomBindByName(column = "fuel_type", converter = FuelTypeEnumConverter.class)
    private FuelTypes fuelType;
    @CsvCustomBindByName(column = "amount", converter = LiterConverter.class)
    private Liter amount;
    @CsvCustomBindByName(column = "price_per_liter", converter = EuroConverter.class)
    private Euro pricePerLiter;
    @CsvCustomBindByName(column = "date", converter = DateConverter.class)
    private LocalDate date;

    public Euro getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(Euro pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FuelTypes getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelTypes fuel_type) {
        this.fuelType = fuel_type;
    }

    public Liter getAmount() {
        return amount;
    }

    public void setAmount(Liter amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FuelRecordBean{" +
                "id=" + id +
                ", fuelType=" + fuelType +
                ", amount=" + amount +
                ", pricePerLiter=" + pricePerLiter +
                ", date=" + date +
                '}';
    }
}
