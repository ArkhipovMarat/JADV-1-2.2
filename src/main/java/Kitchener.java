public class Kitchener extends Thread{

    private static final int COOK_TIME = 250;
    private Restaraunt restaraunt;

    public Kitchener(String name, Restaraunt restaraunt) {
        this.restaraunt = restaraunt;
        setName(name);
    }

    @Override
    public void run() {
        System.out.printf("> kitchener%s in restaraunt %n", getName());

        while (true) {
            try {
                restaraunt.getKitchen().beginCoocking(getName());
                coocking();
                restaraunt.getKitchen().endCoocking(getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void coocking () {
        try {
            Thread.sleep(COOK_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
