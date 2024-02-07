package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

//Authored by Sean Lowe

public class EffortLoggerConsoleController extends GUIController implements Initializable {
	
	// EffortLoggerConsole FXML
	@FXML
	private Button accountButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Tab effortLoggerTab;
	@FXML
	private Rectangle clockBackground;
	@FXML
	private ComboBox<String> projectComboBox;
	@FXML
	private TextField lifeCycleField;
	@FXML
	private TextField effortCategoryField;
	@FXML
	private TextField deliverableField;
	@FXML
	private Button startActivityButton;
	@FXML
	private Label clockLabel;
	
	private Date currentDate;
	private Time logStartDate;
	private Time logStopDate;
	
	private EffortLog newLog;
	
	private boolean started = false;
	
	// Projects FXML
	@FXML
	private Tab projectsTab;
	@FXML
	private ComboBox<String> viewProjectsCombo;
	@FXML
	private Button viewProjectsButton;
	@FXML
	private Button createProjectButton;
	@FXML
	private TextField projectNameField;
	@FXML
	private Label projectsMessageLabel;
	
	// Defect Logger
	@FXML
	private Tab defectLoggerTab;
	@FXML
	private ComboBox<String> defectProjectComboBox;
	@FXML
	private TextField defectField;
	@FXML
	private ComboBox<String> statusComboBox;
	@FXML
	private TextField injectedField;
	@FXML
	private TextField removedField;
	@FXML
	private TextField categoryField;
	@FXML
	private TextField fixField;
	@FXML
	private TextArea defectDetailText;
	@FXML
	private Button updateDefectButton;
	@FXML
	private Button createDefectButton;
	
	private DefectLog newDefect;
	
	//Edit Logs
	@FXML
	private Tab logEditorTab;
	@FXML
	private ComboBox<String> editProjectComboBox;
	@FXML
	private TextField idText;
	@FXML
	private TextField lifecycleText;
	@FXML
	private TextField categoryText;
	@FXML
	private TextField deliverableText;

	@FXML
	private Button saveEditButton;
	
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		//projectComboBox.getItems().setAll("Project1", "Project2");
		
		//Hoon-Defect Log Initialization
		statusComboBox.getItems().setAll("Open", "Closed");
	}
	
	public void startActivityOnAction(ActionEvent e) {
		java.util.Date date = new java.util.Date();
		currentDate = new Date(date.getTime());
		logStartDate = new Time(date.getTime());
		clockBackground.setFill(Paint.valueOf("Green"));
		clockLabel.setText("Clock is Running");
		started = true;
	}
	
	public void stopActivityOnAction(ActionEvent e) {
		if(started) {
			java.util.Date date = new java.util.Date();
			logStopDate = new Time(date.getTime());
			clockBackground.setFill(Paint.valueOf("Red"));
			clockLabel.setText("Clock is Stopped");
			newLog = new EffortLog(currentUser.getUserID(), projectComboBox.getValue(), lifeCycleField.getText(), effortCategoryField.getText(), deliverableField.getText(), currentDate, logStartDate, logStopDate);
			newLog.sendLog();
			started = false;
		}
	}
	
	public void logoutButtonOnAction(ActionEvent e) {
		changeStage(EffortLoggerStage.LOGIN, e);
	}
	
	public void effortLoggerTabOnSelectionChanged(Event e) {
		if(effortLoggerTab.isSelected()) {
			// Refresh combo boxes
			// -- For each box
			//  -- Make a connection
			//	-- Request names of database stuff
			//	-- fill it into boxes
			currentUser.getUserProjects();
			projectComboBox.getItems().setAll(currentUser.getProjectCollection());
		} else if(projectsTab.isSelected()) {
			try {
				previousScene = ((Tab) e.getSource()).getTabPane().getScene();
				currentUser.getUserProjects();
				viewProjectsCombo.getItems().setAll(currentUser.getProjectCollection());
				changeStage(EffortLoggerStage.AUTHENTICATION, e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if(logEditorTab.isSelected()) {
			// Refresh combo boxes
			// -- For each box
			//  -- Make a connection
			//	-- Request names of database stuff
			//	-- fill it into boxes
			
			try {
				previousScene = ((Tab) e.getSource()).getTabPane().getScene();
				currentUser.getUserProjects();
				editProjectComboBox.getItems().setAll(currentUser.getProjectCollection());
				changeStage(EffortLoggerStage.AUTHENTICATION, e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if(defectLoggerTab.isSelected()) {
			currentUser.getUserProjects();
			defectProjectComboBox.getItems().setAll(currentUser.getProjectCollection());
		}
	}
	
	public void viewProjectsButtonOnAction(ActionEvent e) {
		selectedProject = viewProjectsCombo.getValue();
		previousScene = ((Node) e.getSource()).getScene();
		changeStage(EffortLoggerStage.VIEW_PROJECT, e);
	}
	
	public void createProjectButtonOnAction(ActionEvent e) {
		if(currentUser.createProject(projectNameField.getText())) {
			projectsMessageLabel.setText("Project created!");
			currentUser.getUserProjects();
			viewProjectsCombo.getItems().setAll(currentUser.getProjectCollection());
		} else {
			projectsMessageLabel.setText("Please enter a unique project name.");
		}
	}

	//Hoon-Creates new defect
	public void createNewDefectLog(ActionEvent e) {
		newDefect = new DefectLog(currentUser.getUserID(), defectProjectComboBox.getValue(), defectField.getText(), defectDetailText.getText(), injectedField.getText(), removedField.getText(), categoryField.getText(), statusComboBox.getValue(), fixField.getText());
		DefectLog.sendDefect(newDefect, e);
	}
	
	public void saveEditLog(ActionEvent e)
	{
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
		
		String project = editProjectComboBox.getValue();
		int id = Integer.parseInt(idText.getText());
		
		String lifeCycle = lifecycleText.getText();
		String category = categoryText.getText();
		String deliverable = deliverableText.getText();
		
		String getLog = "UPDATE effortlogs SET lifeCycle = ?, effortCategory = ?, deliverable = ? WHERE log_id = ? AND projectName = ?;";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(getLog);
			statement.setString(1, lifeCycle);
			statement.setString(2, category);
			statement.setString(3, deliverable);
			statement.setInt(4, id);
			statement.setString(5, project);
			statement.execute();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
