import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kitchen {
    Restaraunt restaraunt;
    private Lock lock;
    private Condition awaitOrder;

    private static final int COOK_TIME = 1500;

    public Kitchen(Restaraunt restaraunt) {
        this.restaraunt = restaraunt;
        this.lock = new ReentrantLock();
        this.awaitOrder = lock.newCondition();
    }

    void notifyKitchener() {
        lock.lock();
        try {
            awaitOrder.signal();
        } finally {
            lock.unlock();
        }
    }

    void beginCoocking(String name) throws InterruptedException {
        lock.lock();
        awaitOrder.await();
        try {
            for (Order order : restaraunt.getOrderList()) {
                if (order.getStatus().equals(Status.ACCEPTED)) {
                    System.out.printf("Kitchener%s: begin cooking order for visitor%s%n", name, order.getVisitorName());
                    order.setStatus(Status.COOKING);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    void endCoocking(String name) throws InterruptedException {
        lock.lock();
        try {
            for (Order order : restaraunt.getOrderList()) {
                if (order.getStatus().equals(Status.COOKING)) {
                    System.out.printf("Kitchener%s: order coocked for visitor%s%n", name, order.getVisitorName());
                    order.setStatus(Status.COOCKED);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
