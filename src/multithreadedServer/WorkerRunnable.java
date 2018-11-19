package multithreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Client.ClientMessage;

public class WorkerRunnable implements Runnable {

	protected SimpleMultithreadedServer server;
	protected Socket clientSocket = null;
	protected String serverText   = null;
	protected String name;

	private PrintWriter out;
	private BufferedReader in;

	public WorkerRunnable(SimpleMultithreadedServer _server, Socket clientSocket, String serverText) {
		this.server = _server;
		this.clientSocket = clientSocket;
		this.serverText   = serverText;
	}

	public void run() {
		try {
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			String msg = "";
			ClientMessage cMessage = new ClientMessage(msg); 

			while ((msg = in.readLine()) != null) {
				System.out.println("message: " + msg + ". from: " + this.name);
				cMessage = new ClientMessage(msg);
				if (cMessage.toConnect == true) {
					this.name = cMessage.message;
					if (server.indexOf(name) > -1) { //check if this name free
						send("That name is already taken");
						close();
						return;
					}
					server.add(this); //add this client to the chat
					sendAll("wellcome " + name); 
					send("wellcome " + name);
				}
					
				else if (cMessage.toStop) {
					sendAll(serverText + "stopped.");
					send("bye");
					stop();
					return;
				}
				else if (cMessage.getList)
					send(Arrays.toString(server.getMembers()));
				else if (cMessage.forAll)
					sendAll(cMessage.message);
				else if (cMessage.forClient) {
					int indexOf = server.getWorkerList().indexOf(cMessage.ClientName);
					if (indexOf > -1)
						server.getWorkerList().get(indexOf).send(cMessage.message);
					else
						send("The client " + cMessage.ClientName + " is not connected");
				}
			}
			close();

		} catch (IOException e) {
			System.err.println("Error " + e.getMessage());	               
		}
	}
	private void stop() throws IOException {
		int index = server.indexOf(this.name);
		server.getWorkerList().remove(index);
		sendAll(name + "leaved");
		close();
	}

	private void close() throws IOException {
		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error " + e.getMessage());	               
		}

	}

	private void sendAll(String message) {
		for (WorkerRunnable wr : server.getWorkerList())
			if (! wr.equals(this))
				wr.send(message);
	}

	private void send(String message) {
		System.out.println("send " + message + " to " + this.name);
		out.println(message);
	}
}
