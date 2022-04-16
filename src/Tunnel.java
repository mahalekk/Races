package src;

public class Tunnel extends Stage {

    public Tunnel() {
        this.length = 60;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                Car.smp.acquire ();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                Car.smp.release ();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
