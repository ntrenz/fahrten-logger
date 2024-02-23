package ntrp.fahrtenlogger.plugins;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.Entities.Trip;

public class DataHandler implements DataHandlerInterface {

    public DataHandler() {}

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
    public List<Refuel> readAllRefuels() {
        List<FuelRecordBean> refuelBeans;
        List<Refuel> refuels = new ArrayList<>();
        try {
            refuelBeans = beanBuilder(ntrp.fahrtenlogger.plugins.FuelRecordBean.class);
            refuelBeans
                .iterator()
                .forEachRemaining(refuelBean -> refuels.add(new Refuel(refuelBean.getId(), refuelBean.getAmount(), refuelBean.getPricePerLiter(), refuelBean.getFuelType(), null, refuelBean.getDate())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refuels;
    }

    @Override
    public List<Trip> readAllTrips() {
        List<TripRecordBean> tripBeans;
        List<Trip> trips = new ArrayList<>();
        try {
            tripBeans = beanBuilder(ntrp.fahrtenlogger.plugins.TripRecordBean.class);
            tripBeans
                .iterator()
                .forEachRemaining(tripBean -> trips.add(new Trip(tripBean.getId(), tripBean.getFrom(), tripBean.getTo(), tripBean.getDistance(), tripBean.getDate())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public void saveRefuels(List<Refuel> refuels) {
        List<FuelRecordBean> refuelBeans = new ArrayList<>();
        refuels.forEach(refuel -> refuelBeans.add(new FuelRecordBean(refuel.getId(), refuel.getFuelType(), refuel.getLiters(), refuel.getPricePerLiter(), refuel.getDate())));
        
        try {
            beanWriter(refuelBeans);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTrips(List<Trip> trips) {
        List<TripRecordBean> tripBeans = new ArrayList<>();
        trips.forEach(trip -> tripBeans.add(new TripRecordBean(trip.getId(), trip.getDate(), trip.getFrom(), trip.getTo(), trip.getDistance())));
        
        try {
            beanWriter(tripBeans);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException | IOException e) {
            e.printStackTrace();
        }
    }
}
