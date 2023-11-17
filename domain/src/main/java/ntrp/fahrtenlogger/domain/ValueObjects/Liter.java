package ntrp.fahrtenlogger.domain.ValueObjects;

import java.util.Objects;

public class Liter {

    private static final char SI_UNIT = 'l';
    private final double volume;

    public Liter(double volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("Volume in liters cannot be negative.");
        }
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Liter liter = (Liter) o;
        return Double.compare(liter.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }

    @Override
    public String toString() {
        return String.format("%.2f " + SI_UNIT, volume);
    }
}