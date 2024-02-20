package ntrp.fahrtenlogger.plugins;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvBean {
    static Path path = Paths.get("plugins/src/main/resources/FUEL_DATA.csv"); // Todo: default value

    static Path getPath() {
        return path;
    };
}
