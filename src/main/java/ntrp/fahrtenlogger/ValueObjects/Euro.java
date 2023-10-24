package ntrp.fahrtenlogger.ValueObjects;

import java.util.Objects;

public class Euro {

    private static final char CURRENCY_CODE = '€';
    private final double amount;

    public Euro(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Euro amount cannot be negative.");
        }
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Euro euro = (Euro) o;
        return Double.compare(euro.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return String.format("%.2f " + CURRENCY_CODE, amount);
    }
}