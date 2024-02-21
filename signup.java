package AttendanceDatabase;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import AttendanceDatabase.Connection1;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class signup extends JFrame {

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JPanel contentPane;
	private JPasswordField newPass;
	private JTextField newUser;
	private JTextField newLname;
	private JTextField newFname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup frame = new signup();
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
	public signup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 957, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel contentPane_1 = new JPanel();
		contentPane_1.setBackground(SystemColor.inactiveCaption);
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(contentPane_1, BorderLayout.CENTER);

		JLabel lblRegistrationTab = new JLabel("R E G I S T R A T I ON ");
		lblRegistrationTab.setForeground(new Color(0, 0, 128));
		lblRegistrationTab.setFont(new Font("Perpetua Titling MT", Font.BOLD, 35));
		lblRegistrationTab.setBounds(286, 49, 441, 51);
		contentPane_1.add(lblRegistrationTab);

		JLabel lblNewLabel_1 = new JLabel("NEW USERNAME:");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1.setBounds(104, 316, 128, 33);
		contentPane_1.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("NEW PASSWORD:");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(104, 390, 128, 33);
		contentPane_1.add(lblNewLabel_1_1);

		JComboBox courses = new JComboBox();
		courses.setFont(new Font("Arial", Font.BOLD, 15));
		courses.setModel(new DefaultComboBoxModel(new String[] {"SELECT COURSE", "BS ENGINEERING", "BS CRIMINOLOGY", "BS ACCOUNTANCY", "BS EDUCATION", "BS NURSING"}));
		courses.setBounds(389, 110, 162, 33);
		contentPane_1.add(courses);

		JButton register = new JButton("REGISTER NOW");
		register.setFont(new Font("Tahoma", Font.BOLD, 15));
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = newFname.getText();
				String lname = newLname.getText();
				String course = courses.getSelectedItem().toString();
				String username = newUser.getText();
				String password = newPass.getText();
				String options = "STUDENT";

				if (fname.equals("") || lname.equals("") || course.equals("SELECT COURSE") || username.equals("")
						|| password.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "Some Fields Are Missing", "Error", 2);
				} else {
					try {
						con = Connection1.getConnection();
						String insertSql = "INSERT INTO users (username, password, options, course) VALUES (?,?,?,?)";
						pst = con.prepareStatement(insertSql);
						pst.setString(1, username);
						pst.setString(2, password);
						pst.setString(3, options);
						pst.setString(4, course);
						
						pst.executeUpdate();

						String insertSql2 = "INSERT INTO info (fname, lname, timeIN, timeOUT) VALUES ( ?, ?,?, ?)";
						pst = con.prepareStatement(insertSql2);
						pst.setString(1, fname);
						pst.setString(2, lname);
						pst.setString(3, "");
						pst.setString(4, "");
						pst.executeUpdate();

					} catch (Exception a) {
						a.printStackTrace();
					}

					infoMessage("YOU ARE NOW REGISTER!");
					newUser.setText("");
					newPass.setText("");
					newFname.setText("");
					newLname.setText("");
					courses.setSelectedItem("SELECT COURSE");
				}
			}

			private void infoMessage(String message) {
				JOptionPane.showMessageDialog(null, message);

			}
		});
		register.setBounds(389, 464, 181, 33);
		contentPane_1.add(register);

		newPass = new JPasswordField();
		newPass.setHorizontalAlignment(SwingConstants.CENTER);
		newPass.setFont(new Font("Tahoma", Font.BOLD, 20));
		newPass.setBounds(267, 382, 480, 51);
		contentPane_1.add(newPass);

		JButton btnNewButton = new JButton("BACK TO LOGIN");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Main Info = new Main();
				Info.setVisible(true);
				setVisible(false);
				
		
			}
		});
		btnNewButton.setBounds(172, 464, 181, 33);
		contentPane_1.add(btnNewButton);

		JLabel lblNewLabel_1_2 = new JLabel("SURNAME:");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(148, 244, 84, 33);
		contentPane_1.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_2_1 = new JLabel("FIRST NAME:");
		lblNewLabel_1_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(136, 170, 96, 33);
		contentPane_1.add(lblNewLabel_1_2_1);

		JButton btnClear = new JButton("CLEAR FORM\r\n");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newUser.setText("");
				newPass.setText("");
				newFname.setText("");
				newLname.setText("");

			}
		});
		btnClear.setBounds(604, 464, 181, 33);
		contentPane_1.add(btnClear);

		newUser = new JTextField();
		newUser.setHorizontalAlignment(SwingConstants.CENTER);
		newUser.setFont(new Font("Tahoma", Font.BOLD, 20));
		newUser.setBounds(267, 310, 480, 48);
		contentPane_1.add(newUser);
		newUser.setColumns(10);

		newLname = new JTextField();
		newLname.setHorizontalAlignment(SwingConstants.CENTER);
		newLname.setFont(new Font("Tahoma", Font.BOLD, 20));
		newLname.setColumns(10);
		newLname.setBounds(267, 238, 480, 48);
		contentPane_1.add(newLname);

		newFname = new JTextField();
		newFname.setHorizontalAlignment(SwingConstants.CENTER);
		newFname.setFont(new Font("Tahoma", Font.BOLD, 20));
		newFname.setColumns(10);
		newFname.setBounds(267, 164, 480, 48);
		contentPane_1.add(newFname);

	}
}
