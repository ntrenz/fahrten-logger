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
}
