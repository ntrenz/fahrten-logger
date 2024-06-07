package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.ValueObjects.Euro;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.domain.ValueObjects.Liter;
import ntrp.fahrtenlogger.domain.data.FuelType;

public class ArgumentsParserTest {

    @InjectMocks
    public static ArgumentsParser argumentsParser;

    @Test
    void testParseDateFrom() {
        String dateString = "01.03.2024";
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN));
        
        LocalDate localDate = ArgumentsParser.parseDateFrom(dateString);

        assertEquals(date, localDate);
    }

    @Test
    void testParseDateFromWrongInput() {
        String dateString = "2024-03-01";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseDateFrom(dateString);
        });
        assertEquals("Date could not be parsed! (Date format must be: DD.MM.YYYY)", exception.getMessage());
    }

    @Test
    void testParseDateFromWrongDate() {
        String dateString = "01.13.2024";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseDateFrom(dateString);
        });
        assertEquals("Date could not be parsed! (Date format must be: DD.MM.YYYY)", exception.getMessage());
    }

    @Test
    void testParseEuroFrom() {
        Euro euro = new Euro(20);

        Euro euroTest = ArgumentsParser.parseEuroFrom(euro.toString());

        assertEquals(euro, euroTest);
    }

    @Test
    void testParseEuroFromWrongInput() {
        String euroString =  "30,5";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseEuroFrom(euroString);
        });
    }

    @Test
    void testParseFuelTypeFrom() {
        FuelType fuelType = FuelType.DIESEL;

        FuelType fuelTypeTest = ArgumentsParser.parseFuelTypeFrom(fuelType.toString());

        assertEquals(fuelType, fuelTypeTest);
    }

    @Test
    void testParseFuelTypeFromWrongInput() {
        String fuelType = "DISEL";
        
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseFuelTypeFrom(fuelType);
        });
        assertEquals("Fuel Type '" + fuelType.toUpperCase() + "' is not defined!", exception.getMessage());
    }

    @Test
    void testParseGasStationFrom() {

    }

    @Test
    void testParseIdFrom1() {
        String id = "1";

        int idTest = ArgumentsParser.parseIdFrom(id);

        assertEquals(Integer.parseInt(id), idTest);
    }

    @Test
    void testParseIdFromMinus1() {
        String id = "-1";

        int idTest = ArgumentsParser.parseIdFrom(id);

        assertEquals(Integer.parseInt(id), idTest);
    }

    @Test
    void testParseIdFromWrongInput() {
        String id = "1.1";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseIdFrom(id);
        });
        assertEquals("ID must be an integer!", exception.getMessage());
    }

    @Test
    void testParseKilometerFrom() {
        Kilometer kilometer = new Kilometer(50);

        Kilometer kilometerTest = ArgumentsParser.parseKilometerFrom(kilometer.toString());

        assertEquals(kilometer, kilometerTest);
    }

    @Test
    void testParseKilometerFromWrongInput() {
        String kilometer = "50,1";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseKilometerFrom(kilometer);
        });
        assertEquals("Distance must be a number! (Number format for Distances is: X.X)", exception.getMessage());
    }

    @Test
    void testParseLiterFrom() {
        Liter liter = new Liter(2);

        Liter literTest = ArgumentsParser.parseLiterFrom(liter.toString());

        assertEquals(liter, literTest);
    }

    @Test
    void testParseLiterFromWrongInput() {
        String liter = "2,1";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseLiterFrom(liter);
        });
    }

    @Test
    void testParsePlaceFrom() {
        Place place = new Place("PLACE");

        Place placeTest = ArgumentsParser.parsePlaceFrom(place.toString());

        assertEquals(place, placeTest);
    }

    @Test
    void testParsePlaceFromEmptyString() {
        String emptyPlaceName = "";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parsePlaceFrom(emptyPlaceName);
        });
    }

    @Test
    void testParseGasStation() {
        GasStation gasStation = new GasStation("PLACE");

        GasStation gasStationTest = ArgumentsParser.parseGasStationFrom(gasStation.toString());

        assertEquals(gasStation, gasStationTest);
    }

    @Test
    void testParseGasStationEmptyString() {
        String emptyGasStationName = "";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            ArgumentsParser.parseGasStationFrom(emptyGasStationName);
        });
    }
}
