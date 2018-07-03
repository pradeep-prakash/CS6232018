package MySQLProject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//Pradeep Prakash & Jonathan Koller
//Group 1 – The product p1 is deleted from Product and Stock
public class Group1 {
	

	public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {

		// Load the MySQL driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Connect to the database
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "admin2018");

		// For atomicity
		conn.setAutoCommit(false);
		
		// For isolation 
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 
		
	
			Statement stmt = null;
			try {
				// create statement object
				stmt = conn.createStatement();
				//Drop tables if they exist
				stmt.executeUpdate("DROP TABLE IF EXISTS Stock CASCADE");
				stmt.executeUpdate("DROP TABLE IF EXISTS Product CASCADE");
				stmt.executeUpdate("DROP TABLE IF EXISTS Depot CASCADE");
				
				//Create table Product (prod, pname, price), primary key(prod), check constraint price>0
				stmt.executeUpdate("Create table IF NOT EXISTS Product("
						+ "prod CHAR(15),"
						+ "pname VARCHAR(30),"
						+ "price DECIMAL,"
						+ "PRIMARY KEY (prod),"
						+ "CHECK (price > 0)"
						+ ")");
				
				//Create table Depot (dep, address, volume), primary key (dep), check constraint volume>0
				stmt.executeUpdate("Create table IF NOT EXISTS Depot("
						+ "dep CHAR(15),"
						+ "addr VARCHAR(30),"
						+ "volume INTEGER,"
						+ "PRIMARY KEY (dep),"
						+ "CHECK (volume > 0)"
						+ ")");
				
				//Create table Stock (prod, dep, quantity)
				//Composite Primary key (prod, dep)
				//'prod' in stock is a foreign key referring the primary key 'prod' in product
				//'dep' in stock is a foreign key referring the primary key 'dep' in depot
				stmt.executeUpdate("Create table IF NOT EXISTS Stock("
						+ "prod CHAR(15),"
						+ "dep CHAR(15),"
						+ "quantity INTEGER,"
						+ "PRIMARY KEY (prod, dep),"
						+ "FOREIGN KEY (prod) REFERENCES Product (prod)," 
						+ "FOREIGN KEY (dep) REFERENCES Depot (dep)"
						+ ")");			
				
				//Insert values in Product table
				stmt.executeUpdate("INSERT INTO Product (prod, pname, price) Values" 
						+ "('p1', 'tape', 2.5)," 
						+ "('p2', 'tv', 250), "
						+ "('p3', 'vcr', 80);");
				
				//Insert values into Depot table
				stmt.executeUpdate("INSERT INTO Depot (dep, addr, volume) Values" 
						+ "('d1', 'New York', 9000)," 
						+ "('d2', 'Syracuse', 6000), "
						+ "('d4', 'New York', 2000);");
				
				//Insert values into Stock Table
				stmt.executeUpdate("INSERT INTO Stock (prod, dep, quantity) Values" 
						+ "('p1', 'd1', 1000),"
						+ "('p1', 'd2', -100)," 
						+ "('p1', 'd4', 1200)," 
						+ "('p3', 'd1', 3000)," 
						+ "('p3', 'd4', 2000)," 
						+ "('p2', 'd4', 1500)," 
						+ "('p2', 'd1', -400)," 
						+ "('p2', 'd2', 2000);");
				
		} catch (SQLException e) {
			System.out.println("catch Exception");
			System.out.println(e);
			// For atomicity
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		} // main
		conn.commit();
		stmt.close();
		conn.close();
	}
}
