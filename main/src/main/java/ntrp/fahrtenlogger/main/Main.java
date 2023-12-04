package ntrp.fahrtenlogger.main;

import com.opencsv.bean.CsvToBean;
import ntrp.fahrtenlogger.domain.Refuelling;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.domain.data.FuelTypes;
import ntrp.fahrtenlogger.plugins.CsvBean;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.FuelRecordBean;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataHandler d = new DataHandler();

        List<FuelRecordBean> refuelling = d.beanBuilder(FuelRecordBean.class);
        refuelling.get(1).setFuelType(FuelTypes.E5);
        refuelling.iterator().forEachRemaining( System.out::println );

        d.beanWriter(refuelling);
    }
}
