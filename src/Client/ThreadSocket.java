package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadSocket extends Thread {

	private Socket socket;
	
	public ThreadSocket(Socket _socket) {
		socket = _socket;
	}
	
	public void run() {
		try {
			String line = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));  //open a BufferedReader on the socket
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in)); //for reading user input (line at a time from the standard input stream)

			while ((line = br.readLine()) != null) {
				System.out.println(in.readLine());
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
