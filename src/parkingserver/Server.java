package parkingserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server implements Runnable {
	public static ConcurrentHashMap<String, Lot> lots = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		try {
			SSLServerSocket ssock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(12345);
			new Server().start();
			while (ssock.isBound()) {
				new ConnectionHandler().start(ssock.accept());
			}
		} catch (Exception e) {
			System.out.println("Connection Error, assuming missing SSL key B.S. is happening");
		}
	}

	public void run() {//reservation garbage collector -- ensures that stale reservations dont build up too much
		while(true){
			try {
				wait(1000 * 60 * 60 * 24);//last number is the number of hours to wait in between rgc cycles
				TimePoint currentTime = new TimePoint(LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm")));
				for(Lot l : lots.values()){
					l.triggerRGC(currentTime);
				}
			} catch (Exception e) {
				System.out.println("Something went wrong with the daily garbage collection!  This should never happen.");
			}
		}
	}
	public void start(){
		new Thread(this).start();
	}
}
