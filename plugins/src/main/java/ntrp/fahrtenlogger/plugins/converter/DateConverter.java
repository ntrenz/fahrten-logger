package ntrp.fahrtenlogger.plugins.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import ntrp.fahrtenlogger.plugins.FuelRecordBean;

import java.time.LocalDate;


public class DateConverter extends AbstractBeanField<FuelRecordBean, String>  {
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        try {
            return LocalDate.parse(value.trim());
        } catch (RuntimeException e) {
            throw new CsvDataTypeMismatchException(e.getMessage());
        }
    }
}
