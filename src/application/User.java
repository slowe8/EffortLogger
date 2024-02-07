package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import javafx.event.ActionEvent;

public class User {

	private int user_id;
	private String username;
	private String password;
	private String firstname;
	private String middlename;
	private String lastname;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String phone;
	
	private List<String> projectList = new ArrayList<String>();
	
	public User() {
		user_id = 0;
		username = "";
		password = "";
		firstname = "";
		middlename = "";
		lastname = "";
		address = "";
		city = "";
		state= "";
		zip = "";
		email = "";
		phone = "";
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password, 
			String firstname, String middlename, String lastname) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
	}
	
	public void setPersonal(String address, String city, String state, String zip,
			String email, String phone) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.email = email;
		this.phone = phone;
	}
	
	public int getUserID() {
		if(user_id == 0) {
			int uid;
			DatabaseConnection connectNow = new DatabaseConnection();
			Connection connectDB = connectNow.getConnection();
			
			String getUserID = "SELECT user_id FROM users WHERE username = ?";
			try {
				PreparedStatement statement = connectDB.prepareStatement(getUserID);
				statement.setString(1, username);
				ResultSet queryResult = statement.executeQuery();
				
				if(queryResult.next()) {
					uid = queryResult.getInt(1);
				} else {
					uid = 0;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				uid = 0;
			}
			return uid;
		} else {
			return user_id;
		}
	}
	
	public void setUserID() {
		user_id = getUserID();
	}
	
	// Returns one on success and zero on fail
	public boolean sendRegister(ActionEvent e) {
		// Establishes database conenction
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		// Formatted database insertions
		String registerUser = "INSERT INTO users(username, password, IV) VALUES (?, ?, ?);";
		String registerUserData = "INSERT INTO user_data(user_id, first_name, middle_name, last_name, address_line, city, state, zip, email, phone, IV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			SecretKey key = Encryptor.getKeyFromPassword(password, Encryptor.getNextSalt());
			SecretKeyStore.store(username, key);
			byte[] iv = Encryptor.generateIv();
			IvParameterSpec IV = new IvParameterSpec(iv);
			String encryptedPassword = Encryptor.encrypt(password, key, IV);
			
			// First tries to create a unique user
			PreparedStatement statementOne = connectDB.prepareStatement(registerUser);
			statementOne.setString(1, username);
			statementOne.setString(2, encryptedPassword);
			statementOne.setBytes(3, iv);
			statementOne.execute();
			setUserID();
			
			iv = Encryptor.generateIv();
			IV = new IvParameterSpec(iv);
			String encryptedFirstName = Encryptor.encrypt(firstname, key, IV);
			String encryptedMiddleName = Encryptor.encrypt(middlename, key, IV);
			String encryptedLastName = Encryptor.encrypt(lastname, key, IV);
			String encryptedAddress = Encryptor.encrypt(address, key, IV);
			String encryptedCity = Encryptor.encrypt(city, key, IV);
			String encryptedState = Encryptor.encrypt(state, key, IV);
			String encryptedZip = Encryptor.encrypt(zip, key, IV);
			String encryptedEmail = Encryptor.encrypt(email, key, IV);
			String encryptedPhone = Encryptor.encrypt(phone, key, IV);
			
			// If a user is created correctly, then their data is stored attached to their user_id
			if(user_id != 0) {
				PreparedStatement statementTwo = connectDB.prepareStatement(registerUserData);
				statementTwo.setInt(1, user_id);
				statementTwo.setString(2, encryptedFirstName);
				statementTwo.setString(3, encryptedMiddleName);
				statementTwo.setString(4, encryptedLastName);
				statementTwo.setString(5, encryptedAddress);
				statementTwo.setString(6, encryptedCity);
				statementTwo.setString(7, encryptedState);
				statementTwo.setString(8, encryptedZip);
				statementTwo.setString(9, encryptedPhone);
				statementTwo.setString(10, encryptedEmail);
				statementTwo.setBytes(11, iv);
				statementTwo.execute();
				//changeStage(EffortLoggerStage.LOGIN, e);
				return true;
			} else {
				//registerMessageLabel.setText("Not Registered!");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public boolean validateLogin(ActionEvent e, String password) {
		// Establish a database connection
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
	
		// Check for invalid input
		
		// Format for database query
		String verifyLogin = "SELECT password FROM users WHERE username = ?;";
		
		SecretKey secretKey = SecretKeyStore.load(username);
		byte[] iv = new byte[16];
		iv = getIV();
		IvParameterSpec IV = new IvParameterSpec(iv);
		
		try {
			// Create a prepared statement for the database to prevent SQL injection
			PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
			statement.setString(1, username);
			ResultSet queryResult = statement.executeQuery();
			
			// If a result is returned, then the user has entered correct credentials 
			queryResult.next();
			if(Encryptor.encrypt(password, secretKey, IV).equals(queryResult.getString(1))) {
				//loginMessageLabel.setText("Logged In!");
				user_id = getUserID();
				return true;
				//changeStage(EffortLoggerStage.EFFORT_LOGGER, e);
			} else {
				//loginMessageLabel.setText("Invalid login.");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean loadUser() {
		// Establish a database connection
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		
		
		try {
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public byte[] getIV() {
		// Establish a database connection
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		// Check for invalid input
		
		// Format for database query
		String getIV = "SELECT IV FROM users WHERE username = ?";
		
		try {
			// Create a prepared statement for the database to prevent SQL injection
			PreparedStatement statement = connectDB.prepareStatement(getIV);
			statement.setString(1, username);
			ResultSet queryResult = statement.executeQuery();
			
			// If a result is returned, then the user has entered correct credentials 
			while(queryResult.next()) {
				return queryResult.getBytes(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		byte[] empty = {};
		return empty;
	}
	
	public boolean getUserProjects() {
		// Establish a database connection
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		// Check for invalid input
		
		// Format for database query
		String getProjects = "SELECT project_name FROM projects;";
		projectList.clear();
		try {
			PreparedStatement statement = connectDB.prepareStatement(getProjects);
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next()) {
				projectList.add(queryResult.getString(1));
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}	
	
	public List<String> getProjectCollection() {
		return projectList;
	}
		
	public boolean authenticatePassword(String password) {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String getPassword = "SELECT password FROM users WHERE user_id = ?";
		
		SecretKey secretKey = SecretKeyStore.load(username);
		byte[] iv = new byte[16];
		iv = getIV();
		IvParameterSpec IV = new IvParameterSpec(iv);
		
		try {
			// Create a prepared statement for the database to prevent SQL injection
			PreparedStatement statement = connectDB.prepareStatement(getPassword);
			statement.setInt(1, user_id);
			ResultSet queryResult = statement.executeQuery();
			
			// If a result is returned, then the user has entered correct credentials 
			queryResult.next();
			if(Encryptor.encrypt(password, secretKey, IV).equals(queryResult.getString(1))) {
				//loginMessageLabel.setText("Logged In!");
				return true;
				//changeStage(EffortLoggerStage.EFFORT_LOGGER, e);
			} else {
				//loginMessageLabel.setText("Invalid login.");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean createProject(String projectName) {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String createProject = "INSERT INTO projects(project_name) VALUES (?);";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(createProject);
			statement.setString(1, projectName);
			statement.execute();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
