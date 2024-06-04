package ntrp.fahrtenlogger.adapters.interpreter;

import java.util.List;

public class UnknownInterpreter extends CommandInterpreter {
    private String input;
    public UnknownInterpreter(String input) {
        super(List.of(input));
    }

    @Override
    public void parseCommands() {
        this.input = this.arguments_list.get(0);
    }

    @Override
    public void executeCommands() {
        System.err.println("Des häm ma net! (" + this.input + ")");
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

    @Override
    protected void parseReadCommands() throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseReadCommands'");
    }

    @Override
    protected void parseAnalyzeCommands() throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseAnalyzeCommands'");
    }
}
