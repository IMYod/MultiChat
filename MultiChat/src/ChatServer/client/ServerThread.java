
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
/**
 * This class for the thread for the client!!
 * All the information is transform by those threads.
 * @author Elad and Yoav
 *
 */
public class ServerThread implements Runnable 
{
	public Socket socket;
	protected String userName;
	protected boolean isAlived;
	protected final LinkedList<String> messagesToSend;
	protected boolean hasMessages = false;
	protected Scanner serverIn;
	protected InputStream serverInStream;
	protected PrintWriter serverOut;

	public ServerThread(Socket socket, String userName)
	{

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
	public void run()
	{
		System.out.println("Welcome: " + userName);

		System.out.println("Local Port :" + socket.getLocalPort());
		System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

		try
		{
			serverOut = new PrintWriter(socket.getOutputStream(), false);
			serverInStream = socket.getInputStream();
			serverIn = new Scanner(serverInStream);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

}
