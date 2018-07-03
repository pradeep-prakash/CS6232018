package MySQLProject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Group1 {
	

	public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {

		// Load/register the MySQL driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Connect to the database
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "admin2018");

		// For atomicity 
		conn.setAutoCommit(false);
		
		// For isolation 
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 
		
	
			Statement stmt1 = null;
			try {
			stmt1 = conn.createStatement();
				stmt1.executeUpdate("Create table IF NOT EXISTS student1("
						+ "id integer,"
						+ "name VARCHAR(10),"
						
						+ "PRIMARY KEY (id)"
						+")");
	
				// Either the 2 following inserts are executed, or none of them are. This is atomicity.
				stmt1.executeUpdate("insert into student1 values (1, 'stud1')");
				stmt1.executeUpdate("insert into student1 values (2, 'stud2')");
			
		} catch (SQLException e) {
			System.out.println("catch Exception");
			System.out.println(e);
			// For atomicity
			conn.rollback();
			stmt1.close();
			conn.close();
			return;
		} // main
		System.out.println("Commiting data here....");
		conn.commit();

		// list available records
		String sql = "SELECT id, first, last, age FROM Employees";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("List result set for reference....");
		printRs(rs);
		
		// clean-up / conclude environment 
		rs.close();
		stmt1.close();
		conn.close();
	}
}
