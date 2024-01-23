module plugins {
    requires adapters;
    requires domain;
    requires com.opencsv;
    requires java.sql;
    exports ntrp.fahrtenlogger.plugins;
    exports ntrp.fahrtenlogger.plugins.converter;
}