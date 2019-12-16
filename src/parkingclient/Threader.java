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
    public static void create(int delay, Threader_Interface lambda){
        Threader act = new Threader(delay,lambda);
        new Thread(act).start();
    }
}