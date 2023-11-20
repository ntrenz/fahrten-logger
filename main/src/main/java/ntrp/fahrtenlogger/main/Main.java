package ntrp.fahrtenlogger.main;

import com.opencsv.bean.CsvToBean;
import ntrp.fahrtenlogger.domain.Refuelling;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.FuelRecordBean;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataHandler d = new DataHandler();

        List<FuelRecordBean> refuelling = d.beanBuilder(ntrp.fahrtenlogger.plugins.FuelRecordBean.class);
        refuelling.iterator().forEachRemaining( System.out::println );

//        d.beanWriter(refuelling);
    }
}
