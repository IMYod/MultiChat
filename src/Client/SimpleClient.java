package Client;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*  This class implements a simple client that connects to the server
 *  i.e illustrates how establish a connection to a server program using the Socket class
 *  and then, how the client can send data to and receive data from the server through the socket.
 */

/**
 *
 * @author annaf
 */

import java.io.*;
import java.net.*;

public class SimpleClient {

	int port;
	public Socket socket = null;       //Socket object for communicating
	public PrintWriter out = null;    //socket output to server - for sending data through the socket to the server
	public BufferedReader in = null;  //socket input from server - for reading server's response
	public String name = null;

	public SimpleClient() throws UnknownHostException, IOException, InterruptedException {
		socket = new Socket("127.0.0.1", 45000);
		name = randomName();//delete this on GUI!!!
		connect();
	}
	
	public SimpleClient(int _port, String host, String _name) throws UnknownHostException, IOException, InterruptedException {
		socket = new Socket(host, port);   //establish the socket connection between the client and the server
		name = _name;
		connect();
	}

	public void connect() throws IOException, InterruptedException {
		try {
			
			out = new PrintWriter(socket.getOutputStream(), true);  //open a PrintWriter on the socket
			out.println("name>>>" + name);
			startMessageReader();
			
			System.out.println("Enter your message, to end write stop");

			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in)); //for reading user input (line at a time from the standard input stream)

			String userInput = "";
			while ((userInput = ) != null) {
				out.println(userInput);     //sends to the server immediately
				if (userInput.equals("stop"))
					break;
			}

			System.out.println("CLIENT: I LEAVED");

			//closing any streams connected to a socket  before closing this socket
			out.close();
			in.close();
			br.close();
			socket.close();

		} catch (UnknownHostException e) {
			System.out.println("Don't know about this host\n" + e.getMessage());            
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Couldn't get I/O for "
					+ "the connection to this host\n" + e.getMessage());
			System.exit(1);
		}

	}

	private void startMessageReader() {
		ThreadSocket t = new ThreadSocket(socket);
		t.start();
	}

	public static String randomName() { //delete it when you connect to GUI!!!!
		String randName = "";
		do {
			randName += (char)((int)(Math.random()*26)+65);
		}while (Math.random() > 0.3);
		return randName;
	}
	
	public static void main(String[] args) {
		try {
			SimpleClient client1 = new SimpleClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
	}
}
