package RedirectingServer;

import java.net.Socket;

public class ConnectionHandler implements Runnable{
	public Socket sock;
	public void start(Socket s) {
		sock = s;
		new Thread(this).start();
	}
	public void run() {
		
	}
}
