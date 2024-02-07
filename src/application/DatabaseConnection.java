package application;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * This class describes an object for an active database connection
 * Authored by Sean Lowe
 */
public class DatabaseConnection {
	// Connection object
	public Connection databaseLink;
	
	// Establishes a connection to the database
	public Connection getConnection() {
		// Information about the server to be connected to 
		String databaseName = "efdb";
		String databaseUser = "remoteuser"; // When testing, replace with testing user credentials
		String databasePassword = "REmote12357!";
		String url = "jdbc:mysql://localhost:3306/" + databaseName;
		// jdbc:mysql://LAPTOP-K74RHHAU:3306/?user=remoteuser
		// remoteuser@LAPTOP-K74RHHAU:3306
		// jdbc:mysql://LAPTOP-K74RHHAU:3306/?user=remoteuser
		
		// Tries to establish a connection and passes back Connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return databaseLink;
	}
}
