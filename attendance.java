package AttendanceDatabase;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.time.format.DateTimeFormatter;
import AttendanceDatabase.Connection1;
import java.time.LocalDateTime;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class attendance extends JFrame {

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JPanel contentPane;
	private JTextField fname;
	private JTextField lname;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					attendance frame = new attendance("", "", "");
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

	public attendance(String name, String course, String pass) {

		int userIdToUpdate = 0;
		String showUser = "";
		String showPass = "";
		String showName = "";
		String showLast = "";
		String showCourse = "";
		try {
			con = Connection1.getConnection();
			String selectSql = "SELECT * FROM users WHERE username=? AND password=?";
			pst = con.prepareStatement(selectSql);
			pst.setString(1, name);
			pst.setString(2, pass);
			ResultSet resultSet = pst.executeQuery();

			if (resultSet.next()) {
				userIdToUpdate = resultSet.getInt("id");
				showUser = resultSet.getString("username");
				showPass = resultSet.getString("password");
				showCourse = resultSet.getString("course");

			}

			String selectSql2 = "SELECT fname, lname FROM info WHERE id=?";
			pst = con.prepareStatement(selectSql2);
			pst.setInt(1, userIdToUpdate);
			ResultSet resultSet1 = pst.executeQuery();

			if (resultSet1.next()) {
				showName = resultSet1.getString("fname");
				showLast = resultSet1.getString("lname");
			}

		} catch (Exception a) {
			a.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1039, 593);
		contentPane = new JPanel();
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel nameLABEL = new JLabel(name);
		nameLABEL.setBackground(Color.LIGHT_GRAY);
		nameLABEL.setForeground(Color.BLUE);
		nameLABEL.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLABEL.setBounds(61, 139, 201, 38);
		contentPane.add(nameLABEL);

		JLabel courses = new JLabel(course);
		courses.setForeground(Color.BLUE);
		courses.setFont(new Font("Tahoma", Font.BOLD, 20));
		courses.setBounds(23, 151, 226, 62);
		contentPane.add(courses);

		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
		String currentDay = currentDayOfWeek.toString();
		currentDay = currentDay.toUpperCase();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(244, -27, 811, 589);
		contentPane.add(tabbedPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel dayLabel = new JLabel("Today is " + currentDay);
		dayLabel.setForeground(new Color(30, 144, 255));
		dayLabel.setBounds(262, 60, 328, 38);
		panel_1.add(dayLabel);
		dayLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel dateTimeLabel = new JLabel("Current Date and Time: " + formattedDateTime);
		dateTimeLabel.setBounds(164, 30, 504, 38);
		panel_1.add(dateTimeLabel);
		dateTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 17));

		
		String savedTimeIn = "";
		String savedTimeOut = "";
		try {
			con = Connection1.getConnection();

			String updateOUT = "SELECT timeIN FROM info WHERE id=?";
			pst = con.prepareStatement(updateOUT);
			pst.setInt(1, userIdToUpdate);
			rs = pst.executeQuery();

			if (rs.next()) {
				savedTimeIn = rs.getString("timeIN");
			}

		} catch (Exception a) {
			a.printStackTrace();
		}

		JLabel timeInLabel = new JLabel("Time In: " + savedTimeIn);
		timeInLabel.setForeground(Color.RED);
		timeInLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		timeInLabel.setBackground(Color.CYAN);
		timeInLabel.setBounds(257, 149, 535, 113);
		panel_1.add(timeInLabel);

		try {
			con = Connection1.getConnection();

			String updateOUT = "SELECT timeOUT FROM info WHERE id=?";
			pst = con.prepareStatement(updateOUT);
			pst.setInt(1, userIdToUpdate);
			rs = pst.executeQuery();

			if (rs.next()) {
				savedTimeOut = rs.getString("timeOUT");
			}

		} catch (Exception a) {
			a.printStackTrace();
		}

		JLabel timeOutLabel = new JLabel("Time Out: " + savedTimeOut);
		timeOutLabel.setForeground(Color.RED);
		timeOutLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		timeOutLabel.setBackground(SystemColor.activeCaption);
		timeOutLabel.setBounds(260, 314, 535, 113);
		panel_1.add(timeOutLabel);

		JButton timeIN = new JButton("TIME IN\r\n");
		timeIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int userIdToUpdate = 0;
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
				String formattedDateTime = now.format(formatter);

				try {
					timeInLabel.setText("Time In:      " + formattedDateTime);

					con = Connection1.getConnection();
					String selectSql = "SELECT id FROM users WHERE username=? AND password=?";
					pst = con.prepareStatement(selectSql);
					pst.setString(1, name);
					pst.setString(2, pass);
					ResultSet resultSet = pst.executeQuery();

					if (resultSet.next()) {
						userIdToUpdate = resultSet.getInt("id");
					}

					String updateIN = "UPDATE info SET timeIN=? WHERE id=?";
					pst = con.prepareStatement(updateIN);
					pst.setString(1, formattedDateTime);
					pst.setInt(2, userIdToUpdate);
					pst.executeUpdate();

				} catch (Exception a) {
					a.printStackTrace();
				}

			}
		});
		timeIN.setBounds(43, 184, 134, 47);
		panel_1.add(timeIN);

		JButton timeOUT = new JButton("TIME OUT\r\n");
		timeOUT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userIdToUpdate = 0;
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
				String formattedDateTime = now.format(formatter);

				try {
					timeOutLabel.setText("Time Out:      " + formattedDateTime);

					con = Connection1.getConnection();
					String selectSql = "SELECT id FROM users WHERE username=? AND password=?";
					pst = con.prepareStatement(selectSql);
					pst.setString(1, name);
					pst.setString(2, pass);
					ResultSet resultSet = pst.executeQuery();

					if (resultSet.next()) {
						userIdToUpdate = resultSet.getInt("id");
					}

					String updateOUT = "UPDATE info SET timeOUT=? WHERE id=?";
					pst = con.prepareStatement(updateOUT);
					pst.setString(1, formattedDateTime);
					pst.setInt(2, userIdToUpdate);
					pst.executeUpdate();

				} catch (Exception a) {
					a.printStackTrace();
				}

			}
		});
		timeOUT.setBounds(43, 349, 134, 47);
		panel_1.add(timeOUT);

		JButton btnNewButton_2 = new JButton("RESET TIME ");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to reset the time, THIS WILL DELETE YOUR ATTENDANCE RECORD?",
						"Reset Time", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {

					int userIdToUpdate = 0;

					try {
						timeInLabel.setText("Time In: ");
						timeOutLabel.setText("Time Out: ");

						con = Connection1.getConnection();
						String selectSql = "SELECT id FROM users WHERE username=? AND password=?";
						pst = con.prepareStatement(selectSql);
						pst.setString(1, name);
						pst.setString(2, pass);
						ResultSet resultSet = pst.executeQuery();

						if (resultSet.next()) {
							userIdToUpdate = resultSet.getInt("id");
						}

						String resetTimeSql = "UPDATE info SET timeOUT='', timeIN='' WHERE id=?";
						pst = con.prepareStatement(resetTimeSql);
						pst.setInt(1, userIdToUpdate);
						pst.executeUpdate();

					} catch (Exception a) {
						a.printStackTrace();
					}

				}
			}
		});
		btnNewButton_2.setBounds(503, 448, 142, 38);
		panel_1.add(btnNewButton_2);

		JLabel lblNewLabel_3 = new JLabel(
				"Please be careful and make sure to save your attendance, as this action will record the exact time on your account.");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblNewLabel_3.setBounds(35, 503, 658, 47);
		panel_1.add(lblNewLabel_3);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("UPDATE NEW INFORMATION");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(281, 38, 389, 36);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("FIRST NAME :");
		lblNewLabel_2.setBounds(30, 131, 142, 22);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("LAST NAME :");
		lblNewLabel_2_1.setBounds(30, 206, 142, 22);
		panel.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("USER NAME :");
		lblNewLabel_2_2.setBounds(30, 278, 142, 22);
		panel.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("PASSWORD :");
		lblNewLabel_2_3.setBounds(30, 354, 142, 22);
		panel.add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_4 = new JLabel("COURSE :");
		lblNewLabel_2_4.setBounds(448, 119, 142, 22);
		panel.add(lblNewLabel_2_4);

		
		
		JComboBox selectCourse = new JComboBox();
		selectCourse.setModel(new DefaultComboBoxModel(new String[] {"SELECT COURSE", "BS ENGINEERING", "BS CRIMINOLOGY", "BS ACCOUNTANCY", "BS EDUCATION", "BS NURSING"}));
		selectCourse.setBounds(448, 144, 200, 21);
		panel.add(selectCourse);
		
		selectCourse.setSelectedItem(showCourse);

		fname = new JTextField(showName);
		fname.setForeground(new Color(0, 0, 255));
		fname.setEditable(false);
		fname.setBounds(91, 165, 286, 41);
		panel.add(fname);
		fname.setColumns(10);

		lname = new JTextField(showLast);
		lname.setForeground(new Color(0, 0, 255));
		lname.setEditable(false);
		lname.setColumns(10);
		lname.setBounds(91, 238, 286, 41);
		panel.add(lname);

		username = new JTextField(showUser);
		username.setColumns(10);
		username.setBounds(91, 303, 286, 41);
		panel.add(username);

		password = new JPasswordField(showPass);
		password.setBounds(91, 398, 286, 41);
		panel.add(password);

		JButton btnNewButton_1 = new JButton("SAVE CHANGES\r\n");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String firstname = fname.getText();
				String lastname = lname.getText();
				String user = username.getText();
				String pass = password.getText();
				String courseOne = selectCourse.getSelectedItem().toString();
				String options = "STUDENT";
				int userIdToUpdate = 0;

				if (firstname.equals("") || lastname.equals("") || user.equals("SELECT COURSE") || pass.equals("")
						|| courseOne.equals("SELECT COURSE")) {
					JOptionPane.showMessageDialog(rootPane, "Some Fields Are Missing", "Error", 2);
				} else {

					int confirmResult = JOptionPane.showConfirmDialog(rootPane,
							"Are you sure you want to change your credentials?", "Confirmation",
							JOptionPane.YES_NO_OPTION);

					if (confirmResult == JOptionPane.YES_OPTION) {

						try {
							con = Connection1.getConnection();
							String selectSql = "SELECT id FROM users WHERE username=? AND password=?";
							pst = con.prepareStatement(selectSql);
							pst.setString(1, name);
							pst.setString(2, pass);
							ResultSet resultSet = pst.executeQuery();

							if (resultSet.next()) {
								userIdToUpdate = resultSet.getInt("id");
							}

							String updateSql = "UPDATE users SET username=?, password=?, options=?, course=? WHERE id=?";
							pst = con.prepareStatement(updateSql);
							pst.setString(1, user);
							pst.setString(2, pass);
							pst.setString(3, options);
							pst.setString(4, courseOne);
							pst.setInt(5, userIdToUpdate);

							pst.executeUpdate();

							String insertSql2 = "UPDATE info SET fname =?, lname =? WHERE id=?";
							pst = con.prepareStatement(insertSql2);
							pst.setString(1, firstname);
							pst.setString(2, lastname);
							pst.setInt(3, userIdToUpdate);
							pst.executeUpdate();

							JOptionPane.showMessageDialog(rootPane, "Your credentials have been updated successfully.",
									"Success", JOptionPane.INFORMATION_MESSAGE);
							fname.setText("");
							lname.setText("");
							username.setText("");
							password.setText("");
							selectCourse.setSelectedItem("SELECT COURSE");

						} catch (Exception a) {
							a.printStackTrace();
						}

						attendance g = new attendance(user, courseOne, pass);
						g.setVisible(true);
						setVisible(false);

					}

				}

			}

		});
		btnNewButton_1.setBounds(549, 389, 165, 36);
		panel.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("CLEAR");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				password.setText("");
				selectCourse.setSelectedItem("SELECT COURSE");
			}
		});
		btnNewButton_1_1.setBounds(411, 389, 118, 36);
		panel.add(btnNewButton_1_1);

		JLabel lblNewLabel_4 = new JLabel("CONTACT ADMIN TO CHANGE YOUR FIRSTNAME AND LASTNAME");
		lblNewLabel_4.setForeground(new Color(47, 79, 79));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		lblNewLabel_4.setBounds(396, 354, 372, 31);
		panel.add(lblNewLabel_4);

		JButton btnNewButton_3 = new JButton("SET ATTENDANCE\r\n");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);

			}
		});
		btnNewButton_3.setBounds(10, 266, 213, 62);
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_3_1 = new JButton("EDIT CREDENTIALS");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.setSelectedIndex(1);
			}
		});

		btnNewButton_3_1.setBounds(10, 378, 213, 62);
		contentPane.add(btnNewButton_3_1);

		JButton btnNewButton = new JButton("LogOut");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to LogOut?", "logout",
						JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					Main x = new Main();
					x.setVisible(true);
					setVisible(false);
				}
			}
		});
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setBounds(10, 508, 85, 38);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Welcome to attendance");
		lblNewLabel_1.setBackground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1.setBounds(10, 54, 234, 31);
		contentPane.add(lblNewLabel_1);

	}
}
