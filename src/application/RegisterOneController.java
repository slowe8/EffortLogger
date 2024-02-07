package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/* Authored by Sean Lowe
 * This class controls the first Register screen 
 */

public class RegisterOneController extends GUIController {
	
	@FXML
	private TextField firstNameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button nextButton;
	@FXML
	private TextField lastNameField;
	@FXML 
	private TextField middleNameField;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private Label registerMessageLabel;
	
	public void nextButtonOnAction(ActionEvent e) {
		try {
			// Saves data from user to shared buffer and swicthes screens
			if(passwordField.getText().equals(confirmPasswordField.getText())) {
				newUser = new User(usernameField.getText(), passwordField.getText(),firstNameField.getText(),
						middleNameField.getText(), lastNameField.getText());
			} else {
				registerMessageLabel.setText("Passwords do not match");
				return;
			}
			
			changeStage(EffortLoggerStage.REGISTER_TWO, e);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
}
