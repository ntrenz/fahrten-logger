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

    @Override
    protected void parseNewCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseNewCommands'");
    }

    @Override
    protected void parseModifyCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseModifyCommands'");
    }

    @Override
    protected void parseDeleteCommands() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Unimplemented method 'parseDeleteCommands'");
    }
}
