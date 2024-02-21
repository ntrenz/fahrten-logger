package ntrp.fahrtenlogger.plugins;

import com.opencsv.bean.CsvBindByName;

public class TripBean extends CsvBean {
    @CsvBindByName
    private int id;
    @CsvBindByName
    private int date;
    @CsvBindByName
    private String from;
    @CsvBindByName
    private String to;
    @CsvBindByName
    private float distance;

    @Override
    public String toString() {
        return String.format("Trip %d on %d from %s to %s (%.2f km)", id, date, from, to, distance);
    }
}