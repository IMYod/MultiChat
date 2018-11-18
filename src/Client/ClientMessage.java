package Client;
import java.io.IOException;
import java.util.StringTokenizer;

public class ClientMessage {
	public boolean toConnect = false;

	public boolean toStop = false;
		
	public String message;
	
	public boolean forAll = false;
	
	public boolean forClient = false;
	public String ClientName;

	public boolean getList = false;
	
	public ClientMessage(String clntMessage) throws IOException {
		StringTokenizer st = new StringTokenizer(clntMessage,">>>");
		if (st.hasMoreTokens()) {
			String first = st.nextToken();
			switch (first) {
			case "name":
				this.toConnect = true;
				message = st.nextToken(); //message = my name
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
