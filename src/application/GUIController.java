package application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

// Authored by Sean Lowe
public class GUIController {
	
	protected static Scene previousScene;

	static enum EffortLoggerStage {
		LOGIN,
		REGISTER_ONE,
		REGISTER_TWO,
		EFFORT_LOGGER,
		AUTHENTICATION,
		VIEW_PROJECT;
	}
	
	protected static User currentUser;
	protected static User newUser;
	
	protected static boolean authenticated = false;
	
	protected static String selectedProject;
	
	protected void loadPreviousScene(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(previousScene);
		stage.show();
	}
		
	protected void changeStage(EffortLoggerStage nextStage, Event e) {
		switch(nextStage) {
		case LOGIN:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		case REGISTER_ONE:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("RegisterOne.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		case REGISTER_TWO:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("RegisterTwo.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		case EFFORT_LOGGER:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("EffortLoggerConsole.fxml"));
				Scene scene = new Scene(root, 800, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		case AUTHENTICATION:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("Authentication.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Tab) e.getSource()).getTabPane().getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		case VIEW_PROJECT:
			try {
				Parent root = (Parent) FXMLLoader.load(getClass().getResource("ViewProject.fxml"));
				Scene scene = new Scene(root, 810, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch(Exception exc) {
				exc.printStackTrace();
			}
			break;
		}
				
	}
	
}
