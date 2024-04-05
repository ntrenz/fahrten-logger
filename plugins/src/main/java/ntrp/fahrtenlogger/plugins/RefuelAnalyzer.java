package ntrp.fahrtenlogger.plugins;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * The 'RefuelAnalyzer' class provides functionality for analyzing refuel events.
 * It can calculate total fuel volume refueled, the average price per liter,
 * and provide a detailed analysis for a specific gas station and date.
 */
public class RefuelAnalyzer {

    /**
     * List of refuels to analyze.
     */
    private final List<Refuel> refuels;

    /**
     * Print instance used for pretty-printing output.
     */
    private final Print print;

    /**
     * Formatter for dates.
     */
    private final DateTimeFormatter dateTimeFormatter;

    /**
     * Constructs a new RefuelAnalyzer with a list of refuels.
     *
     * @param refuels the list of refuels to be analyzed.
     */
    public RefuelAnalyzer(List<Refuel> refuels) {
        this.refuels = refuels;
        print = Print.getInstance();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    }

    /**
     * Calculates the total volume refueled in the provided list of refuels.
     *
     * @param refuels the list of refuels.
     * @return the total volume refueled.
     */
    public Liter getTotalVolume(List<Refuel> refuels) {
        return new Liter(
                refuels.stream()
                        .map(Refuel::getLiters)
                        .map(Liter::volume)
                        .reduce(0., Double::sum)
        );
    }

    /**
     * Calculates the average price per liter from the provided list of refuels.
     *
     * Returns 0.0 if the list is empty to avoid division by zero.
     *
     * @param refuels the list of refuels.
     * @return the average price per liter.
     */
    public Euro getAveragePricePerLiter(List<Refuel> refuels) {
        if (refuels.isEmpty()) return new Euro(0.0);

        double totalSum = refuels.stream()
                .map(Refuel::getPricePerLiter)
                .map(Euro::amount)
                .reduce(0., Double::sum);

        double average = totalSum / refuels.size();

        return new Euro(average);
    }

    /**
     * Provides a detailed analysis for refuels at a specific gas station on a certain date.
     * If the station or date are null, refuels are not filtered for these parameters.
     * <p>
     * This analysis includes the total number of refuels, the gas station (if provided),
     * the date (if provided), total volume refueled, and the average price.
     *
     * @param gasStation the gas station where the refuels occurred.
     * @param date the date when the refuels occurred.
     * @return a string containing a detailed analysis.
     */
    public String getAnalysis(GasStation gasStation, LocalDate date) {
        List<Refuel> filteredRefuels = refuels.stream().filter(refuel ->
                (gasStation == null || refuel.getGasStation().equals(gasStation)) &&
                (date == null || refuel.getDate().equals(date))
        ).toList();

        boolean gasStationProvided = gasStation != null;
        boolean dateProvided = date != null;

        int count = filteredRefuels.size();
        Liter totalVolume = getTotalVolume(filteredRefuels);
        Euro averagePricePerLiter = getAveragePricePerLiter(filteredRefuels);

        StringBuilder analysis = new StringBuilder();
        analysis.append(print.bold(print.color(String.valueOf(count), Color.ORANGE)));
        analysis.append(count == 1 ? " refuel" : " refuels");
        analysis.append(" recorded");

        if (gasStationProvided) {
            analysis.append(" at a gas station in ");
            analysis.append(print.bold(print.color(gasStation.toString(), Color.ORANGE)));
        }

        if (dateProvided) {
            analysis.append(" on ");
            analysis.append(print.bold(print.color(dateTimeFormatter.format(date), Color.ORANGE)));
        }

        analysis.append(" with a total volume of ");
        analysis.append(print.bold(print.color(totalVolume.format(), Color.ORANGE)));
        analysis.append(" at an average price of ");
        analysis.append(print.bold(print.color(averagePricePerLiter.format() + "/l", Color.ORANGE)));

        return analysis.toString();
    }
}
