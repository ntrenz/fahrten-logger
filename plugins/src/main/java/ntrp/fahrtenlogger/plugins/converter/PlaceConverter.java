package ntrp.fahrtenlogger.plugins.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.plugins.TripRecordBean;

public class PlaceConverter extends AbstractBeanField<TripRecordBean, String> {
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        try {
            return new Place(value);
        } catch (Exception e) {
            throw new CsvDataTypeMismatchException(e.getMessage());
        }
    }
}
