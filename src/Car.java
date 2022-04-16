package src;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;
    static volatile boolean isWinner = false;

    final static CountDownLatch cdl = new CountDownLatch (4);
    final static CountDownLatch cdl2 = new CountDownLatch (4);
    final static Semaphore smp = new Semaphore (2);
    final static CyclicBarrier cyclicBarrier = new CyclicBarrier (4);
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println (this.name + " готовится");
            Thread.sleep (500 + (int) (Math.random () * 800));
            cdl.countDown ();
            System.out.println (this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace ();
        }

        try {
            cyclicBarrier.await ();
            for (int i = 0; i < race.getStages ().size (); i++) {
                race.getStages ().get (i).go (this);
                if (!isWinner && race.getStages ().indexOf (race.getStages ().get (i)) == race.getStages ().size () - 1) {
                    isWinner = true;
                    System.out.println (name + " WIN");
                }
            }
            cdl2.countDown ();
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException (e);
        }
    }
}
