package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.domain.Entities.Trip;
import ntrp.fahrtenlogger.plugins.DataHandler;
import ntrp.fahrtenlogger.plugins.TripAnalyzer;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        DataHandler dataHandler = new DataHandler();

        List<Trip> trips = dataHandler.getAllTrips();
        for(Trip trip : trips) System.out.println(trip.toString());
        TripAnalyzer tripAnalyzer = new TripAnalyzer(trips);
        String totalDistanceFormatted = String.format("%.1f km", tripAnalyzer.getTotalDistance());
        System.out.println("Trips Gesamt = " + tripAnalyzer.getCount());
        System.out.println("Gesamtdistanz = " + totalDistanceFormatted);

//        List<TripBean> trips = dataHandler.buildBean(TripBean.class);
//        trips.iterator().forEachRemaining(c -> System.out.println(c.toString()));
    }
}
