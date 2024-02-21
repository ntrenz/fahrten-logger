module plugins {
    requires transitive adapters;
    requires domain;
    requires com.opencsv;
    requires java.sql;
    requires java.desktop;
    exports ntrp.fahrtenlogger.plugins;
    exports ntrp.fahrtenlogger.plugins.converter;
    requires application;
    exports ntrp.fahrtenlogger.plugins.io;
}