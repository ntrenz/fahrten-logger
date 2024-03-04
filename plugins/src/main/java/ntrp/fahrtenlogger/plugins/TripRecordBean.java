package ntrp.fahrtenlogger.plugins;

import java.nio.file.Paths;
import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.plugins.converter.DateConverterTrip;
import ntrp.fahrtenlogger.plugins.converter.KilometerConverter;
import ntrp.fahrtenlogger.plugins.converter.PlaceConverter;

import java.nio.file.Path;

public class TripRecordBean extends CsvBean {
    private final Path path = Paths.get("plugins/src/main/resources/TRIP_DATA.csv");
    private final String headerLine = "\"DATE\",\"DISTANCE\",\"FROM_PLACE\",\"ID\",\"TO_PLACE\"";

    @CsvBindByName
    private int id;
    @CsvCustomBindByName(column = "DATE", converter = DateConverterTrip.class)
    private LocalDate date;
    @CsvCustomBindByName(column = "FROM_PLACE", converter = PlaceConverter.class)
    private Place from;
    @CsvCustomBindByName(column = "TO_PLACE", converter = PlaceConverter.class)
    private Place to;
    @CsvCustomBindByName(column = "DISTANCE", converter = KilometerConverter.class)
    private Kilometer distance;

    public TripRecordBean() {}

    public TripRecordBean(int id, LocalDate date, Place from, Place to, Kilometer distance) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Path getPath() {
        return path;
    };

    public String getHeaderLine() {
        return headerLine;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Place getFrom() {
        return from;
    }

    public Place getTo() {
        return to;
    }

    public Kilometer getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public void setTo(Place to) {
        this.to = to;
    }

    public void setDistance(Kilometer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "TripRecordBean [id=" + id + ", date=" + date + ", from=" + from + ", to=" + to + ", distance="
                + distance + "]";
    }
}