package ntrp.fahrtenlogger.domain.data;

/**
 * The FuelType enum represents the type of fuel.
 */
public enum FuelType {

    /**
     * Represents Diesel fuel type.
     */
    DIESEL("Diesel"),

    /**
     * Represents E10 fuel type.
     */
    E10("E10"),

    /**
     * Represents E5 fuel type.
     */
    E5("E5");

    /**
     * The type of fuel.
     */
    private final String fuelType;

    /**
     * Constructs a new FuelType with the specified type.
     *
     * @param fuelType the type of fuel
     */
    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Returns the string representation of the fuel type.
     *
     * @return the string representation of the fuel type
     */
    @Override
    public String toString() {
        return "FuelType{" +
                "fuelType='" + fuelType + '\'' +
                '}';
    }
}