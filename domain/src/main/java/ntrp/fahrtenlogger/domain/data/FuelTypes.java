package ntrp.fahrtenlogger.domain.data;

public enum FuelTypes {
    E5("E5"),
    E10("E10"),
    DIESEL("Diesel");

//    @Override
//    public String toString() {
//        return "FuelTypes{" +
//                "description='" + description + '\'' +
//                '}';
//    }

    private String description;

    private FuelTypes(String description) {}

}