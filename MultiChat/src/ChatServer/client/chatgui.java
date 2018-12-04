
package ChatServer.client;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ChatServer.client.ServerThread;

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
 * This class is the GUI for the chat.
 * Here you can actually see the whole conversation of the customer, 
 * in front of other customers or in front of the server.
 * 
 *@author Yoav and Elad.
 */
public class chatgui extends JFrame {

	private JPanel contentPane;
	public JTextField txtName;
	public JTextField textaddress;
	private ServerThread serverThread;
	private JTextArea textAreaSend;
	private JTextArea textArea;
	

	/**
	 * Create the frame.
	 * The constructor of the Chatgui gets thread from the server,
	 * Thats how the Gui knows how to read the messages from the clients.  
	 */
	public chatgui(ServerThread _serverThread) {
		serverThread = _serverThread;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtName.setBounds(73, 11, 113, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(59, 57, 681, 420);
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", 0, 14));
		textArea.setText(welcomeText());
//		contentPane.add(textArea);
		
		JScrollPane sp = new JScrollPane(textArea);
		sp.setForeground(Color.BLACK);
		sp.setBackground(Color.WHITE);
		sp.setBounds(59, 57, 681, 420);
		contentPane.add(sp);
		
		JButton btSend = new JButton("Send");
		btSend.setFont(new Font("Tahoma", Font.BOLD, 14));
		btSend.setBounds(627, 487, 113, 36);
		contentPane.add(btSend);
		btSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				serverThread.addNextMessage(textAreaSend.getText());
				textAreaSend.setText("");
			}
		});
		
		textAreaSend = new JTextArea();
		textAreaSend.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));
		textAreaSend.setBounds(59, 487, 558, 25);
		contentPane.add(textAreaSend);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBounds(485, 11, 103, 36);
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		contentPane.add(btnClear);
		
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(10, 11, 74, 22);
		contentPane.add(lblName);
		
		textaddress = new JTextField();
		textaddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		textaddress.setColumns(10);
		textaddress.setBounds(297, 11, 129, 22);
		contentPane.add(textaddress);
		
		JLabel lbAddress = new JLabel("Address");
		lbAddress.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbAddress.setBounds(210, 11, 74, 22);
		contentPane.add(lbAddress);
		
		JButton btShowOnline = new JButton("Show Online");
		btShowOnline.setFont(new Font("Tahoma", Font.BOLD, 14));
		btShowOnline.setBounds(611, 11, 129, 36);
		btShowOnline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				serverThread.addNextMessage("getList");
			}
		});
		contentPane.add(btShowOnline);
		
		this.setVisible(true);
		this.textaddress.setText(""+serverThread.socket.getLocalPort());
		this.txtName.setText(serverThread.userName);
		
		boolean stop = false;
		
		while(! serverThread.socket.isClosed() && !stop){
			try {
				Thread.sleep(200);
				if(serverThread.serverInStream.available() > 0) {
					if(serverThread.serverIn.hasNextLine()){
						addText(serverThread.serverIn.nextLine());
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(serverThread.hasMessages){
				String nextSend = "";
				synchronized(serverThread.messagesToSend){
					nextSend = serverThread.messagesToSend.pop();
					if (nextSend == "stop")
						stop = true;
					serverThread.hasMessages = !serverThread.messagesToSend.isEmpty();
				}
				serverThread.serverOut.println(serverThread.userName + ">>>" + nextSend);
				serverThread.serverOut.flush();
			}
		}

		try {
			contentPane.setVisible(false);
			Thread.sleep(1000);
			serverThread.socket.close();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private String welcomeText() {
		return ("\n" +
				"Welcome " + serverThread.userName + "!\n" +
				"For sending brodcast, write: brodcast>>>your massage\n" +
				"For sending message to one user, write: chat>>>user name>>>your meesage\n" +
				"And finally, before you leave, write: stop\n");
	}
	
	private void addText(String nextLine) {
//		textArea.append(nextLine + "\n");
		textArea.insert(nextLine + "\n", 0);
	}
	
}
