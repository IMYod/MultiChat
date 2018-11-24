// place this file the path such ends with: ChatServer/client/Client.java

package ChatServer.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {

	private static final String host = "localhost";
	private static final int portNumber = 4444;

	public String userName;
	private String serverHost;
	private int serverPort;
	private Scanner userInputScanner;
	public static chatgui ChatFrame = null;
	public static logInGui login=null;

	//	public Client (Client other) 
	//	{
	//		this.userName= other.userName;
	//		this.serverHost=other.serverHost;
	//		this.serverPort=other.serverPort;
	//		this.userInputScanner=other.userInputScanner;
	//	}
	public static void main(String[] args){
		login = new logInGui();
		login.setVisible(true);
		boolean wait = false;

		//		String readName = null;
		//		Scanner scan = new Scanner(System.in);
		//		System.out.println("Please input username:");
		//		while(readName == null || readName.trim().equals("")){
		//			// null, empty, whitespace(s) not allowed.
		//			readName = scan.nextLine();
		//			if(readName.trim().equals("")){
		//				System.out.println("Invalid. Please enter again:");
		//			}
		while(wait ==false) 
		{
			if (login.flag==true)
			{
				login.setVisible(false);
				Client client = new Client(login.userName, host, portNumber);
				Scanner scan = new Scanner(System.in);
				client.startClient(scan);
			}
		}
	}
	//		
	//		
	//
	//
	//	}

	private Client(String userName, String host, int portNumber){
		this.userName = userName;
		this.serverHost = host;
		this.serverPort = portNumber;
	}

	private void startClient(Scanner scan){
		try{
			Socket socket = new Socket(serverHost, serverPort);
			ChatFrame = new chatgui();
			ChatFrame.txtName.setText(this.userName);
			ChatFrame.textaddress.setText(this.serverHost);
			ChatFrame.setVisible(true);
			Thread.sleep(1000); // waiting for network communicating.
			ServerThread serverThread = new ServerThread(socket, userName);
			Thread serverAccessThread = new Thread(serverThread);
			serverAccessThread.start();

			serverThread.addNextMessage("ping");//send the user name

			while(serverAccessThread.isAlive()){
				if(scan.hasNextLine()){
					serverThread.addNextMessage(scan.nextLine());
				}
				else {
					Thread.sleep(250);
				}
			}
		}catch(IOException ex){
			System.err.println("Fatal Connection error!");
			ex.printStackTrace();
		}catch(InterruptedException ex){
			System.out.println("Interrupted");
		}
	}
}
