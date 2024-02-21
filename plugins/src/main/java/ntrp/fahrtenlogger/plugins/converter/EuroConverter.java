package ntrp.fahrtenlogger.plugins.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.plugins.FuelRecordBean;

/**
 * Class for converting a string value to a Euro object.
 * <p>
 * This class extends AbstractBeanField and overrides the convert method to convert a string value to a Euro object.
 */
public class EuroConverter extends AbstractBeanField<FuelRecordBean, String> {
    /**
     * Converts a string value to a Euro object.
     *
     * @param value The string value to be converted.
     * @return A Euro object representing the value.
     * @throws CsvDataTypeMismatchException If the conversion fails.
     */
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        try {
            return new Euro(Double.parseDouble(value.trim()));
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("The value '" + value + "' cannot be converted to a Euro object.", e.getClass());
        }
    }
}