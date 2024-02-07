package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/* Authored by Sean Lowe
 * This class is what finishes the register transaction for the user 
 */

public class RegisterTwoController extends GUIController {
	
	@FXML
	private Label registerMessageLabel;
	@FXML
	private Button registerButton;
	@FXML
	private TextField addressField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField zipField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneField;
	
	// Registers user with inputed data
	public void registerButtonOnAction(ActionEvent e) {
		
		newUser.setPersonal(addressField.getText(),	cityField.getText(), phoneField.getText(), zipField.getText(), emailField.getText(), phoneField.getText());
		if(newUser.sendRegister(e)) {
			changeStage(EffortLoggerStage.LOGIN, e);
		} else {
			registerMessageLabel.setText("Not Registered!");
			return;
		}
		
	}
	/*
	public void validateLogin() {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDB = connectNow.getConnection();
	
		// Check for invalid input
		
		String verifyLogin = "SELECT count(1) FROM users WHERE user_name = ? AND user_password = ?;";
		
		try {
			PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
			statement.setString(1, usernameTextField.getText());
			statement.setString(2, passwordField.getText());
			ResultSet queryResult = statement.executeQuery();
			
			while(queryResult.next()) {
				if(queryResult.getInt(1) == 1) {
					loginMessageLabel.setText("Logged In!");
				} else {
					loginMessageLabel.setText("Invalid login.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
}
