package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Entities.GasStation;
import ntrp.fahrtenlogger.domain.Entities.Refuel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RefuelRepositoryTest {

    private DataHandlerInterface dataHandler;
    private RefuelRepository repository;

    @BeforeEach
    public void setUp() {
        dataHandler = Mockito.mock(DataHandlerInterface.class);
    }

    @AfterEach
    public void resetMocks() {
        reset(dataHandler);
        Field instance;
        try {
            instance = RefuelRepository.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        repository = null;
    }

    @Test
    public void testGetNextRefuelId() {
        Refuel refuel1 = Mockito.mock(Refuel.class);
        Refuel refuel2 = Mockito.mock(Refuel.class);
        when(refuel1.getId()).thenReturn(1);
        when(refuel2.getId()).thenReturn(2);

        when(dataHandler.readAllRefuels()).thenReturn(Arrays.asList(refuel1, refuel2));

        repository = RefuelRepository.getInstance(dataHandler);

        assertEquals(3, repository.getNextRefuelId());
    }

    @Test
    public void testReadRefuels() {
        GasStation gasStation = Mockito.mock(GasStation.class);
        LocalDate date = LocalDate.of(2020, 1, 1);

        Refuel refuel = Mockito.mock(Refuel.class);
        when(refuel.getGasStation()).thenReturn(gasStation);
        when(refuel.getDate()).thenReturn(date);

        when(dataHandler.readAllRefuels()).thenReturn(List.of(refuel));

        repository = RefuelRepository.getInstance(dataHandler);
        List<Refuel> refuels = repository.readRefuels(date, gasStation);

        assertEquals(1, refuels.size());
        assertTrue(refuels.contains(refuel));
    }

    @Test
    public void testWriteRefuel() {
        Refuel refuel = Mockito.mock(Refuel.class);

        repository = RefuelRepository.getInstance(dataHandler);
        repository.writeRefuel(refuel);

        verify(dataHandler).saveRefuels(new ArrayList<>(Collections.singletonList(refuel)));
    }

    @Test
    public void testDeleteRefuel() {
        Refuel refuel1 = Mockito.mock(Refuel.class);
        Refuel refuel2 = Mockito.mock(Refuel.class);
        when(refuel1.getId()).thenReturn(2);
        when(refuel2.getId()).thenReturn(1);
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 1, 2);
        when(refuel1.getDate()).thenReturn(date1);
        when(refuel2.getDate()).thenReturn(date2);

        when(dataHandler.readAllRefuels()).thenReturn(new ArrayList<>(Arrays.asList(refuel1, refuel2)));

        repository = RefuelRepository.getInstance(dataHandler);
        repository.deleteRefuel(refuel1);

        verify(dataHandler).saveRefuels(List.of(refuel2));
    }

    @Test
    public void testSaveRefuels() {
        Refuel refuel = Mockito.mock(Refuel.class);

        when(dataHandler.readAllRefuels()).thenReturn(Collections.singletonList(refuel));

        repository = RefuelRepository.getInstance(dataHandler);
        repository.saveRefuels();

        verify(dataHandler).saveRefuels(Collections.singletonList(refuel));
    }
}