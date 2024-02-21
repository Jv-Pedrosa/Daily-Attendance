
package AttendanceDatabase;

import java.sql.*;

public class Connection1 {
	static Connection con;
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/jvproj", "root", null);
		}catch(Exception a) {
			a.printStackTrace();
		}
		return con;
	}
}