package ChatServer.server;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.rmi.server.ServerCloneException;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class _JUnitTest {

	public static boolean doIt;

	@Test
	void runServer() {		  

		doIt = true;
		Thread oneServer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ChatServer server = new ChatServer(4444);
					server.startServer();
				} catch (Exception e) {
					_JUnitTest.doIt = false;
				}
			}
		});	
		oneServer.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(doIt);
		return;
	}


}
