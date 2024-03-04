package ntrp.fahrtenlogger.plugins;

import java.nio.file.Path;

public abstract class CsvBean {
    public abstract Path getPath();
    public abstract String getHeaderLine();
}
