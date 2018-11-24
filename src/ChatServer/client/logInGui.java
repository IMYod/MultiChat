package ChatServer.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class logInGui extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUserName;
	public String userName="";
	public boolean flag = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logInGui frame = new logInGui();
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
	public logInGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		textFieldUserName = new JTextField();
		textFieldUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUserName.setColumns(10);


		JLabel lblEnterYourName = new JLabel("Enter your name please");
		lblEnterYourName.setFont(new Font("Yu Gothic", Font.BOLD, 20));

		JButton btnEnter = new JButton("Enter");
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(82)
										.addComponent(lblEnterYourName, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(118)
										.addComponent(textFieldUserName, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(149)
										.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(89, Short.MAX_VALUE)));
		btnEnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				while (flag == false)
				{
					if(textFieldUserName.getText()!="")
					{
						userName = textFieldUserName.getText();
						flag = true;
					}
				}
			}
		});


		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(60)
						.addComponent(lblEnterYourName)
						.addGap(18)
						.addComponent(textFieldUserName, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(42, Short.MAX_VALUE))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
