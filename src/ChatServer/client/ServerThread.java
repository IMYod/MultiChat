// place this file the path such ends with: ChatServer/client/ServerThread.java

package ChatServer.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ServerThread implements Runnable {
	private Socket socket;
	private String userName;
	private boolean isAlived;
	private final LinkedList<String> messagesToSend;
	private boolean hasMessages = false;

	public ServerThread(Socket socket, String userName){
		this.socket = socket;
		this.userName = userName;
		messagesToSend = new LinkedList<String>();
	}

	public void addNextMessage(String message){
		synchronized (messagesToSend){
			hasMessages = true;
			messagesToSend.push(message);
		}
	}

	@Override
	public void run(){
		System.out.println("Welcome :" + userName);

		System.out.println("Local Port :" + socket.getLocalPort());
		System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

		try{
			PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
			InputStream serverInStream = socket.getInputStream();
			Scanner serverIn = new Scanner(serverInStream);
			while(!socket.isClosed()){
				if(serverInStream.available() > 0){
					if(serverIn.hasNextLine()){
						System.out.println(serverIn.nextLine());
					}
				}
				if(hasMessages){
					String nextSend = "";
					synchronized(messagesToSend){
						nextSend = messagesToSend.pop();
						hasMessages = !messagesToSend.isEmpty();
					}
					serverOut.println(userName + ">>>" + nextSend);
					serverOut.flush();
				}
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

	}

}
