package ntrp.fahrtenlogger.plugins;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.Entities.Trip;

public class DataHandler implements DataHandlerInterface {

    private <T extends CsvBean> void initFiles(T beanObject) {
        try {
            File f = new File(String.valueOf(beanObject.getPath()));
            f.createNewFile();
            FileWriter fileWriter = new FileWriter(String.valueOf(beanObject.getPath()));
            fileWriter.write(beanObject.getHeaderLine());           
            fileWriter.close();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        } 
    }

    /**
     * Generic implementation of a CSV reader accepting every class T implementing the interface {@link CsvBean}.
     * @param beanObject class of data type
     * @return List of data objects of type T
     * @param <T> Type of CSV Reader
     * @throws Exception
     */
    public <T extends CsvBean> List<T> beanBuilder(Class<T> beanObject, T beanObject2) throws Exception {
        try (Reader reader = Files.newBufferedReader(beanObject2.getPath())) {
            CsvToBean<T> csvToBeanReader = new CsvToBeanBuilder<T>(reader)
                    .withType(beanObject)
                    .build();
            return csvToBeanReader.parse().stream().toList();
        } catch (IOException ioE) {
            initFiles(beanObject2);
            return beanBuilder(beanObject, beanObject2);
        }
    }

    /**
     * Generic implementation of a CSV writer accepting every class T implementing the interface {@link CsvBean}.
     * @param <T> Type of CSV Reader
     * @param beanObjects list of bean object classes of data type T
     * @return Boolean if writing was successfull
     * @throws Exception
     */
    public <T extends CsvBean> void beanWriter(List<T> beanObjects, T beanObject2) throws Exception {
        try (Writer writer = new FileWriter(String.valueOf(beanObject2.getPath()))) {
            StatefulBeanToCsv<T> beanToCsvWriter = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar('\"')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            beanToCsvWriter.write(beanObjects);
            if (beanObjects.isEmpty()) {
                initFiles(beanObject2);
            }
        } catch (IOException ioE) {
            initFiles(beanObject2);
            beanWriter(beanObjects, beanObject2);
        }
    }

    @Override
    public List<Refuel> readAllRefuels() {
        List<FuelRecordBean> refuelBeans;
        List<Refuel> refuels = new ArrayList<>();
        try {
            refuelBeans = beanBuilder(ntrp.fahrtenlogger.plugins.FuelRecordBean.class, new FuelRecordBean());
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
            tripBeans = beanBuilder(ntrp.fahrtenlogger.plugins.TripRecordBean.class, new TripRecordBean());
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
            beanWriter(refuelBeans, new FuelRecordBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTrips(List<Trip> trips) {
        List<TripRecordBean> tripBeans = new ArrayList<>();
        trips.forEach(trip -> tripBeans.add(new TripRecordBean(trip.getId(), trip.getDate(), trip.getFrom(), trip.getTo(), trip.getDistance())));
        
        try {
            beanWriter(tripBeans, new TripRecordBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
