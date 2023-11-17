package ntrp.fahrtenlogger.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutoTest {
    @Test
    public void CreateAuto() {
        String auto_make = "VW";
        Auto a = new Auto(auto_make);

        assertEquals(a.make, auto_make);
    }
}