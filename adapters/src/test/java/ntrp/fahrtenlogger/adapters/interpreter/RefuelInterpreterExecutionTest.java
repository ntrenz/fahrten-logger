package ntrp.fahrtenlogger.adapters.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ntrp.fahrtenlogger.application.DataHandlerInterface;
import ntrp.fahrtenlogger.application.RefuelRepository;
import ntrp.fahrtenlogger.application.TripRepository;
import ntrp.fahrtenlogger.domain.Entities.Refuel;

@ExtendWith(MockitoExtension.class)
public class RefuelInterpreterExecutionTest {
    @Mock(name = "dataHandler")
    static DataHandlerInterface mockedDataHandler;
    @Mock(name = "refuelRepository")
    static RefuelRepository mockedRefuelRepository;
    @Mock
    List<Refuel> mockedRefuelList;
    @Mock(name = "action")
    Actions mockedAction;

    @InjectMocks
    RefuelInterpreter refuelInterpreter;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeAll
    static void testSetup() {
        mockedDataHandler = Mockito.mock(DataHandlerInterface.class);
        Mockito.when(mockedDataHandler.readAllRefuels()).thenReturn(new ArrayList<Refuel>());
        try (MockedStatic<RefuelRepository> mockedStaticRefuelRepository = mockStatic(RefuelRepository.class)) {
            mockedStaticRefuelRepository.when(() -> RefuelRepository.getInstance(mockedDataHandler))
                    .thenReturn(mockedRefuelRepository);
        }
    }

    @BeforeEach
    void methodSetup() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void methodTeardown() {
        reset(mockedDataHandler);
        reset(mockedRefuelList);
        System.setOut(originalOut);
    }

    @Test
    void executeNewCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.NEW.ordinal());
        Mockito.doNothing().when(mockedRefuelRepository).writeRefuel(any());

        refuelInterpreter.executeCommands();

        verify(mockedRefuelRepository, times(1)).writeRefuel(any());
    }

    @Test
    void executeReadCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.READ.ordinal());
        when(mockedRefuelList.iterator()).thenReturn(new Iterator<Refuel>() {
            private final List<Refuel> items = List.of(new Refuel(1, null, null, null, null, null));
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
            public Refuel next() {
                return items.get(++iter);
            }
        });
        Mockito.when(mockedRefuelRepository.readRefuels(any(), any())).thenReturn(mockedRefuelList);

        refuelInterpreter.executeCommands();

        verify(mockedRefuelRepository, times(1)).readRefuels(any(), any());
        assertEquals(mockedRefuelList.stream().map(String::valueOf).collect(Collectors.joining()),
                outContent.toString());
    }

    @Test
    void executeDeleteCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.DELETE.ordinal());
        Mockito.doNothing().when(mockedRefuelRepository).deleteRefuel(any());

        refuelInterpreter.executeCommands();

        verify(mockedRefuelRepository, times(1)).deleteRefuel(any());
    }

    @Test
    void executeModifyCommand() {
        when(mockedAction.ordinal()).thenReturn(Actions.MODIFY.ordinal());

        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            refuelInterpreter.executeCommands();
        });
        assertEquals("Unimplemented case: " + mockedAction.toString(), exception.getMessage());
    }
}
