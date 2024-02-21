package AttendanceDatabase;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Main extends JFrame {

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setForeground(SystemColor.window);
		setBackground(SystemColor.window);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 995, 598);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ATTENDANCE SYSTEM");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Perpetua Titling MT", Font.BOLD, 35));
		lblNewLabel.setBounds(155, 70, 650, 51);
		contentPane.add(lblNewLabel);

		user = new JTextField();
		user.setHorizontalAlignment(SwingConstants.CENTER);
		user.setFont(new Font("Tahoma", Font.BOLD, 20));
		user.setBounds(235, 236, 480, 51);
		contentPane.add(user);
		user.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("USERNAME\r\n:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(427, 205, 221, 33);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("PASSWORD:\r\n");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(427, 305, 221, 33);
		contentPane.add(lblNewLabel_1_1);

		JComboBox userType = new JComboBox();
		userType.setToolTipText("SELECT TYPE OF USER\r\n");
		userType.setFont(new Font("Arial", Font.BOLD, 15));
		userType.setBackground(Color.LIGHT_GRAY);
		userType.setModel(new DefaultComboBoxModel(new String[] { "SELECT", "ADMIN", "STUDENT" }));
		userType.setBounds(425, 159, 93, 21);
		contentPane.add(userType);

		JButton LOGIN = new JButton("LOGIN");
		LOGIN.setFont(new Font("Tahoma", Font.BOLD, 15));
		LOGIN.setBackground(SystemColor.control);
		LOGIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = user.getText();
				String password = pass.getText();
				String options = userType.getSelectedItem().toString();

				if (username.equals("") || password.equals("") || options.equals("SELECT")) {
					JOptionPane.showMessageDialog(rootPane, "Some Fields Are Missing", "Error", 2);
				} else {
					try {
						con = Connection1.getConnection();
						pst = con.prepareStatement(
								"SELECT * FROM users WHERE BINARY username=? AND BINARY password=? AND options=?");
						pst.setString(1, username);
						pst.setString(2, password);
						pst.setString(3, options);
						rs = pst.executeQuery();

						if (rs.next()) {
							String name = rs.getString("username");
							String pass = rs.getString("password");

							String s1 = rs.getString("options");

							if (options.equalsIgnoreCase("ADMIN") && s1.equalsIgnoreCase("ADMIN")) {
								adminView ad = new adminView();
								ad.setVisible(true);
								setVisible(false);
								;
							}
							if (options.equalsIgnoreCase("STUDENT") && s1.equalsIgnoreCase("STUDENT")) {

								String course = rs.getString("course");

								attendance at = new attendance(name, course, pass);
								at.setVisible(true);
								setVisible(false);

							}
						} else {
							JOptionPane.showMessageDialog(rootPane, "Invalid Credentials!", "Login Error", 2);
						}
					} catch (Exception a) {
						a.printStackTrace();
					}

				}

			}
		});
		LOGIN.setBounds(536, 445, 179, 40);
		contentPane.add(LOGIN);

		JButton btnNewButton_1 = new JButton("SIGN UP");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBackground(SystemColor.control);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				signup Info = new signup();
				Info.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(235, 445, 179, 40);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel_1_2 = new JLabel("NEW STUDENT?");
		lblNewLabel_1_2.setForeground(SystemColor.textHighlight);
		lblNewLabel_1_2.setFont(new Font("Arial", Font.BOLD, 10));
		lblNewLabel_1_2.setBounds(283, 418, 105, 17);
		contentPane.add(lblNewLabel_1_2);

		pass = new JPasswordField();
		pass.setHorizontalAlignment(SwingConstants.CENTER);
		pass.setFont(new Font("Tahoma", Font.BOLD, 20));
		pass.setBounds(235, 334, 480, 51);
		contentPane.add(pass);

		JLabel lblNewLabel_1_2_1 = new JLabel("TYPE OF USER");
		lblNewLabel_1_2_1.setForeground(SystemColor.textHighlight);
		lblNewLabel_1_2_1.setFont(new Font("Arial", Font.BOLD, 10));
		lblNewLabel_1_2_1.setBounds(432, 143, 86, 17);
		contentPane.add(lblNewLabel_1_2_1);
	}
}
