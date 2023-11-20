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
import ntrp.fahrtenlogger.domain.Refuelling;

public class DataHandler {
    private Path fuel_data_path = Paths.get("plugins/src/main/resources/FUEL_DATA.csv");
    private Path car_data_path = Paths.get("plugins/src/main/resources/CAR_DATA.csv");

    public DataHandler(Path fuel_data_path, Path car_data_path) {
        this.fuel_data_path = fuel_data_path;
        this.car_data_path = car_data_path;
    }

    public DataHandler() { }

    public List<Refuelling> readFuelData() {
        List<Refuelling> refuellings = new ArrayList<Refuelling>();
        try (Reader reader = Files.newBufferedReader(this.fuel_data_path)) {
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
        try (Reader reader = Files.newBufferedReader(fuel_data_path)) {
            //            for (FuelRecordBean b : cb) {
//                out.add(new Refuelling(b.getId(), b.getFuelType(), b.getAmount()));
//            }
            CsvToBean<FuelRecordBean> cb = new CsvToBeanBuilder<FuelRecordBean>(reader)
                    .withType(c)
                    .build();
            return cb.parse().stream().toList();
        }
    }

    public void beanWriter(List<Refuelling> data) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(String.valueOf(fuel_data_path))) {
            StatefulBeanToCsv<Refuelling> sbc = new StatefulBeanToCsvBuilder<Refuelling>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(data);
        }
    }
}
