//The server node has NO ENCRYPTION ON ITS COMMUNICATION WHATSOEVER
//the idea behind this is that any information it shares could be skimmed (as easily) from other sources anyway, and thus encryption would only add useless bloat.
//server takes a similar approach to tracking lots as a unix-like software repository
//the clients request a ledger of all known servers one layer down from the lowest applicable node, and use it to find the next lowest layer until a lot is reached
//the ledger of any one layer should be small enough that it can be downloaded quickly, but large enough so that not too many hops have to be made,
//a trade off that can be changed on a branch-by-branch basis.

package RedirectingServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Server{
	public static Collection<SubServer> subServers = Collections.synchronizedCollection(new LinkedList<SubServer>());
	
	@SuppressWarnings("resource")
	public static void main(String[]args) throws IOException{
		ServerSocket ssock = new ServerSocket(12345);
		while(ssock.isBound()) {
			new ConnectionHandler().start(ssock.accept());
		}
	}
}
