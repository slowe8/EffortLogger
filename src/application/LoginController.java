package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/*
 * This class is the GUI controller for the login screen
 */

public class LoginController extends GUIController {
	
	// FXML loading variables for active members of the GUI
	
	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button registerButton;
	@FXML
	private Label loginMessageLabel;
	
	static int user_id;
	
	// Checks is a user is able to login
	public void loginButtonOnAction(ActionEvent e) {
		if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
			//loginMessageLabel.setText("Logged in!");
			currentUser = new User(usernameTextField.getText());
			if(currentUser.validateLogin(e, passwordField.getText())) {
				changeStage(EffortLoggerStage.EFFORT_LOGGER, e);
			} else {
				loginMessageLabel.setText("Not able to login.");
			}
		} else {
			loginMessageLabel.setText("Please enter a user name and password.");
		}
	}
	
	// Switches to the Register GUI
	public void registerButtonOnAction(ActionEvent e) {
		changeStage(EffortLoggerStage.REGISTER_ONE, e);
	}
	
	// Validates if the user has entered a valid login
	
	
}
