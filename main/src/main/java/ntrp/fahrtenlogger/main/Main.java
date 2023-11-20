package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.domain.Refuelling;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.FuelRecordBean;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataHandler d = new DataHandler();

        List<Refuelling> refuelling = d.beanBuilder(ntrp.fahrtenlogger.plugins.FuelRecordBean.class);

        System.out.println(refuelling);
    }
}
