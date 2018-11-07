package secretarytools;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.DoctorController;
import controllers.SecretaryController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import models.Doctor;
import models.Models;
import models.Slots;
import models.Users;

public class ManageClients {

	private SecretaryController control;
	private ArrayList<Slots> list;
	private Models model;
	private Users user;
	

	
//    @FXML private ResourceBundle resources;
//    @FXML private URL location;
    @FXML private Button btnExit;
    @FXML private ComboBox<String> cmbClient;
    @FXML private ComboBox<String> cmbResSlot;
    @FXML private Button btnRemove;
    
    
    @FXML public void exit() 
    {
    	Stage stage = (Stage)btnExit.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    public void cancelReservation() 
    {
    	Stage stage = (Stage)btnExit.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    public void addChoices()
    {
    	if(!cmbClient.getItems().isEmpty())
    	{
    		cmbClient.getItems().clear();
    		list.clear();
    	}
    	
    	list = model.getReservedSlots();
    	
    	for(int i = 0; i < list.size(); i++)
    	{
    		cmbClient.getItems().add(list.get(i).getTimeStart() + " - " + list.get(i).getTimeEnd());
    	}
    }
    
    public void setController(SecretaryController control)
    {
    	this.control = control;
    }
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
    public void setUser(Users user)
    {
    	this.user = user;
    }
    
//    public void initiateListeners()
//    {
//    	dtpChoose.valueProperty().addListener((ov, oldValue, newValue) -> {
//            date = newValue;
//            addChoices();
//            disableWidgets();
//        });
//    	
//    	cmbSlots.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//        	@Override
//        	public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//        		cmbSlots.getSelectionModel().clearAndSelect((Integer) number2);
//        		setUpEdit((Integer) number2);
//        	}
//        });
//    	
//    	addChoices();
//    }

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'ManageClients.fxml'.";
        assert cmbClient != null : "fx:id=\"cmbClient\" was not injected: check your FXML file 'ManageClients.fxml'.";
        assert cmbResSlot != null : "fx:id=\"cmbResSlot\" was not injected: check your FXML file 'ManageClients.fxml'.";
        assert btnRemove != null : "fx:id=\"btnRemove\" was not injected: check your FXML file 'ManageClients.fxml'.";

        //addChoices();
        
    }
}
