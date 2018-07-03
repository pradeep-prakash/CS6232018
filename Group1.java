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
				System.out.println("Group 1 – The product p1 is deleted from Product and Stock\n"
						+ "Pradeep Prakash & Jonathan Koller\n" );
				
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
						+ "FOREIGN KEY (prod) REFERENCES Product (prod) ON DELETE CASCADE," 
						+ "FOREIGN KEY (dep) REFERENCES Depot (dep) ON DELETE CASCADE"
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
				
				//Output of Tables before operation
				
				System.out.println("Tables before performing delete operation\n");
				
				ResultSet producttable = stmt.executeQuery("select * from Product");
				System.out.println("Product Table:");
				System.out.println("prod " + "pname " + "price");
				while(producttable.next()) {
					System.out.println( producttable.getString("prod")
							+ "\t " + producttable.getString("pname")
							+ "\t " + producttable.getInt("price"));
				} 
				
				ResultSet depottable = stmt.executeQuery("select * from Depot");
				System.out.println("\nDepot Table:");
				System.out.println("dep " + "address " + "volume");
				while(depottable.next()) {
					System.out.println( depottable.getString("dep")
							+ "\t " + depottable.getString("addr")
							+ "\t " + depottable.getInt("volume"));
				} 
				
				ResultSet stocktable = stmt.executeQuery("select * from Stock");
				System.out.println("\nStock Table");
				System.out.println("prod " + "dep " + "quantity");
				while(stocktable.next()) {
					System.out.println(stocktable.getString("prod") 
							+ "\t " + stocktable.getString("dep") 
							+ "\t " + stocktable.getInt("quantity"));
				} 
				
				
				
				//Tables output after delete operation
				System.out.println("\nTables after performing delete operation");
				System.out.println("p1 is deleted from Product & Stock tables\n");
				
				stmt.executeUpdate("DELETE FROM Product WHERE prod = 'p1'");
				stmt.executeUpdate("DELETE FROM Stock WHERE prod = 'p1'");
				
				ResultSet productresult = stmt.executeQuery("select * from Product");
				System.out.println("\nProduct Table:");
				System.out.println("prod " + "pname " + "price");
				while(productresult.next()) {
					System.out.println( productresult.getString("prod") 
							+ "\t " + productresult.getString("pname") 
							+ "\t " + productresult.getInt("price"));
				} 
				
				
				
				ResultSet depotresult = stmt.executeQuery("select * from Depot");
				System.out.println("\nDepot Table:");
				System.out.println("dep " + "address " + "volume");
				while(depotresult.next()) {
					System.out.println( depotresult.getString("dep")
							+ "\t " + depotresult.getString("addr")
							+ "\t " + depotresult.getInt("volume"));
				} 
				
				ResultSet stockresult = stmt.executeQuery("select * from Stock");
				System.out.println("\nStock Table");
				System.out.println("prod " + "dep " + "quantity");
				while(stockresult.next()) {
					System.out.println(stockresult.getString("prod") 
							+ "\t " + stockresult.getString("dep") 
							+ "\t " + stockresult.getInt("quantity"));
				} 
				
				
				
				
		} catch (SQLException e) {
			System.out.println("catch Exception");
			System.out.println(e);
			// For atomicity
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		} // main
		System.out.println("Commiting data here....");
		conn.commit();

		// list available records
		String sql = "SELECT * FROM Product";
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("Product listed for reference....");
		printRs(rs);
		
		sql = "SELECT * FROM Stock";
		rs = stmt.executeQuery(sql);
		System.out.println("Stock listed for reference....");
		printRs(rs);
		
		sql = "SELECT * FROM Depot";
		rs = stmt.executeQuery(sql);
		System.out.println("Depot listed for reference....");
		printRs(rs);
		

		// clean-up / conclude environment 
		rs.close();
		stmt.close();
		conn.close();
	}
}
