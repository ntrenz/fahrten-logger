package ntrp.fahrtenlogger.application;

import java.util.List;

import ntrp.fahrtenlogger.application.analyzer.PrintInterface;
import ntrp.fahrtenlogger.application.analyzer.RefuelAnalyzer;
import ntrp.fahrtenlogger.application.analyzer.TripAnalyzer;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.Entities.Trip;

public class AnalyzerWrapper {
    public static void analyzeFor(Refuel refuel, RefuelRepository refuelRepository, PrintInterface print) {
        List<Refuel> refuels = refuelRepository.readRefuels(refuel.getDate(), refuel.getGasStation());

        RefuelAnalyzer refuelAnalyzer = new RefuelAnalyzer(refuels, print);
        System.out.println(refuelAnalyzer.getAnalysis(refuel.getGasStation(), refuel.getDate()));
    }

    public static void analyzeFor(Trip trip, TripRepository tripRepository, PrintInterface print) {
        List<Trip> trips = tripRepository.readTrips(trip.getFrom(), trip.getTo(), trip.getDate());

        TripAnalyzer tripAnalyzer = new TripAnalyzer(trips, print);
        System.out.println(tripAnalyzer.getAnalysis(trip.getFrom(), trip.getTo(), trip.getDate()));
    }
}
