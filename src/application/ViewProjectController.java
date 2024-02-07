package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ViewProjectController extends GUIController implements Initializable {
	
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox vbox;

    @FXML
    private TableColumn<EffortLog, String> dateCol;

    @FXML
    private TableColumn<EffortLog, String> deliverableCol;

    @FXML
    private TableColumn<EffortLog, String> effortCatCol;

    @FXML
    private TableView<EffortLog> effortLogsTable;

    @FXML
    private TableColumn<EffortLog, String> lifeCycleCol;

    @FXML
    private Label projectNameLabel;

    @FXML
    private TableColumn<EffortLog, String> startCol;

    @FXML
    private TableColumn<EffortLog, String> stopCol;
    
    @FXML
    private TableColumn<EffortLog, Integer> logIDCol;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TableView<DefectLog> defectLogTable;
    
    @FXML
    private TableColumn<DefectLog, Integer> defectIDCol;
    @FXML
    private TableColumn<DefectLog, String> defectCol;
    @FXML
    private TableColumn<DefectLog, String> detailsCol;
    @FXML
    private TableColumn<DefectLog, String> injectedCol;
    @FXML
    private TableColumn<DefectLog, String> removedCol;
    @FXML
    private TableColumn<DefectLog, String> categoryCol;
    @FXML
    private TableColumn<DefectLog, String> statusCol;
    @FXML
    private TableColumn<DefectLog, String> fixCol;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		// TODO Auto-generated method stub
		projectNameLabel.setText(selectedProject);
		
		logIDCol.setCellValueFactory(new PropertyValueFactory<EffortLog, Integer>("log_id"));
		lifeCycleCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("lifeCycle"));
		effortCatCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("effortCategory"));
		deliverableCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("deliverable"));
		dateCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("dateString"));
		startCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("startTimeString"));
		stopCol.setCellValueFactory(new PropertyValueFactory<EffortLog, String>("stopTimeString"));
		
		effortLogsTable.setItems(EffortLog.getLog(currentUser.getUserID(), selectedProject));
		
		defectIDCol.setCellValueFactory(new PropertyValueFactory<DefectLog, Integer>("defect_id"));
		defectCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("defect"));
		detailsCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("details"));
		injectedCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("injected"));
		removedCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("removed"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("category"));
		statusCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("status"));
		fixCol.setCellValueFactory(new PropertyValueFactory<DefectLog, String>("fix"));
		
		defectLogTable.setItems(DefectLog.getLog(currentUser.getUserID(), selectedProject));
	}
	
	public void backButtonOnAction(ActionEvent e) {
		loadPreviousScene(e);
	}

}