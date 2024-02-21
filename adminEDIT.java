package AttendanceDatabase;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class adminEDIT extends JFrame {

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
					adminEDIT frame = new adminEDIT("");
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
	public adminEDIT(String id) {
		setType(Type.POPUP);
		setAlwaysOnTop(true);

		int userIdToUpdate = 0;
		String showUser = "";
		String showPass = "";
		String showName = "";
		String showLast = "";
		String showCourse = "";

		try {
			con = Connection1.getConnection();
			String selectSql = "SELECT * FROM users WHERE id=? ";
			pst = con.prepareStatement(selectSql);
			pst.setString(1, id);

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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 612, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("EDIT STUDENT PROFILE");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 25));
		lblNewLabel.setBounds(142, 100, 426, 88);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("FIRST NAME :\r\n");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1.setForeground(new Color(0, 0, 128));
		lblNewLabel_1.setBounds(39, 226, 115, 32);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("LAST NAME :\r\n");
		lblNewLabel_1_1.setForeground(new Color(0, 0, 128));
		lblNewLabel_1_1.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(39, 283, 115, 32);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("USERNAME :\r\n");
		lblNewLabel_1_2.setForeground(new Color(0, 0, 128));
		lblNewLabel_1_2.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(39, 342, 115, 32);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("PASSWORD :\r\n");
		lblNewLabel_1_3.setForeground(new Color(0, 0, 128));
		lblNewLabel_1_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_1_3.setBounds(39, 406, 115, 32);
		contentPane.add(lblNewLabel_1_3);

		JComboBox selectCourse = new JComboBox();
		selectCourse.setForeground(new Color(0, 0, 128));
		selectCourse.setModel(new DefaultComboBoxModel(new String[] { "SELECT COURSE :", "BS ENGINEERING",
				"BS CRIMINOLOGY", "BS ACCOUNTANCY", "BSEDUCATION", "BS NURSING" }));
		selectCourse.setBounds(205, 182, 148, 21);
		contentPane.add(selectCourse);

		selectCourse.setSelectedItem(showCourse);

		fname = new JTextField(showName);
		fname.setBounds(164, 225, 243, 39);
		contentPane.add(fname);
		fname.setColumns(10);

		lname = new JTextField(showLast);
		lname.setColumns(10);
		lname.setBounds(164, 283, 243, 39);
		contentPane.add(lname);

		username = new JTextField(showUser);
		username.setColumns(10);
		username.setBounds(164, 342, 243, 39);
		contentPane.add(username);

		password = new JPasswordField(showPass);
		password.setBounds(164, 409, 243, 39);
		contentPane.add(password);

		JButton btnNewButton_1 = new JButton("CLEAR FORM");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				password.setText("");
				fname.setText("");
				lname.setText("");
				selectCourse.setSelectedItem("SELECT COURSE :");

			}
		});
		btnNewButton_1.setBounds(124, 494, 132, 32);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("SAVE CHANGES");
		btnNewButton_1_1.addActionListener(new ActionListener() {
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
							String selectSql = "SELECT * FROM users WHERE id=? ";
							pst = con.prepareStatement(selectSql);
							pst.setString(1, id);
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
							selectCourse.setSelectedItem("SELECT COURSE ");

						} catch (Exception a) {
							a.printStackTrace();
						}

					}
				}

			}

		});
		btnNewButton_1_1.setBounds(277, 494, 132, 32);
		contentPane.add(btnNewButton_1_1);
	}
}
