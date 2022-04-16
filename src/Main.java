package src;

import java.util.concurrent.*;

public class Main {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race (new Road (20), new Tunnel (), new Road (10));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car (race, 20 + (int) (Math.random () * 10));
        }

        for (Car car : cars) {
            new Thread (car).start ();
        }
        Car.cdl.await ();
        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        Car.cdl2.await ();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
