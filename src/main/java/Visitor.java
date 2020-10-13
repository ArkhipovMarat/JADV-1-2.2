public class Visitor extends Thread {

    private Restaraunt restaraunt;

    private static final long ORDER_WAIT = 1000;
    private static final long EATING_TIME = 3000;

    public Visitor(String name, Restaraunt restaraunt) {
        this.restaraunt = restaraunt;
        setName(name);
    }

    @Override
    public void run() {
        System.out.printf("> visitor%s in restaraunt %n", getName());

        waitingTime();
        restaraunt.addOrder(getName());

        while (true) {
            if (restaraunt.waitForOrder(getName())) {
                eatingTime();
                System.out.printf("> visitor%s went off %n", getName());
                break;
            }
        }
    }

    private void waitingTime() {
        try {
            Thread.sleep(ORDER_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eatingTime () {
        try {
            Thread.sleep(EATING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
