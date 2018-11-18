package multithreadedServer;

/*
 * This class implements a simple multithreaded server 
 * that processes the incoming requests in the one thread (is called a listening thread)
 * and hands off the client connection to a other thread (is called a worker thread) that will process the request. 
 * --- one thread per each client connection ---
 */

/**
 *
 * @author annaf
 */

import java.io.*;
import java.lang.reflect.Member;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class SimpleMultithreadedServer implements Runnable{

	int          serverPort   = 45000; // The port that this server is listening on
	ServerSocket serverSocket = null;  // Server socket that will listen for incoming connections
	Thread       runningThread = null;
	boolean      isStopped    = false;
	private ArrayList<WorkerRunnable> workerList = new ArrayList<>();

	public SimpleMultithreadedServer(int port){
		this.serverPort = port;
		workerList = new ArrayList<>();
	}

	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		//open server Socket
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			System.err.println("Cannot listen on this port.\n" + e.getMessage());
			System.exit(1);
		}    
		while(!isStopped()){
			Socket clientSocket = null;  // socket created by accept

			try {
				clientSocket = this.serverSocket.accept(); // wait for a client to connect

			} catch (IOException e) {
				if(isStopped()) {
					System.out.println("IOException: Server Stopped.") ;
					return;
				}
				throw new RuntimeException(
						"Error accepting client connection", e);    //Accept failed
			}
			//server code here ...
			System.out.println("Server accepts the client connection");

			// Client information               
			InetAddress addr = clientSocket.getInetAddress();
			System.out.println("Server: Received a new connection from ("+ addr.getHostAddress()  + "): " + addr.getHostAddress() + " on port: " + clientSocket.getPort());
			String clientInfo="";
			clientInfo= "Client on port " + clientSocket.getPort();
			new Thread(new WorkerRunnable(this, clientSocket, clientInfo)).start();
		}

		System.out.println("Server Stopped.");            

	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}

	public ArrayList<WorkerRunnable> getWorkerList() {
		return workerList;
	}



	public int indexOf(String name) {
		for (WorkerRunnable wr : workerList)
			if (wr.name.equals(name))
				return workerList.indexOf(wr);
		return -1;
	}

	public String[] getMembers() {
		String[] members = new String[this.workerList.size()];
		Iterator<WorkerRunnable> workersIterator = workerList.iterator();
		int i=0;
		while (workersIterator.hasNext()) {
			members[i] = workersIterator.next().name;
		}
		return members; 
	}

	public void add(WorkerRunnable wr) {
		this.workerList.add(wr);
	}
}