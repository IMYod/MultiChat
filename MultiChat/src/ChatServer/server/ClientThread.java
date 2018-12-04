// place this file the path such ends with: ChatServer/server/ClientThread.java

package ChatServer.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * This class for the thread for the Server!!
 * All the information is transform by those threads.
 * @author נביאי
 *
 */
public class ClientThread implements Runnable
{
	private Socket socket;
	private PrintWriter clientOut;
	private ChatServer server;
	protected String clientName;

	public ClientThread(ChatServer server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
	}

	private PrintWriter getWriter(){
		return clientOut;
	}

	@Override
	public void run() {
		try{
			// setup
			this.clientOut = new PrintWriter(socket.getOutputStream(), false);
			Scanner in = new Scanner(socket.getInputStream());

			// start communicating
			while(!socket.isClosed()){
				if(in.hasNextLine()){
					String input = in.nextLine();
					ClientMessage cMessage = new ClientMessage(input);

					if (cMessage.toConnect == true)
						if (server.contains(cMessage.fromClient)) {
							send("The name " + cMessage.fromClient + " already exist");
							break;
						}
						else
							clientName = cMessage.fromClient;

					if (cMessage.toStop == true) {
						send("bye bye...\n");
						Thread.sleep(100);
						stop();
					}
					else if (cMessage.getList)
						send("List > " + server.getUsers());
					else if (cMessage.forAll == true)
						brodcast(cMessage.fromClient + " > " + cMessage.message);
					else if (cMessage.forClient == true)
					{
						sendTo(cMessage.ClientName, cMessage.message, cMessage.fromClient);
						send(clientName + " > " + cMessage.message);
					}
				}
			}
			stop();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stop() {
		server.clients.remove(this);
		Thread.currentThread().stop();
	}

	private void sendTo(String clientNameTo, String message, String from) {
		for (ClientThread clientT : server.clients)
			if (clientNameTo.equals(clientT.clientName))
				clientT.send(from + " > " + message);	
	}

	private void send (String msg) {
		PrintWriter out = this.getWriter();
		out.write(msg + "\r\n");
		out.flush();
	}


	private void brodcast(String msg) {
		for(ClientThread thatClient : server.getClients()){
			PrintWriter thatClientOut = thatClient.getWriter();
			if(thatClientOut != null){
				thatClientOut.write(msg + "\r\n");
				thatClientOut.flush();
			}
		}
	}
}