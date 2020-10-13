public class Main {
    private static final int VISITOR_COUNT = 5;
    private static final int OFFICIANT_COUNT = 2;
    private static final int VISITOR_PERIOD = 2000;

    public static void main(String[] args) throws InterruptedException {
        Restaraunt restaraunt = new Restaraunt();
        officiantEntering(restaraunt);
        kitchenerEntering(restaraunt);
        visitorEntering(restaraunt);
    }

    public static void visitorEntering(Restaraunt restaraunt) throws InterruptedException {
        for (int i = 1; i <= VISITOR_COUNT; i++) {
            Thread.sleep(VISITOR_PERIOD);
            Thread visitor = new Visitor("" + i, restaraunt);
            visitor.start();
        }
    }

    public static void officiantEntering(Restaraunt restaraunt) {
        for (int i = 1; i <= OFFICIANT_COUNT; i++) {
            Thread officiant = new Officiant("" + i, restaraunt);
            officiant.setDaemon(true);
            officiant.start();
        }
    }

    public static void kitchenerEntering(Restaraunt restaraunt) {
            Thread kitchener = new Kitchener("Bob", restaraunt);
            kitchener.setDaemon(true);
            kitchener.start();
    }

}
