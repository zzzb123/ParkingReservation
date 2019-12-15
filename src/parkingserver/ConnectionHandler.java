package parkingserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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
			toExit:while(true){
				String command = in.readLine();
				swi:switch(command){
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
						for(int i = 0; i < Math.min(numlots, sortedlots.size()); i++){
							res += sortedlots.get(i).lotName + "\t" + sortedlots.get(i).getNormalOpenings(reservation) + "," + sortedlots.get(i).getHandicapOpenings(reservation);
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
								out.println("ok");
								break swi;
							}
						}
						out.println("sorry");
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
						Server.writeUserFile(user, Server.USER_FILE_NAME);
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
							out.println(targetlot.lotName + "\t" + reservation.startTime.getTime() + "\t" + reservation.endTime.getTime() + "\t" + targetlot.getTotalSpots() +  "\t" + targetlot.getNormalOpenings(reservation) + "\t" + targetlot.getHandicapOpenings(reservation));
						}
						break;
					case "reserve-spot":
						try{
						if(targetlot instanceof MappedLot){
							Scheduler[][] lotmap = ((MappedLot)targetlot).getLotVisualization();
							coord = in.readLine().split(",");
							lotmap[Integer.parseInt(coord[0])][Integer.parseInt(coord[1])].reserveTime(reservation);
							user.linkReservation(reservation, targetlot);
						}
						else{
							String type = in.readLine();
							try{
							if(type.equals("normal"))
								targetlot.reserveNormalSpot(reservation);
							else
								targetlot.reserveHandicapSpot(reservation);
							user.linkReservation(reservation, targetlot);
							out.println("ok");
							}catch(Exception e){out.println("sorry");}
						}
						out.println("ok");
						}catch(Exception e){out.println("error");}
						break;
					case "disconnect": //lets make a way to prevent the server from throwing errors left and right during execution, shall we?
						break toExit;
					case "list-reservations":
						for(Entry<Reservation,Lot> e : user.reservations.entrySet()){
							out.println(e.getKey().startTime.getTime() + ":" + e.getKey().endTime.getTime() +  ":" + e.getValue().lotName);
						}
						out.println("ok");
						break;
					case "cancel-reservation"://TODO known bad implementation
						targetlot.cancelReservation(reservation);
						user.reservations.remove(reservation);
						break;
					case "fix-reservation":
						reservation = new Reservation(reservation.startTime, reservation.endTime);
						break;
					default:
						System.out.println("unknown command entered");
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