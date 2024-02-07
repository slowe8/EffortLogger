package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

//Authored by Sean Lowe
public class EffortLog {

	private int log_id;
	private int user_id;
	private String projectName;
	private String lifeCycle;
	private String effortCategory;
	private String deliverable;
	private Date date;
	private Time startTime;
	private Time stopTime;
	
	private String dateString;
	private String startTimeString;
	private String stopTimeString;
	
	public EffortLog(int uid, String projectName, String lifeCycle, String effortCategory, String deliverable, Date date, Time startTime, Time stopTime) {
		user_id = uid;
		this.projectName = projectName;
		this.lifeCycle = lifeCycle;
		this.effortCategory = effortCategory;
		this.deliverable = deliverable;
		this.date = date;
		this.startTime = startTime;
		this.stopTime = stopTime;
		
		dateString = date.toString();
		startTimeString = startTime.toString();
		stopTimeString = stopTime.toString();
	}
	
	public EffortLog(int logid, int uid, String projectName, String lifeCycle, String effortCategory, String deliverable, Date date, Time startTime, Time stopTime) {
		log_id = logid;
		user_id = uid;
		this.projectName = projectName;
		this.lifeCycle = lifeCycle;
		this.effortCategory = effortCategory;
		this.deliverable = deliverable;
		this.date = date;
		this.startTime = startTime;
		this.stopTime = stopTime;
		
		dateString = date.toString();
		startTimeString = startTime.toString();
		stopTimeString = stopTime.toString();
	}
	
	public int getLog_id() {
		return log_id;
	}

	public String getLifeCycle() {
		return lifeCycle;
	}

	public String getEffortCategory() {
		return effortCategory;
	}

	public String getDeliverable() {
		return deliverable;
	}

	public String getDateString() {
		return dateString;
	}

	public String getStartTimeString() {
		return startTimeString;
	}

	public String getStopTimeString() {
		return stopTimeString;
	}

	public void sendLog() {
		// Establishes database conenction
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String addLog = "INSERT INTO effortlogs(user_id, projectName, lifeCycle, effortCategory, deliverable, date, startTime, stopTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			System.out.println("User ID: " + user_id);
			PreparedStatement statement = connectDB.prepareStatement(addLog);
			statement.setInt(1, user_id);
			statement.setString(2, projectName);
			statement.setString(3, lifeCycle);
			statement.setString(4, effortCategory);
			statement.setString(5, deliverable);
			statement.setDate(6, date);
			statement.setTime(7, startTime);
			statement.setTime(8, stopTime);
			statement.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ObservableList<EffortLog> getLog(int user_id, String projectName) {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String getLog = "SELECT * FROM effortlogs WHERE user_id = ? AND projectName = ?;";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(getLog);
			statement.setInt(1, user_id);
			statement.setString(2, projectName);
			ResultSet queryResult = statement.executeQuery();
			
			ObservableList<EffortLog> effortLogs = FXCollections.observableArrayList();
			
			while(queryResult.next()) {
				effortLogs.add(new EffortLog(queryResult.getInt(1), queryResult.getInt(2), queryResult.getString(3), queryResult.getString(4), queryResult.getString(5), queryResult.getString(6),
						queryResult.getDate(7), queryResult.getTime(8), queryResult.getTime(9)));
			}
			return effortLogs;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
}
