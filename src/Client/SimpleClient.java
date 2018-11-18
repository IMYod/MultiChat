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

	public static void main(String[] args) throws IOException, InterruptedException {

		Socket socket = null;       //Socket object for communicating
		PrintWriter out = null;    //socket output to server - for sending data through the socket to the server
		BufferedReader in = null;  //socket input from server - for reading server's response
		String name = null;

		name = randomName();//change it on GUI!!!
		System.out.println("Mu name is: " + name);

		try {
			socket = new Socket("127.0.0.1", 45000);   //establish the socket connection between the client and the server
			out = new PrintWriter(socket.getOutputStream(), true);  //open a PrintWriter on the socket
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));  //open a BufferedReader on the socket
			
			System.out.println("Enter your message, to end write stop");
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in)); //for reading user input (line at a time from the standard input stream)
			
			String userInput = "name>>>" + name;
			
			do { //first of all send the name
				out.println(userInput);     //sends to the server immediately
				System.out.println(in.readLine());  // waits until the server sends back data,reads and prints it
				if (userInput.equals("stop"))
					break;
			} while ((userInput = br.readLine()) != null);

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

	public static String randomName() { //temp function!!!
		String randName = "";
		do {
			randName += (char)((int)(Math.random()*26)+65);
		}while (Math.random() > 0.3);
		return randName;
	}
}
