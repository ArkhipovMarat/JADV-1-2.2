import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaraunt {
    volatile private List<Order> orderList = new LinkedList<>();
    private Lock lock;

    public Restaraunt() {
        this.lock = new ReentrantLock();
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

    void bringOrder(String name) throws InterruptedException {
        lock.lock();
        try {
            for (Order order : getOrderList()) {
                if (order.getStatus().equals(Status.COOCKED)) {
                    System.out.printf("> > > officiant%s: bring order to visitor%s%n", name, order.getVisitorName());
                    order.setStatus(Status.SUBMITED);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    boolean waitForOrder(String visitorName) {
        lock.lock();
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

    void beginCoocking(String name) throws InterruptedException {
        lock.lock();
        try {
            for (Order order : orderList) {
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
            for (Order order : orderList) {
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

    public List<Order> getOrderList() {
        return orderList;
    }
}
