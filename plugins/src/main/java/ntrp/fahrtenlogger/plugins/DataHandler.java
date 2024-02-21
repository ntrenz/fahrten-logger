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
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ntrp.fahrtenlogger.adapters.DataSaver;
import ntrp.fahrtenlogger.domain.RepositoryInterface;

import ntrp.fahrtenlogger.domain.Entities.Trip;

public class DataHandler implements DataSaver {
    private Path fuel_data_path = Paths.get("plugins/src/main/resources/FUEL_DATA.csv");
    private Path car_data_path = Paths.get("plugins/src/main/resources/CAR_DATA.csv");
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

    /**
     * Generic implementation of a CSV reader accepting every class T implementing the interface {@link CsvBean}.
     * @param beanObject class of data type
     * @return List of data objects of type T
     * @param <T>
     * @throws Exception TODO
     */
    public <T extends CsvBean> List<T> beanBuilder(Class<T> beanObject) throws Exception {
        try (Reader reader = Files.newBufferedReader(T.getPath())) {
            CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                    .withType(beanObject)
                    .build();
            return cb.parse().stream().toList();
        }
    }

    public <T extends CsvBean> boolean beanWriter(List<T> beanObjects) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(String.valueOf(T.getPath()))) {
            StatefulBeanToCsv<CsvBean> sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write((CsvBean) beanObjects);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveAllRepositories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllRepositories'");
    }

    @Override
    public boolean saveRepository(RepositoryInterface repository) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveRepository'");
    }
}
