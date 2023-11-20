package ntrp.fahrtenlogger.domain.ValueObjects;

import java.util.Objects;

public class Kilometer {

    private static final String SI_CODE = "km";
    private final double value;

    public Kilometer(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Kilometer value cannot be negative.");
        }
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kilometer kilometer = (Kilometer) o;
        return Double.compare(kilometer.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + " " + SI_CODE;
    }
}