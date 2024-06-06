package ntrp.fahrtenlogger.adapters.interpreter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

public final class ArgumentsParser {
    public static Liter parseLiterFrom(String volume) throws IllegalArgumentException {
        try {
            return new Liter(Double.parseDouble(volume));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Liter must be a number! (Number format for Liter is: X.X)");
        }
    }

    public static Euro parseEuroFrom(String amount) throws IllegalArgumentException {
        try {
            return new Euro(Double.parseDouble(amount));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Currency (EURO) must be a number! (Number format for EURO is: X.X)");
        }
    }

    public static Kilometer parseKilometerFrom(String kilometer) throws IllegalArgumentException {
        try {
            return new Kilometer(Double.parseDouble(kilometer));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Distance must be a number! (Number format for Distances is: X.X)");
        }
    }

    public static int parseIdFrom(String id) throws IllegalArgumentException {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID must be an integer!");
        }
    }

    public static LocalDate parseDateFrom(String date) throws IllegalArgumentException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        } catch (Exception e) {
            throw new IllegalArgumentException("Date could not be parsed! (Date format must be: DD.MM.YYYY)");
        }
    }

    public static Place parsePlaceFrom(String place) throws IllegalArgumentException {
        try {
            return new Place(place);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static FuelType parseFuelTypeFrom(String fuelType) throws IllegalArgumentException {
        try {
            return FuelType.valueOf(fuelType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Fuel Type '" + fuelType.toUpperCase() + "' is not defined!");
        }
    }

    public static GasStation parseGasStationFrom(String gasStationName) throws IllegalArgumentException {
        try {
            return new GasStation(gasStationName);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
