package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/*
 * This class describes a DefectLog object and its attributes and methods
 * Authored by Taehoon Kwon
 */
public class DefectLog {
	// Attributes for a DefectLog
	private int defect_id;
	private int user_id;
	private String projectName;
	private String defect;
	private String details;
	private String injected;
	private String removed;
	private String category;
	private String status;
	private String fix;
	
	// Constructor
	public DefectLog(int uid, String projectName, String defect, String details, String injected, String removed, String category, String status, String fix) {
		user_id = uid;
		this.projectName = projectName;
		this.defect = defect;
		this.details = details;
		this.injected = injected;
		this.removed = removed;
		this.category = category;
		this.status = status;
		this.fix = fix;
	}
	
	public DefectLog(int defectid, int uid, String projectName, String defect, String details, String injected, String removed, String category, String status, String fix) {
		defect_id = defectid;
		user_id = uid;
		this.projectName = projectName;
		this.defect = defect;
		this.details = details;
		this.injected = injected;
		this.removed = removed;
		this.category = category;
		this.status = status;
		this.fix = fix;
	}
	
	public int getDefect_id() {
		return defect_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getDefect() {
		return defect;
	}

	public String getDetails() {
		return details;
	}

	public String getInjected() {
		return injected;
	}

	public String getRemoved() {
		return removed;
	}

	public String getCategory() {
		return category;
	}

	public String getStatus() {
		return status;
	}

	public String getFix() {
		return fix;
	}

	public static void sendDefect(DefectLog defect, ActionEvent e) {
		// Establishes database connection
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String addDefect = "INSERT INTO defectlogs(user_id, projectName, defect, details, injected, removed, category, status, fix) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(addDefect);
			statement.setInt(1, defect.user_id);
			statement.setString(2, defect.projectName);
			statement.setString(3, defect.defect);
			statement.setString(4, defect.details);
			statement.setString(5, defect.injected);
			statement.setString(6, defect.removed);
			statement.setString(7, defect.category);
			statement.setString(8, defect.status);
			statement.setString(9, defect.fix);
			statement.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ObservableList<DefectLog> getLog(int user_id, String projectName) {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String getLog = "SELECT * FROM defectlogs WHERE user_id = ? AND projectName = ?;";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(getLog);
			statement.setInt(1, user_id);
			statement.setString(2, projectName);
			ResultSet queryResult = statement.executeQuery();
			
			ObservableList<DefectLog> defectLogs = FXCollections.observableArrayList();
			
			while(queryResult.next()) {
				defectLogs.add(new DefectLog(queryResult.getInt(1), queryResult.getInt(2), queryResult.getString(3), queryResult.getString(4), queryResult.getString(5), queryResult.getString(6),
						queryResult.getString(7), queryResult.getString(8), queryResult.getString(9), queryResult.getString(10)));
			}
			return defectLogs;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
