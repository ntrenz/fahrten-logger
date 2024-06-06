package ntrp.fahrtenlogger.plugins;

import org.junit.jupiter.api.Test;

import ntrp.fahrtenlogger.application.analyzer.PrintColors;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PrintTest {

    Print print;

    @BeforeEach
    public void setUp() {
        print = Print.getInstance();
    }

    @Test
    public void testNewLine() {
        // This is hard to test properly as newLine doesn't return anything
        // and it outputs to the system console, so we will just run it to confirm no errors are thrown
        print.newLine();
    }

    @Test
    public void testOut() {
        // This is hard to test properly as out doesn't return anything
        // and it outputs to the system console, so we will just run it to confirm no errors are thrown
        print.out("This is a test");
    }

    @Test
    public void testOutWithNewLine() {
        // As above, hard to test properly as outWithNewLine doesn't return anything
        // and it outputs to the console, so we will just run it to confirm no errors are thrown
        print.outWithNewLine("This is a test");
    }

    @Test
    public void testOutWithDelimiter() {
        // As above, hard to test properly as outWithDelimiter doesn't return anything
        // and it outputs to the console, so we will just run it to confirm no errors are thrown
        print.outWithDelimiter("This is a test");
    }

    @Test
    public void testPercentage() {
        String percentage = print.percentage(0.23456);
        assertEquals("23,5 %", percentage);
    }

    @Test
    public void testColor() {
        String coloredText = print.color("This is a test", PrintColors.ORANGE);
        assertTrue(coloredText.startsWith("\u001B[38;2;255;87;51m"));
        assertTrue(coloredText.endsWith("\u001B[0m"));
        assertTrue(coloredText.contains("This is a test"));
    }

    @Test
    public void testBold() {
        String boldedText = print.bold("This is a test");
        assertTrue(boldedText.startsWith("\u001B[1m"));
        assertTrue(boldedText.endsWith("\u001B[0m"));
        assertTrue(boldedText.contains("This is a test"));
    }

    @Test
    public void testItalic() {
        String italicizedText = print.italic("This is a test");
        assertTrue(italicizedText.startsWith("\u001B[3m"));
        assertTrue(italicizedText.endsWith("\u001B[0m"));
        assertTrue(italicizedText.contains("This is a test"));
    }

    @Test
    public void testUnderlined() {
        String underlinedText = print.underlined("This is a test");
        assertTrue(underlinedText.startsWith("\u001B[4m"));
        assertTrue(underlinedText.endsWith("\u001B[0m"));
        assertTrue(underlinedText.contains("This is a test"));
    }
}