package ntrp.fahrtenlogger.domain.ValueObjects;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * The Euro record represents an amount in Euros.
 */
public record Euro(double amount) {

    /**
     * Constructs a new Euro with the specified amount.
     * Throws an IllegalArgumentException if the amount is negative.
     *
     * @param amount the amount in Euros
     */
    public Euro {
        if (amount < 0) {
            throw new IllegalArgumentException("Euro amount cannot be negative.");
        }
    }

    /**
     * Adds the specified Euro amount to this Euro.
     *
     * @param euro the Euro amount to add
     * @return a new Euro representing the sum of this Euro and the specified Euro
     */
    public Euro addAmount(Euro euro) {
        return new Euro(this.amount + euro.amount());
    }

    /**
     * Subtracts the specified Euro amount from this Euro.
     *
     * @param euro the Euro amount to subtract
     * @return a new Euro representing the difference between this Euro and the specified Euro
     */
    public Euro subAmount(Euro euro) {
        return new Euro(this.amount - euro.amount());
    }

    /**
     * Formats this Euro amount as a string in the format used in Germany.
     *
     * @return a string representation of this Euro amount in the format used in Germany
     */
    public String format() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        return format.format(amount);
    }
}