package parkingclient;

public class Threader implements Runnable {
    private int delay;
    private Threader_Interface action;

    private Threader(int delay, Threader_Interface lambda) {
        this.delay = delay;
        this.action = lambda;
    }

    public interface Threader_Interface {
        void execute();
    }

    public void run() {
        while (delay >= 0) {
            try {
                wait(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.execute();
        }
    }

    /**
     * Generates a new thread that executes a lambda every <delay> milliseconds
     * @param delay the number of milliseconds to wait between runs - negative values execute only once
     * @param lambda the lambda to be evaluated
     */
    public static void create(int delay, Threader_Interface lambda){
        Threader act = new Threader(delay,lambda);
        new Thread(act).start();
    }
}