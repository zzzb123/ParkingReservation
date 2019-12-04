package parkingserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ConnectionHandler implements Runnable{
	private Socket sock;
	private BufferedReader in;
	private PrintWriter out;
	public void start(Socket s) {
		sock = s;
		new Thread(this).start();
	}
	public void finalize() throws Exception{
		sock.close();
		in.close();
		out.close();
	}
	public void run(){//this block contains the main logic that ties it all together.  The rest of this package is just for databasing the lot structures.
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			//Start of productive things
			List<Lot> sortedlots = null;
			Lot targetlot = null;
			Reservation reservation = null;
			User user = null;
			while(true){
				String command = in.readLine();
				switch(command){
					case "set-coordinates":
						String[] coord = in.readLine().split(",");
						double [] coords = new double[]{Double.parseDouble(coord[0]), Double.parseDouble(coord[1])};
						sortedlots = sortLots(coords);
						break;
					case "set-user":
						String username = in.readLine();
						String passhash = in.readLine();
						User u = Server.users.get(username);
						if(u == null || !u.password.equals(passhash)){
							out.println("sorry");
							break;
						}
						user = u;
						out.println("ok");
						break;
					case "set-reservation-times":
						TimePoint t1 = new TimePoint(in.readLine());
						TimePoint t2 = new TimePoint(in.readLine());
						reservation = new Reservation(t1, t2);
						break;
					case "get-lots":
						int numlots = Integer.parseInt(in.readLine());
						String res = "";
						if(numlots < Server.lots.size())
							for(int i = 0; i < numlots; i++){
								res += sortedlots.get(i).lotName + "//" + sortedlots.get(i).getNumOpenings(reservation);
								if(i < numlots-1)
									res+="::";
							}
						out.println(res);
						break;
					case "set-target-lot":
						String name = in.readLine();
						for(Lot l : Server.lots){
							if(l.lotName.equals(name)){
								targetlot = l;
								break;
							}
						}
						break;
					case "register-user":
						username = in.readLine();
						String email = in.readLine();
						passhash = in.readLine();
						String identifier = in.readLine();
						if(Server.users.containsKey(username)){
							out.println("sorry");
							break;
						}
						user = (identifier.equals("null"))?new User(username, email, passhash):new User(username, email, passhash, identifier);
						Server.users.put(username, user);
						out.println("ok");
						break;
					case "get-lot-data":
						if(targetlot instanceof MappedLot){
							res = "";
							Scheduler[][] lotmap = ((MappedLot)targetlot).getLotVisualization();
							res += lotmap[0].length + "," + lotmap.length + ":";
							for(int y = 0; y < lotmap.length; y++){
								for(int x = 0; x < lotmap[y].length; x++){
									if(lotmap[x][y] != null){
										res += x + "," + y + "," + ((lotmap[x][y].timeAvailable(reservation))?"open":"closed") + "," + (targetlot.schedulerIsHandicap(lotmap[x][y])?("handicap"):("normal"));
										if(y < lotmap.length-1 || x < lotmap[y].length-1)
											res += ":";
									}
								}
							}
							out.println(res);
						}
						else{
							out.println(targetlot.getNormalSpots() + "," + targetlot.getHandicapSpots());
						}
						break;
					case "reserve-spot":
						if(targetlot instanceof MappedLot){
							Scheduler[][] lotmap = ((MappedLot)targetlot).getLotVisualization();
							coord = in.readLine().split(",");
							lotmap[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].reserveTime(reservation);
						}
						else{
							String type = in.readLine();
							if(type.equals("normal"))
								targetlot.reserveNormalSpot(reservation);
							else
								targetlot.reserveHandicapSpot(reservation);
							user.linkReservation(reservation, targetlot);
						}
						break;
				}
			}
			//End of productive things
		} catch (Exception e) {
			System.out.println("Error in communication with client.  Assuming client messed up or there was a synchronization error.");
		}
	}

	public LinkedList<Lot> sortLots(double[] pos){
		LinkedList<Lot> sortedLots = new LinkedList<>();
		for(Lot l : Server.lots){
			int insertPos = 0;
			while(insertPos < sortedLots.size()){
				if(l.getDistance(pos) < sortedLots.get(insertPos).getDistance(pos))
					break;
				else
					insertPos++;
			}
			sortedLots.add(insertPos, l);
		}
		return sortedLots;
	}
}