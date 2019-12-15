package parkingserver;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
	public static List<Lot> lots = Collections.synchronizedList(new ArrayList<Lot>());
	public static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

	public static final String LOT_FILE_NAME = "lotfile.txt";
	public static final String USER_FILE_NAME = "userfile.txt";

	public static void main(String[] args) {
		try {
			loadLotsFile(LOT_FILE_NAME);
			loadUserFile(USER_FILE_NAME);
			// begin listening for connections
			ServerSocket ssock = new ServerSocket(12345);
			new Server().start();// start old reservation gc
			while (!ssock.isClosed()) {
				new ConnectionHandler().start(ssock.accept());
			}
			ssock.close();
		} catch (Exception e) {
			System.out.println(
					"Error on startup, check the formatting on the preloaded files and your network permissions");
			e.printStackTrace();
		}
	}

	public void run() {// reservation garbage collector -- ensures that stale reservations dont build
						// up too much
		try {
			while (true) {
				wait(1000 * 60 * 60 * 24);// last number is the number of hours to wait in between rgc cycles
				TimePoint currentTime = new TimePoint(
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu,MM,dd,HH,mm")));
				for (Lot l : lots) {
					l.triggerRGC(currentTime);
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong with the daily garbage collection!  This should never happen.");
		}
	}

	public void start() {
		new Thread(this).start();
	}

	public static void loadLotsFile(String filename) throws Exception {
		LinkedList<String> lotdata = FileInteract.getFileContents(filename);
		while (lotdata.size() != 0) {
			lotdata.remove(0); // purges the seperator line (its only used for readability anyway)
			String lotname = lotdata.remove(0);
			String numspots = lotdata.remove(0);
			String[] p = lotdata.remove(0).split(",");
			double[] pos = new double[] { Double.parseDouble(p[0]), Double.parseDouble(p[1]) };
			Lot l;
			if (numspots.contains(":")) {
				String[] points = numspots.split(":");
				String[] size = points[0].split(",");
				SpotPlaceholder[][] template = new SpotPlaceholder[Integer.parseInt(size[0])][Integer
						.parseInt(size[1])];
				for (int i = 1; i < points.length; ++i) {
					String[] point = points[i].split(",");
					template[Integer.parseInt(point[0])][Integer.parseInt(point[1])] = new SpotPlaceholder(
							point[2].equals("n"));
				}
				l = new MappedLot(lotname, template, pos);
			} else {
				String[] ns = numspots.split(",");
				l = new Lot(lotname, Integer.parseInt(ns[0]), Integer.parseInt(ns[1]), pos);
			}
			lots.add(l);
		}
	}

	public static void loadUserFile(String filename) throws Exception {// TODO finish implementing the user import
																		// feature
		LinkedList<String> userdata = FileInteract.getFileContents(filename);
		while (userdata.size() != 0) {
			userdata.remove(0);
			String username = userdata.remove(0);
			String email = userdata.remove(0);
			String passhash = userdata.remove(0);
			String identifier = userdata.remove(0);
			if (identifier.equals("null"))
				identifier = null;
			users.put(username, new User(username, email, passhash, identifier));
		}
	}

	public static synchronized void writeUserFile(User u, String filename) {
		try{
			PrintWriter f = new PrintWriter(new FileWriter(filename, true));
			f.println("//");
			f.println(u.username);
			f.println(u.email);
			f.println(u.password);
			f.println(u.identifier);
			f.close();
		}catch(Exception e){System.out.println("random error on writing a new user, have fun fixing :)");}
	}
}
/*
the lot file is formatted like the following example::
<><><><><><><><><><><><>
Lot A
12,5
6.0000023,12.87778754
<><><><><><><><><><><><>
Lot B
4,4:1,1,h:2,2,n:3,3,h:0,0,n
1.0,12.5

The lot data is as follows:
a spacer line (the text does not matter)
the name of the lot
the number of spots (or an array containing the coordinates of the spots with the first element being the size of the lot if the lot is mapped)
the geographical position of the lot
*/

/*
the user file is formatted like the following::
<><><><><><><><><><><><>
rdash2
rdash2@masonlive.gmu.edu
12876478326478723787324
G01212083
~borgarborgarborgarborgar~
gmustudent
gmustudent@gmu.edu
743897389247893
null

following the format:
a spacer line
username
email
the hash of their password
any identification needed (or null if none exists)
*/