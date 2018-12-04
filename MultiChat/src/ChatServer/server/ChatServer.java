package ChatServer.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a client side , Here the server can manage all his clients.
 * The Server knows to open socket for each client. and so on.
 * @author Yoav and Elad.
 *
 */
public class ChatServer {

    private static final int portNumber = 4444;

    private int serverPort;
    protected List<ClientThread> clients; // or "protected static List<ClientThread> clients;"
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber)
    {
        this.serverPort = portNumber;
    }

    public List<ClientThread> getClients()
    {
        return clients;
    }

    void startServer()
    {
        clients = new ArrayList<ClientThread>();
        ServerSocket serverSocket = null;
        try 
        {
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket);
        } 
        catch (IOException e)
        {
            System.err.println("Could not listen on port: "+serverPort);
            System.exit(1);
        }
    }

    private void acceptClients(ServerSocket serverSocket)
    {
        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while(true){
            try
            {
                try 
                {
					Thread.sleep(100);
				} 
                catch (InterruptedException e)
                {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Socket socket = serverSocket.accept();
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                ClientThread client = new ClientThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            }
            catch (IOException ex)
            {
                System.out.println("Accept failed on : "+serverPort);
            }
        }
    }
    protected String getUsers() {
    	StringBuilder sb = new StringBuilder("[");
    	for (ClientThread client : clients)
    		sb.append(client.clientName + ",");
    	sb.deleteCharAt(sb.length()-1);
    	sb.append("]");
    	return sb.toString();
    }
    
    protected boolean contains (String name) {
    	for (ClientThread client : clients)
    		if (name.equals(client.clientName))
    			return true;
    	return false;
    }
}
