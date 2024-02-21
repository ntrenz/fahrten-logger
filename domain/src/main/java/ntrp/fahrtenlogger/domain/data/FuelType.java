package ntrp.fahrtenlogger.domain.data;

/**
 * Enum representing various types of fuel.
 * <p>
 * This enum includes types such as Diesel, E10, and E5.
 */
public enum FuelType {
    /**
     * Diesel fuel type.
     */
    DIESEL("Diesel"),

    /**
     * E10 fuel type.
     */
    E10("E10"),

    /**
     * E5 fuel type.
     */
    E5("E5");

    private final String fuelType;

    /**
     * Constructor for the enum.
     *
     * @param fuelType A string representing the fuel type.
     */
    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Returns the string representation of the enum constant.
     *
     * @return A string representing the fuel type.
     */
    @Override
    public String toString() {
        return "FuelTypes{fuelType="+this.fuelType+"}";
    }
}