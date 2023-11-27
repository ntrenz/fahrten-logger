package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

import ntrp.fahrtenlogger.adapters.DataSaver;

public class ExitInterpreter extends CommandInterpreter {
    DataSaver dataHandler;
    public ExitInterpreter(List<String> args, DataSaver dataHandler) {
        super(args);
        this.dataHandler = dataHandler;
    }

    @Override
    public void parseCommands() { }

    @Override
    public void executeCommands() {
        dataHandler.saveAllRepositories();
    }

    public static String getHelp() {
        return "Speichert alle aktiven Daten ab und beendet das Programm.";
    }
}
