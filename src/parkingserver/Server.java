package parkingserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server implements Runnable {
	public static List<Lot> lots = Collections.synchronizedList(new ArrayList<Lot>());
	public static void main(String[] args) {
		try {
			LinkedList<String> lotdata = FileInteract.getFileContents("lotdata.txt");
			while(lotdata.size() != 0){
				String lotname = lotdata.remove(0);
				String numspots = lotdata.remove(0);
				String[] p = lotdata.remove(0).split(",");
				double[] pos = new double[]{Double.parseDouble(p[0]), Double.parseDouble(p[1])};
				Lot l;
				if(numspots.equals("mappedlot")){
					String[] points = lotdata.remove(0).split(":");
					String[] size = points[0].split(",");
					SpotPlaceholder[][] template = new SpotPlaceholder[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
					for(int i = 1; i < points.length; ++i){
						String[] point = points[i].split(",");
						template[Integer.parseInt(point[0])][Integer.parseInt(point[1])] = new SpotPlaceholder(point[2].equals("n"));
					}
					l = new MappedLot(lotname, template, pos);
				}
				else{
					String[] ns = numspots.split(",");
					l = new Lot(lotname, Integer.parseInt(ns[0]), Integer.parseInt(ns[1]), pos);
				}
				lots.add(l);
			}

			//begin listening for connections
			SSLServerSocket ssock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(12345);
			new Server().start();
			while (ssock.isBound()) {
				new ConnectionHandler().start(ssock.accept());
			}
		} catch (Exception e) {
			System.out.println("Error on startup, either the lot geodata is messed up or there was an error with SSL.");
		}
	}

	public void run() {//reservation garbage collector -- ensures that stale reservations dont build up too much
		while(true){
			try {
				wait(1000 * 60 * 60 * 24);//last number is the number of hours to wait in between rgc cycles
				TimePoint currentTime = new TimePoint(LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm")));
				for(Lot l : lots){
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
