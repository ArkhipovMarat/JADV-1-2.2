import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaraunt {
    volatile private List<Order> orderList = new LinkedList<>();
    private Kitchen kitchen;
    private Lock lock;
    private Condition orderReady;

    public Restaraunt() {
        this.lock = new ReentrantLock();
        this.kitchen = new Kitchen(this);
        this.orderReady = lock.newCondition();
    }

    void notifyVisitor() {
        lock.lock();
        try {
            orderReady.signal();
        } finally {
            lock.unlock();
        }
    }

    void addOrder(String name) {
        lock.lock();
        try {
            System.out.printf("visitor%s added order%n", name);
            orderList.add(new Order(Status.NOTIFIED, name));
        } finally {
            lock.unlock();
        }
    }

    void acceptOrder(String name) throws InterruptedException {
        lock.lock();
        try {
            for (Order order : getOrderList()) {
                if (order.getStatus().equals(Status.NOTIFIED)) {
                    System.out.printf("> > officiant%s: accepted order from visitor%s%n", name, order.getVisitorName());
                    order.setStatus(Status.ACCEPTED);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    boolean bringOrder(String name) throws InterruptedException {
        lock.lock();
        try {
            for (Order order : getOrderList()) {
                if (order.getStatus().equals(Status.COOCKED)) {
                    System.out.printf("> > > officiant%s: bring order to visitor%s%n", name, order.getVisitorName());
                    order.setStatus(Status.SUBMITED);
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }

    }

    boolean waitForOrderReady(String visitorName) throws InterruptedException {
        lock.lock();
        orderReady.await();
        try {
            for (Order order : getOrderList()) {
                if (order.getVisitorName().equals(visitorName) &
                        order.getStatus().equals(Status.SUBMITED)) {
                    System.out.printf("> > > visitor%s: get meat and begin eating%n", visitorName);
                    orderList.remove(order);
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }
}
