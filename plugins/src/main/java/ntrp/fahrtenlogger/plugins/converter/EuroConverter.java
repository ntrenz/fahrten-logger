package ntrp.fahrtenlogger.plugins.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.plugins.FuelRecordBean;

/**
 * Class for converting a string distance to a Euro object.
 * <p>
 * This class extends AbstractBeanField and overrides the convert method to convert a string distance to a Euro object.
 */
public class EuroConverter extends AbstractBeanField<FuelRecordBean, String> {
    /**
     * Converts a string distance to a Euro object.
     *
     * @param value The string distance to be converted.
     * @return A Euro object representing the distance.
     * @throws CsvDataTypeMismatchException If the conversion fails.
     */
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        try {
            return new Euro(Double.parseDouble(value.trim()));
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("The distance '" + value + "' cannot be converted to a Euro object.", e.getClass());
        }
    }
}