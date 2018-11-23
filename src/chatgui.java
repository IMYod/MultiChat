import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ChatServer.client.ServerThread;

import java.awt.GridLayout;
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

public class chatgui extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField textField;
	private ServerThread serverThread;

	/**
	 * Launch the application.
	 */
	public chatgui (ServerThread _serverThread) {
		super();
		serverThread = _serverThread;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					chatgui frame = new chatgui();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}

	/**
	 * Create the frame.
	 */
	public chatgui() {
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
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(59, 57, 681, 420);
		contentPane.add(textArea);
		
		JButton btSend = new JButton("Send");
		btSend.setFont(new Font("Tahoma", Font.BOLD, 14));
		btSend.setBounds(627, 487, 113, 36);
		contentPane.add(btSend);
		
		JTextArea textAreaSend = new JTextArea();
		textAreaSend.setBounds(59, 487, 558, 25);
		contentPane.add(textAreaSend);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBounds(485, 11, 103, 36);
		contentPane.add(btnClear);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(10, 11, 74, 22);
		contentPane.add(lblName);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setColumns(10);
		textField.setBounds(297, 11, 129, 22);
		contentPane.add(textField);
		
		JLabel lbAddress = new JLabel("Address");
		lbAddress.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbAddress.setBounds(210, 11, 74, 22);
		contentPane.add(lbAddress);
		
		JButton btShowOnline = new JButton("Show Online");
		btShowOnline.setFont(new Font("Tahoma", Font.BOLD, 14));
		btShowOnline.setBounds(611, 11, 129, 36);
		contentPane.add(btShowOnline);
		

	}
	
}
