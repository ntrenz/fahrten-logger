package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.TripRepository;
import ntrp.fahrtenlogger.domain.Entities.Trip;

@ExtendWith(MockitoExtension.class)
public class TripInterpreterExecutionTest {
    @Mock(name = "dataHandler")
    static DataHandlerInterface mockedDataHandler;
    @Mock(name = "tripRepository")
    static TripRepository mockedTripRepository;
    @Mock
    List<Trip> mockedTripList;
    @Mock(name = "action")
    Actions mockedAction;

    @InjectMocks
    TripInterpreter tripInterpreter;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeAll
    static void testSetup() {
        mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
        Mockito.when(mockedDataHandler.readAllTrips()).thenReturn(new ArrayList<Trip>());
        Mockito.when(TripRepository.getInstance(mockedDataHandler)).thenReturn(mockedTripRepository);
    }

    @BeforeEach
    void methodSetup() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void methodTeardown() {
        reset(mockedDataHandler);
        reset(mockedTripList);
        System.setOut(originalOut);
    }

    @Test
    void executeNewCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.NEW.ordinal());
        Mockito.doNothing().when(mockedTripRepository).writeTrip(any());

        tripInterpreter.executeCommands();

        verify(mockedTripRepository, times(1)).writeTrip(any());
    }

    @Test
    void executeReadCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.READ.ordinal());
        when(mockedTripList.iterator()).thenReturn(new Iterator<Trip>() {
            private final List<Trip> items = List.of(new Trip(2, null, null, null, null), new Trip(2, null, null, null, null));
            private int iter = 0;

            @Override
            public boolean hasNext() {
                if (items.size() > iter) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Trip next() {
                return items.get(++iter);
            }
        });
        Mockito.when(mockedTripRepository.readTrips(any(), any(), any())).thenReturn(mockedTripList);

        tripInterpreter.executeCommands();

        verify(mockedTripRepository, times(1)).readTrips(any(), any(), any());
        assertEquals(mockedTripList.stream().map(String::valueOf).collect(Collectors.joining()),
                outContent.toString());
    }

    @Test
    void executeDeleteCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.DELETE.ordinal());
        Mockito.doNothing().when(mockedTripRepository).deleteTrip(any());

        tripInterpreter.executeCommands();

        verify(mockedTripRepository, times(1)).deleteTrip(any());
    }

    @Test
    void executeModifyCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.MODIFY.ordinal());

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            tripInterpreter.executeCommands();
        });
        assertEquals("Unimplemented case: " + mockedAction.toString(), exception.getMessage());
    }
}
