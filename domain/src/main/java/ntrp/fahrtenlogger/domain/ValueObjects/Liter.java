package ntrp.fahrtenlogger.domain.ValueObjects;

/**
 * The Liter record represents a volume in liters.
 */
public record Liter(double volume) {

    /**
     * The SI unit code for liters.
     */
    private static final char SI_CODE = 'l';

    /**
     * Constructs a new Liter with the specified volume.
     * Throws an IllegalArgumentException if the volume is negative.
     *
     * @param volume the volume in liters
     */
    public Liter {
        if (volume < 0) {
            throw new IllegalArgumentException("Volume in liters cannot be negative.");
        }
    }

    /**
     * Adds the specified Liter volume to this Liter.
     *
     * @param liter the Liter volume to add
     * @return a new Liter representing the sum of this Liter and the specified Liter volume
     */
    public Liter addVolume(Liter liter) {
        return new Liter(this.volume + liter.volume());
    }

    /**
     * Subtracts the specified Liter volume from this Liter.
     *
     * @param liter the Liter volume to subtract
     * @return a new Liter representing the difference between this Liter and the specified Liter volume
     */
    public Liter subVolume(Liter liter) {
        return new Liter(this.volume - liter.volume());
    }

    /**
     * Formats this Liter volume as a string in the format used in the SI system.
     *
     * @return a string representation of this Liter volume in the format used in the SI system
     */
    public String format() {
        return String.format("%.2f %s", volume, SI_CODE);
    }
}