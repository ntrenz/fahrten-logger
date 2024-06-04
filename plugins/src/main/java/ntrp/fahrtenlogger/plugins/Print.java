package ntrp.fahrtenlogger.plugins;

import ntrp.fahrtenlogger.application.analyzer.PrintColors;
import ntrp.fahrtenlogger.application.analyzer.PrintInterface;

/**
 * The Print class is a Singleton class which provides functionality for outputting text with various modification options.
 * The modifications that can be made include coloring, bolding, italicizing, and underlining the text.
 * The class also provides methods for adding new-lines and delimiters in the output, and converting the fraction to percentage.
 * The class uses ANSI escape codes to modify the text.
 */
public class Print implements PrintInterface {

    /**
     * Singleton instance of the Print class.
     */
    private static Print instance = null;

    /**
     * Private constructor to prevent creating an instance of the class from outer classes.
     */
    public Print() {}

    /**
     * Provides the singleton instance of the Print class.
     * If the instance is null, a new instance is created.
     *
     * @return Singleton instance of the Print class.
     */
    public static Print getInstance() {
        if (instance == null) instance = new Print();
        return instance;
    }

    /**
     * Provides the start part of an ANSI escape code.
     *
     * @param ansiCode ANSI escape code.
     * @return Formatted ANSI escape code start string.
     */
    private String startAnsiCode(String ansiCode) {
        return "\u001B[" + ansiCode;
    }

    /**
     * Provides the end part of an ANSI escape code.
     *
     * @return Formatted ANSI escape code end string.
     */
    private String endAnsiCode() {
        return "\u001B[0m";
    }

    /**
     * Prints a new line to the standard output.
     */
    public void newLine() {
        System.out.println(System.lineSeparator());
    }

    /**
     * Prints a string to the standard output followed by a new line.
     *
     * @param text Text to be printed to the standard output.
     */
    public void out(String text) {
        System.out.println(text);
    }

    /**
     * Prints a string to the standard output followed by new line.
     *
     * @param text Text to be printed to the standard output.
     */
    public void outWithNewLine(String text) {
        System.out.println(text + System.lineSeparator());
    }

    /**
     * Prints a string to the standard output followed by a delimiter line.
     *
     * @param text Text to be printed to the standard output.
     */
    public void outWithDelimiter(String text) {
        System.out.println(text);
        System.out.println(System.lineSeparator());
        System.out.println(bold("-".repeat(170)));
        System.out.println(System.lineSeparator());
    }

    /**
     * Converts a fraction to a percentage string rounded to 1 decimal place and
     * returns it with a '%' sign appended at the end.
     *
     * @param fraction Fraction to be converted to percentage.
     * @return String representing the percentage.
     */
    public String percentage(double fraction) {
        double percentage = Math.round(fraction * 1000) / 10.;
        return (percentage + " %").replace('.', ',');
    }

    /**
     * Colors a text with given color and returns it.
     *
     * @param text Text to be colored.
     * @param color Color object representing the color to be set.
     * @return Colored text string.
     */
    @Override
    public String color(String text, PrintColors color) {
        // Color c = new Color(color.r, color.g, color.b);

        // int r = c.getRed();
        // int g = c.getGreen();
        // int b = c.getBlue();

        String colorCode = "38;2;" + color.r + ";" + color.g + ";" + color.b + "m";
        return startAnsiCode(colorCode) + text + endAnsiCode();
    }

    /**
     * Bolds a text and returns it.
     *
     * @param text Text to be bolded.
     * @return Bolded text string.
     */
    @Override
    public String bold(String text) {
        return startAnsiCode("1m") + text + endAnsiCode();
    }

    /**
     * Italicizes a text and returns it.
     *
     * @param text Text to be italicized.
     * @return Italicized text string.
     */
    public String italic(String text) {
        return startAnsiCode("3m") + text + endAnsiCode();
    }

    /**
     * Underlines a text and returns it.
     *
     * @param text Text to be underlined.
     * @return Underlined text string.
     */
    public String underlined(String text) {
        return startAnsiCode("4m") + text + endAnsiCode();
    }
}