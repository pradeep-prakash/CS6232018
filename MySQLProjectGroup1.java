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
		String sql = "SELECT id, first, last, age FROM Product";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("Product listed for reference....");
		printRs(rs);
		
		sql = "SELECT id, first, last, age FROM Stock";
		rs = stmt.executeQuery(sql);
		System.out.println("Stock listed for reference....");
		printRs(rs);
		
		sql = "SELECT id, first, last, age FROM Depot";
		rs = stmt.executeQuery(sql);
		System.out.println("Depot listed for reference....");
		printRs(rs);
		

		// clean-up / conclude environment 
		rs.close();
		stmt1.close();
		conn.close();
	}
}

ResultSet rs = stmt.executeQuery ("select *
from student");
• This sends the query to the database and
returns the result of the query as a ResultSet.
• The method next moves the cursor to the
next row and makes that row the one on
which we can operate.
• We use getXXX (getFloat, getString,
getInt) to retrieve the value in each column.



