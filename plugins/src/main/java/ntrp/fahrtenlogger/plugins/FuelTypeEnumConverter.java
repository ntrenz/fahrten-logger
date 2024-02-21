package ntrp.fahrtenlogger.plugins;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import ntrp.fahrtenlogger.domain.data.FuelType;

public class FuelTypeEnumConverter extends AbstractBeanField<FuelRecordBean, String> {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        try {
            return FuelType.valueOf(value.trim());
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException(e.getMessage());
        }
    }
}
