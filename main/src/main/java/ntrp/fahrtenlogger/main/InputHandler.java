package ntrp.fahrtenlogger.main;

import java.io.Console;

public class InputHandler {

    private Console console;

    public InputHandler() {
        this.console = System.console();
    }

    public void GetInput() {
        String input = console.readLine();
        System.out.println(input);
        char[] pwd = console.readPassword();
        System.out.println(pwd);
    }
}
