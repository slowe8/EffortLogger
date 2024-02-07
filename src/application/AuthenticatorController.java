package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/*
 * Authentication Screen Controller for authentication-protected tabs
 * Authored by Sean Lowe
 * 
 * This controller takes a user input password and checks it against their stored password
 * in the database.
 * 
 * If the password they entered matches, it returns them to the Tab they selected
 * If the password does not match, it displays a message that the password is wrong.
 */
public class AuthenticatorController extends GUIController {
	
	// Objects to be loaded via FXML
	@FXML
	private PasswordField passwordField;
	@FXML 
	private Button authenticateButton;
	@FXML
	private Label messageLabel;
	
	// onAction function for authenticateButton
	public void authenticateButtonOnAction(ActionEvent e) {
		authenticated = false; // Sets super-static flag to false
		
		// If entered password matches password for the current user
		// - return to previous screen
		// else
		// - display failed message
		if (currentUser.authenticatePassword(passwordField.getText())) {
			authenticated = true;
			messageLabel.setText("Authenticated!");
			loadPreviousScene(e);
		} else {
			messageLabel.setText("Not authenticated.");
		}
	}
	
}
