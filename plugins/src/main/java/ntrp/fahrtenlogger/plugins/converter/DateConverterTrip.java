package ntrp.fahrtenlogger.plugins.converter;

import java.time.LocalDate;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import ntrp.fahrtenlogger.plugins.TripRecordBean;

public class DateConverterTrip extends AbstractBeanField<TripRecordBean, String> {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        try {
            return LocalDate.parse(value.trim());
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException(e.getMessage());
        }
    }

}
