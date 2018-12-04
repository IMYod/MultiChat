package ChatServer.server;
import java.io.IOException;
import java.util.StringTokenizer;
/**
 * In This class we managed the protocol for the communication between the clients and server. 
 * "For sending brodcast:
 *  	brodcast>>>your massage
 * "For sending message to one user, write: 
 * 		chat>>>user name>>>your meesage.
 *  "And finally, before you leave, write: 
 *  	stop.
 * 
 * @author נביאי
 *
 */
public class ClientMessage 
{
	public boolean toConnect = false;

	public boolean toStop = false;
		
	public String message;
	
	public boolean forAll = false;
	
	public boolean forClient = false;
	public String ClientName;

	public boolean getList = false;
	
	public String fromClient;
	
	public ClientMessage(String clntMessage) throws IOException 
	{
		StringTokenizer st = new StringTokenizer(clntMessage,">>>");
		if (st.hasMoreTokens())
			fromClient = st.nextToken();
		if (st.hasMoreTokens()) {
			String first = st.nextToken();
			switch (first) {
			case "ping":
				this.toConnect = true;
				return;

			case "stop":
				this.toStop = true;
				return;
				
			case "getList":
				this.getList = true;
				return;
			
			case "brodcast":
				this.forAll = true;
				message = st.nextToken();
				return;
				
			case "chat":
				this.forClient = true;
				ClientName = st.nextToken();
				message = st.nextToken();
				return;

			default:
				return;
			}
		}
	}
}
