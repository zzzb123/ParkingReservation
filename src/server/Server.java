package server;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server{
	public static void main(String[]args){
		try {
			SSLServerSocket ssock = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(12345);
			while(ssock.isBound()) {
				new ConnectionHandler().start(ssock.accept());
			}
		}catch(Exception e) {System.out.println("Connection Error, assuming missing SSL key B.S. is happening");}
	}
}
