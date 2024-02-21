package ntrp.fahrtenlogger.plugins;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.Refuelling;

public class DataHandler {

    private Path fuelDataPath = Paths.get("plugins/src/main/resources/FUEL_DATA.csv");
    private Path carDataPath = Paths.get("plugins/src/main/resources/CAR_DATA.csv");

    private static final Path TRIPS_PATH = Paths.get("plugins/src/main/resources/trips.csv");


    public DataHandler(Path fuelDataPath, Path carDataPath) {
        this.fuelDataPath = fuelDataPath;
        this.carDataPath = carDataPath;
    }

    public DataHandler() {}

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(TRIPS_PATH);
                CSVReader csvReader = new CSVReader(reader)
        ) {
            csvReader.readAll().stream().skip(1).map(line -> {
                String[] attributes = line[0].split(";");
                return new Trip(
                        Integer.parseInt(attributes[0]),
                        Integer.parseInt(attributes[1]),
                        attributes[2],
                        attributes[3],
                        Float.parseFloat(attributes[4])
                );
            }).forEach(trips::add);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return trips;
    }

    public List<Refuelling> readFuelData() {
        List<Refuelling> refuellings = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(this.fuelDataPath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                for (String[] fuelRecord : csvReader.readAll()) {

                }

                return refuellings;
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FuelRecordBean> beanBuilder(Class<FuelRecordBean> c) throws Exception {
        try (Reader reader = Files.newBufferedReader(fuelDataPath)) {
            CsvToBean<FuelRecordBean> cb = new CsvToBeanBuilder<FuelRecordBean>(reader)
                    .withType(c)
                    .build();
            return cb.parse().stream().toList();
        }
    }

    public List<TripBean> buildBean(Class<TripBean> c) throws Exception {
        try (Reader reader = Files.newBufferedReader(TRIPS_PATH)) {
            CsvToBean<TripBean> tripBean = new CsvToBeanBuilder<TripBean>(reader)
                    .withType(c)
                    .build();
            return tripBean.parse().stream().toList();
        }
    }

    public void beanWriter(List<Refuelling> data) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(String.valueOf(fuelDataPath))) {
            StatefulBeanToCsv<Refuelling> sbc = new StatefulBeanToCsvBuilder<Refuelling>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(data);
        }
    }
}
