package ntrp.fahrtenlogger.plugins;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;
import ntrp.fahrtenlogger.plugins.converter.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FuelRecordBean extends CsvBean {
    private final Path path = Paths.get("plugins/src/main/resources/FUEL_DATA.csv");
    private final String headerLine = "\"AMOUNT\",\"DATE\",\"FUEL_TYPE\",\"ID\",\"PRICE_PER_LITER\"";

    @CsvBindByName
    private int id;
    @CsvCustomBindByName(column = "FUEL_TYPE", converter = FuelTypeEnumConverter.class)
    private FuelType fuelType;
    @CsvCustomBindByName(column = "AMOUNT", converter = LiterConverter.class)
    private Liter amount;
    @CsvCustomBindByName(column = "PRICE_PER_LITER", converter = EuroConverter.class)
    private Euro pricePerLiter;
    @CsvCustomBindByName(column = "DATE", converter = DateConverter.class)
    private LocalDate date;

    public FuelRecordBean() {}

    public FuelRecordBean(int id, FuelType fuelType, Liter amount, Euro pricePerLiter, LocalDate date) {
        this.id = id;
        this.fuelType = fuelType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
        this.date = date;
    }

    public Path getPath() {
        return path;
    };
    
    public String getHeaderLine() {
        return headerLine;
    }

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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuel_type) {
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
