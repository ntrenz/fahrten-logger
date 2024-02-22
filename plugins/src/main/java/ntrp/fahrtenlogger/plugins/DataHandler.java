package ntrp.fahrtenlogger.plugins;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ntrp.fahrtenlogger.adapters.DataSaver;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.RepositoryInterface;

import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

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
                        new Place(attributes[2]),
                        new Place(attributes[3]),
                        new Kilometer(Float.parseFloat(attributes[4])),
                        LocalDate.parse(attributes[1], DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN))
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
     * @param <T> Type of CSV Reader
     * @throws Exception
     */
    public <T extends CsvBean> List<T> beanBuilder(Class<T> beanObject) throws Exception {
        try (Reader reader = Files.newBufferedReader((Path) beanObject.getDeclaredMethod("getPath").invoke(null))) {
            CsvToBean<T> csvToBeanReader = new CsvToBeanBuilder<T>(reader)
                    .withType(beanObject)
                    .build();
            return csvToBeanReader.parse().stream().toList();
        }
    }

    /**
     * Generic implementation of a CSV writer accepting every class T implementing the interface {@link CsvBean}.
     * @param <T> Type of CSV Reader
     * @param beanObjects list of bean object classes of data type T
     * @return Boolean if writing was successfull
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public <T extends CsvBean> boolean beanWriter(List<T> beanObjects) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(String.valueOf((Path) beanObjects.get(0).getClass().getDeclaredMethod("getPath").invoke(null)))) {
            StatefulBeanToCsv<T> beanToCsvReader = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar('\"')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            beanToCsvReader.write(beanObjects);
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
