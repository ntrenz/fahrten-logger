package ntrp.fahrtenlogger.domain.ValueObjects;

/**
 * The Kilometer record represents a distance in kilometers.
 */
public record Kilometer(double distance) {

    /**
     * The SI unit code for kilometers.
     */
    private static final String SI_CODE = "km";

    /**
     * Constructs a new Kilometer with the specified distance.
     * Throws an IllegalArgumentException if the distance is negative.
     *
     * @param distance the distance in kilometers
     */
    public Kilometer {
        if (distance < 0) {
            throw new IllegalArgumentException("Kilometer distance cannot be negative.");
        }
    }

    /**
     * Adds the specified Kilometer distance to this Kilometer.
     *
     * @param kilometer the Kilometer distance to add
     * @return a new Kilometer representing the sum of this Kilometer and the specified Kilometer distance
     */
    public Kilometer addDistance(Kilometer kilometer) {
        return new Kilometer(this.distance + kilometer.distance());
    }

    /**
     * Subtracts the specified Kilometer distance from this Kilometer.
     *
     * @param kilometer the Kilometer distance to subtract
     * @return a new Kilometer representing the difference between this Kilometer and the specified Kilometer distance
     */
    public Kilometer subDistance(Kilometer kilometer) {
        return new Kilometer(this.distance - kilometer.distance());
    }

    /**
     * Formats this Kilometer distance as a string in the format used in the SI system.
     *
     * @return a string representation of this Kilometer distance in the format used in the SI system
     */
    public String format() {
        return String.format("%.2f %s", distance, SI_CODE);
    }

    @Override
    public String toString() {
        return Double.toString(distance);
    }

    
}