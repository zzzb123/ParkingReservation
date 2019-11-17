package RedirectingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			
			do {
				String mode = in.readLine();
				switch(mode) {
				case "reg"://register server connecting as leaf
					//TODO implement reg mode
					break;
				case "req"://return leaf list
					//TODO implement req mode
					break;
				case "rep"://check if leaf node is still responding
					//TODO implement
					break;
				case "pos"://re-bind server to a new position -- relocates "true leaves"
					//TODO implement
					break;
				}
			}while(in.ready() && in.readLine().contentEquals("con"));
		} catch (IOException e) {
			System.out.println("Error in communication with client.  Assuming client Disconnected.");
		}
	}
}
