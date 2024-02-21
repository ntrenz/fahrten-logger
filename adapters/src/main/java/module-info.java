module adapters {
    requires application;
    // exports ntrp.fahrtenlogger.adapters;
    requires transitive domain;
    exports ntrp.fahrtenlogger.adapters;
    exports ntrp.fahrtenlogger.adapters.interpreter;
}