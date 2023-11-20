package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hallo");

        Kilometer km = new Kilometer(10);
        System.out.println(km);
    }
}
