package AttendanceDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class adminView extends JFrame {
	private JPanel contentPane;
	private JPanel panel;
	private DefaultTableModel model;
	private JTable table;
	private int currentPage = 1;
	private int pageSize = 15;
	private JButton nextPageButton;
	private JButton prevPageButton;

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textField;
	private JButton deleteButton;
	private JLabel lblAdministrativeTool;

	public adminView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Create the panel to hold the table
		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(10, 10, 976, 658);
		contentPane.add(panel);

		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
		String currentDay = currentDayOfWeek.toString();
		currentDay = currentDay.toUpperCase();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
		String formattedDateTime = now.format(formatter);

		JLabel DATE = new JLabel(formattedDateTime);
		DATE.setFont(new Font("Tahoma", Font.BOLD, 18));
		DATE.setForeground(new Color(0, 0, 0));
		DATE.setBounds(715, 206, 206, 57);
		panel.add(DATE);

		JLabel dayTODAY = new JLabel(currentDay);
		dayTODAY.setFont(new Font("Tahoma", Font.BOLD, 18));
		dayTODAY.setForeground(new Color(0, 0, 0));
		dayTODAY.setBounds(798, 190, 157, 38);
		panel.add(dayTODAY);

		JLabel timeLabel = new JLabel();
		timeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		timeLabel.setForeground(new Color(0, 0, 205));
		timeLabel.setBounds(727, 236, 194, 57);
		panel.add(timeLabel);

		Timer timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				LocalTime currentTime = LocalTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US);
				String formattedTime = currentTime.format(formatter);
				timeLabel.setText("Time: " + formattedTime);
			}
		});
		timer.start();

		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Allow editing for specific columns, if needed
				return false;
			}
		};
		panel.setLayout(null);

		// Create the table
		table = new JTable(model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setFillsViewportHeight(true);
		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(80, 287, 798, 263);
		panel.add(scrollPane);

		prevPageButton = new JButton("<< Previous Page");
		prevPageButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		prevPageButton.setBounds(521, 560, 187, 30);
		panel.add(prevPageButton);

		nextPageButton = new JButton("Next Page >>");
		nextPageButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		nextPageButton.setBounds(708, 560, 170, 30);
		panel.add(nextPageButton);

		lblNewLabel = new JLabel("WELCOME TO");
		lblNewLabel.setForeground(new Color(0, 0, 139));
		lblNewLabel.setFont(new Font("Perpetua Titling MT", Font.BOLD, 42));
		lblNewLabel.setBounds(327, 10, 436, 148);
		panel.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("SEARCH STUDENT :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(21, 233, 218, 29);
		panel.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(222, 233, 237, 30);
		panel.add(textField);
		textField.setColumns(10);

		JButton search = new JButton(">");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String searchText = textField.getText().trim().toLowerCase();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				model.setRowCount(0);
				
				int offset = (currentPage - 1) * pageSize ;
			
				

				  try {
			            con = Connection1.getConnection();
			            String selectSql = "SELECT info.id, fname, lname, timeIN, timeOUT, course FROM info " +
			                               "INNER JOIN users ON info.id = users.id " +
			                               "WHERE info.id LIKE ? OR " +
			                               "fname LIKE ? OR " +
			                               "lname LIKE ? OR " +
			                               "timeIN LIKE ? OR " +
			                               "timeOUT LIKE ? OR " +
			                               "course LIKE ? LIMIT ?,?";
			            pst = con.prepareStatement(selectSql);
			            String wildcardSearch = "%" + searchText + "%";
			            for (int i = 1; i <= 6; i++) {
			                pst.setString(i, wildcardSearch);
			            }
			            pst.setInt(7, offset);
			            pst.setInt(8, pageSize);
			            ResultSet resultSet = pst.executeQuery();

			            

			            while (resultSet.next()) {
			                String id = resultSet.getString("id");
			                String firstname = resultSet.getString("fname");
			                String lastname = resultSet.getString("lname");
			                String intime = resultSet.getString("timeIN");
			                String outtime = resultSet.getString("timeOUT");
			                String course = resultSet.getString("course");
			                String status = "";

			                if (!intime.isEmpty() && !outtime.isEmpty()) {
			                    status = "COMPLETED";
			                } else {
			                    status = "PENDING";
			                }

			                model.addRow(new Object[]{id, firstname, lastname, course, intime, outtime, status});
			            }
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			    }
			});
		

		search.setForeground(new Color(0, 0, 255));
		search.setBounds(469, 233, 46, 29);
		panel.add(search);

		
		
		
		JButton btnI = new JButton("EDIT STUDENT");
		btnI.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {

					String id = (String) table.getValueAt(selectedRow, 0);
					String name = (String) table.getValueAt(selectedRow, 1);
					int confirm = JOptionPane.showConfirmDialog(contentPane,
							"You are going to edit this Student Credentials?",
							"Confirm edit", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						try {
				
				
				adminEDIT edit = new adminEDIT(id);
				edit.setVisible(true);
				
				
				
						}catch (Exception a) {
							a.printStackTrace();
						}
						
					}
				}
			}
		});

					
				
		btnI.setBackground(new Color(175, 238, 238));
		btnI.setForeground(new Color(0, 0, 0));
		btnI.setBounds(80, 561, 157, 30);
		panel.add(btnI);

		deleteButton = new JButton("DELETE");
		deleteButton.setBackground(new Color(205, 92, 92));
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setBounds(247, 560, 100, 30);
		panel.add(deleteButton);
		
		JButton btnNewButton = new JButton("LOGOUT");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setForeground(new Color(0, 0, 0));
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
			
		btnNewButton.setBounds(771, 600, 107, 30);
		panel.add(btnNewButton);
		
		lblAdministrativeTool = new JLabel("ADMINISTRATIVE TOOL");
		lblAdministrativeTool.setForeground(new Color(0, 0, 139));
		lblAdministrativeTool.setFont(new Font("Perpetua Titling MT", Font.BOLD, 42));
		lblAdministrativeTool.setBounds(222, 96, 620, 87);
		panel.add(lblAdministrativeTool);

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {

					String id = (String) table.getValueAt(selectedRow, 0);

					int confirm = JOptionPane.showConfirmDialog(contentPane,
							"WARNING: This action will permanently delete the selected Student, You want to proceed?",
							"Confirm Delete", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						try {

							con = Connection1.getConnection();
							String deleteSql = "DELETE FROM info WHERE id=?";
							pst = con.prepareStatement(deleteSql);
							pst.setString(1, id);
							pst.executeUpdate();
							
							String deleteSql1 = "DELETE FROM users WHERE id=?";
							pst = con.prepareStatement(deleteSql1);
							pst.setString(1, id);
							pst.executeUpdate();

							model.removeRow(selectedRow);
							refreshTable();

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		nextPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPage++;
				refreshTable();
			}
		});

		prevPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentPage > 1) {
					currentPage--;
					refreshTable();
				}
			}
		});

		model.addColumn("ID");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Course");
		model.addColumn("Time In");
		model.addColumn("Time Out");
		model.addColumn("Status");

		refreshTable();
	}

	private void refreshTable() {
		model.setRowCount(0);
		
		int offset = (currentPage - 1) * pageSize ;
		if (offset == 0) {
		    offset = 1;
		}

		try {
			con = Connection1.getConnection();
			String selectSql = "SELECT id, fname, lname, timeIN, timeOUT FROM info " + "LIMIT ?, ?";
			pst = con.prepareStatement(selectSql);
			pst.setInt(1, offset);
			pst.setInt(2, pageSize);
			ResultSet resultSet = pst.executeQuery();

			String selectSql1 = "SELECT course FROM users " + "LIMIT ?, ?";
			pst = con.prepareStatement(selectSql1);
			pst.setInt(1, offset);
			pst.setInt(2, pageSize);
			ResultSet resultSet1 = pst.executeQuery();

			while (resultSet.next() && resultSet1.next()) {
				String id = resultSet.getString("id");
				String firstname = resultSet.getString("fname");
				String lastname = resultSet.getString("lname");
				String course = resultSet1.getString("course");
				String intime = resultSet.getString("timeIN");
				String outtime = resultSet.getString("timeOUT");
				String status = "";

				if (!intime.isEmpty() && !outtime.isEmpty()) {
					status = "COMPLETED";
				} else {
					status = "PENDING";
				}
				table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(JTable F, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected,
								hasFocus, row, column);
						String status = (String) table.getValueAt(row, 6);
						if ("COMPLETED".equals(status)) {
							cellComponent.setBackground(Color.GREEN);
						} else {
							cellComponent.setBackground(Color.RED);
						}
						return cellComponent;
					}
				});

				model.addRow(new Object[] { id, firstname, lastname, course, intime, outtime, status });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				adminView frame = new adminView();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
