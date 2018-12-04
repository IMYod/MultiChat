
package ChatServer.client;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 *  This class is a client side, which actually opens Socket and Port in order to connect to the server.
 *  This class will run the GUI, for the user.
 *  But in order to activate the Client, you //must// first start the server!!! that can be connected to something.
 * @author Yoav & Elad. 
 *
 */
public class Client
{
	private static final String host = "localhost";
	private static final int portNumber = 4444;
	public String userName;
	private String serverHost;
	private int serverPort;
	private Scanner userInputScanner;
	public static chatgui ChatFrame = null;
	public static logInGui login=null;

	public static void main(String[] args)
	{
		login = new logInGui();
		login.setVisible(true);
		boolean wait = false;
		while(wait ==false) 
		{
			try 
			{
				Thread.sleep(200);
			} 

			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (login.flag==true)
			{
				login.setVisible(false);
				Client client = new Client(login.userName, host, portNumber);
				Scanner scan = new Scanner(System.in);
				client.startClient(scan);
			}
		}
	}

	private Client(String userName, String host, int portNumber)
	{
		this.userName = userName;
		this.serverHost = host;
		this.serverPort = portNumber;
	}

	private void startClient(Scanner scan)
	{
		try
		{
			Socket socket = new Socket(serverHost, serverPort);
			ServerThread serverThread = new ServerThread(socket, userName);
			Thread.sleep(1000); // waiting for network communicating.
			Thread serverAccessThread = new Thread(serverThread);
			serverAccessThread.start();	
			Thread.sleep(1000); // waiting for accepting the user name.	
			serverThread.addNextMessage("ping");//send the user name
			ChatFrame = new chatgui(serverThread);		
			ChatFrame.setVisible(true);

		}

		catch(IOException ex)
		{
			System.err.println("Fatal Connection error!");
			ex.printStackTrace();
		}

		catch(InterruptedException ex)
		{
			System.out.println("Interrupted");
		}
	}
}
