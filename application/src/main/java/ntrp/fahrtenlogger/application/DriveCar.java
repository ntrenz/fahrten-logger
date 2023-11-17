package ntrp.fahrtenlogger.application;

import ntrp.fahrtenlogger.domain.Auto;

public class DriveCar {
    public static void main(String[] args) {
        Auto a = new Auto("Test");
        System.out.println(a.make);
    }
}
