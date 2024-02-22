package ntrp.fahrtenlogger.plugins;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import ntrp.fahrtenlogger.application.DataHandlerInterface;

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
}
