// place this file the path such ends with: ChatServer/server/ClientThread.java

package ChatServer.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientThread implements Runnable {
	private Socket socket;
	private PrintWriter clientOut;
	private ChatServer server;
	protected String clientName;

	public ClientThread(ChatServer server, Socket socket){
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
			PrintWriter out = this.getWriter();
						
			if(in.hasNextLine()){ //check if his name exist
				String userName = in.nextLine();
				ClientMessage cMessage = new ClientMessage(userName);
				if (cMessage.toConnect == true) 
					this.clientName = cMessage.message;
				if (server.contains(clientName)) {
					out.write("This name already exist. Try connect again with other name!");
					out.flush();
					close();
				}

			}

			// start communicating
			while(!socket.isClosed()){
				if(in.hasNextLine()){
					String input = in.nextLine();
					System.out.println(input);//delete it??????????????????
					
					ClientMessage cMessage = new ClientMessage(input);
					if (cMessage.toStop) {
						brodcast(clientName + " leaved.");
						out.write("bye");
						out.flush();
						close();
					}
					else if (cMessage.getList)
						send(server.getUsers());
					else if (cMessage.forAll)
						brodcast(cMessage.message);
					else if (cMessage.forClient) {
						if (server.contains(cMessage.ClientName))
							sendTo(cMessage.ClientName, cMessage.message);
						else
							send("The client " + cMessage.ClientName + " is not connected");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendTo(String clientNameSendTo, String message) {
		for (ClientThread clientT : server.clients)
    		if (clientNameSendTo.equals(clientT.clientName))
    			clientT.send(message);
	}

	private void send (String msg) {
		PrintWriter out = this.getWriter();
		out.write(msg);
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

	private void close() {
		//TODO
	}
}
