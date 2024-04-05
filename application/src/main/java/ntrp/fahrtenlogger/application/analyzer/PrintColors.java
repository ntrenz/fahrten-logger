package ntrp.fahrtenlogger.application.analyzer;

public enum PrintColors {
    ORANGE(255, 87, 51),
    RED(215, 38, 0), 
    GREEN(18, 177, 63),
    BLUE(24, 109, 167);

    public int r;
    public int g;
    public int b;

    private PrintColors(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
