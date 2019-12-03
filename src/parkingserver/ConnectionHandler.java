package parkingserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

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
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			//Start of productive things
				//TODO this is where the server will eventually interact with the client.
			//End of productive things
		} catch (IOException e) {
			System.out.println("Error in communication with client.  Assuming client Disconnected.");
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