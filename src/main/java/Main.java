public class Main {
    private static final int VISITOR_COUNT = 5;
    private static final int OFFICIANT_COUNT = 2;
    private static final int VISITOR_PERIOD = 500;

    public static void main(String[] args) throws InterruptedException {
        Restaraunt restaraunt = new Restaraunt();
        officiantEntering(restaraunt);
        kitchenerEntering(restaraunt, "Bob");
        visitorEntering(restaraunt);
    }

    public static void visitorEntering(Restaraunt restaraunt) throws InterruptedException {
        for (int i = 1; i <= VISITOR_COUNT; i++) {
            Thread visitor = new Visitor("" + i, restaraunt);
            visitor.start();
            Thread.sleep(VISITOR_PERIOD);
        }
    }

    public static void officiantEntering(Restaraunt restaraunt) {
        for (int i = 1; i <= OFFICIANT_COUNT; i++) {
            Thread officiant = new Officiant("" + i, restaraunt);
            officiant.setDaemon(true);
            officiant.start();
        }
    }

    public static void kitchenerEntering(Restaraunt restaraunt, String name) {
        Thread kitchener = new Kitchener(name, restaraunt);
        kitchener.setDaemon(true);
        kitchener.start();
    }

}
