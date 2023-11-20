package ntrp.fahrtenlogger.domain.ValueObjects;

import java.util.Objects;

public class Euro {
    /**
     * Currency Code of Euro {@value}
     */
    private static final char CURRENCY_CODE = '€';

    /**
     * Amount of this ValueObject as {@code double}
     */
    private final double amount;

    public Euro(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Euro amount cannot be negative.");
        }
        this.amount = amount;
    }

    /**
     * Gibt die Menge dieses Objekts zurück.
     * @author robin13091
     * @return {@code double (#{@amount})}
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Addiert ein Euro Objekt auf dieses drauf.
     * @param euro Second Euro Object
     * @return Euro
     */
    public Euro addAmount(Euro euro) {
        return new Euro(this.amount + euro.getAmount());
    }

    /**
     * Subtrahiert ein Euro Objekt von diesem.
     * @param euro
     * @return Euro
     */
    public Euro subAmount(Euro euro) {
        return new Euro(this.amount - euro.getAmount());
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