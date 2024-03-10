package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Entities.Place;
import ntrp.fahrtenlogger.domain.Entities.Trip;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TripRepositoryTest {

    private DataHandlerInterface dataHandler;
    private TripRepository repository;

    @BeforeEach
    public void setUp() {
        dataHandler = Mockito.mock(DataHandlerInterface.class);
    }

    @AfterEach
    public void resetMocks() {
        reset(dataHandler);
        Field instance;
        try {
            instance = TripRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        repository = null;
    }

    @Test
    public void testGetNextTripId() {
        Trip trip1 = Mockito.mock(Trip.class);
        Trip trip2 = Mockito.mock(Trip.class);
        when(trip1.getId()).thenReturn(1);
        when(trip2.getId()).thenReturn(2);

        when(dataHandler.readAllTrips()).thenReturn(Arrays.asList(trip1, trip2));

        repository = TripRepository.getInstance(dataHandler);

        assertEquals(3, repository.getNextTripId());
    }

    @Test
    public void testReadTrips() {
        Place from = Mockito.mock(Place.class);
        Place to = Mockito.mock(Place.class);
        LocalDate date = LocalDate.of(2020, 1, 1);

        Trip trip = Mockito.mock(Trip.class);
        when(trip.getFrom()).thenReturn(from);
        when(trip.getTo()).thenReturn(to);
        when(trip.getDate()).thenReturn(date);

        when(dataHandler.readAllTrips()).thenReturn(List.of(trip));

        repository = TripRepository.getInstance(dataHandler);
        List<Trip> trips = repository.readTrips(from, to, date);

        assertEquals(1, trips.size());
        assertTrue(trips.contains(trip));
    }

    @Test
    public void testWriteTrip() {
        Trip trip = Mockito.mock(Trip.class);

        repository = TripRepository.getInstance(dataHandler);
        repository.writeTrip(trip);

        verify(dataHandler).saveTrips(new ArrayList<>(Collections.singletonList(trip)));
    }

    @Test
    public void testDeleteTrip() {
        Trip trip1 = Mockito.mock(Trip.class);
        Trip trip2 = Mockito.mock(Trip.class);
        when(trip1.getId()).thenReturn(2);
        when(trip2.getId()).thenReturn(1);
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 1, 2);
        when(trip1.getDate()).thenReturn(date1);
        when(trip2.getDate()).thenReturn(date2);

        when(dataHandler.readAllTrips()).thenReturn(new ArrayList<>(Arrays.asList(trip1, trip2)));

        repository = TripRepository.getInstance(dataHandler);
        repository.deleteTrip(trip1);

        verify(dataHandler).saveTrips(List.of(trip2));
    }

    @Test
    public void testSaveTrips() {
        Trip trip = Mockito.mock(Trip.class);

        when(dataHandler.readAllTrips()).thenReturn(Collections.singletonList(trip));

        repository = TripRepository.getInstance(dataHandler);
        repository.saveTrips();

        verify(dataHandler).saveTrips(Collections.singletonList(trip));
    }
}