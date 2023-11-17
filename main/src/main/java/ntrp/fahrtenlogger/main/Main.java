package ntrp.fahrtenlogger.main;

import ntrp.fahrtenlogger.application.DriveCar;
import ntrp.fahrtenlogger.domain.*;
import ntrp.fahrtenlogger.domain.ValueObjects.Kilometer;
import ntrp.fahrtenlogger.plugins.Test;

public class Main {
    public static void main(String[] args) {
        Auto a = new Auto("TEST");
        Test t = new Test();

        DriveCar d = new DriveCar();

        System.out.println("Hallo");

        Kilometer km = new Kilometer(10);
        System.out.println(km);
    }
}
