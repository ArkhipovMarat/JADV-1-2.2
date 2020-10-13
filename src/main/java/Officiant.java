public class Officiant extends Thread {

    private Restaraunt restaraunt;

    public Officiant(String name, Restaraunt restaraunt) {
        this.restaraunt = restaraunt;
        setName(name);
    }

    @Override
    public void run() {
        System.out.printf("> officiant%s in restaraunt %n", getName());

        while (true) {
            try {
                restaraunt.acceptOrder(getName());
                restaraunt.bringOrder(getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
