package secretarytools;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.SecretaryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Models;
import models.Slots;
import models.Users;

public class AddClient implements Initializable 
{
	/***********************************************
	 * Class Attributes:
	 */
	private ObservableList<String> time	= FXCollections.observableArrayList("0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130", "1200",
			"1230", "1300", "1330", "1400", "1430", "1500", "1530", "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930", "2000");
	private LocalDate date;
	private SecretaryController control;
	private Slots selectedSlot;
	private Users user;
	private ArrayList<Slots> list;
    private Models model;
	
	/***********************************************
	 * FXML Resources:
	 */
	@FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<String> cmbStart;
    @FXML private ComboBox<String> cmbEnd;
    @FXML private DatePicker datepicker;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private Label lblErrorMsg;
    @FXML private CheckBox ckbRecurring;
    @FXML private TextField txfClientName;
	
	/***********************************************
	 * FXML Methods:
	 */
    
    @FXML
    void cancel() 
    {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void confirmAdd() 
    {

    }
    @Override
	public void initialize(URL location, ResourceBundle resources) 
    {
    	assert cmbStart != null : "fx:id=\"cmbStart\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert cmbEnd != null : "fx:id=\"cmbEnd\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert btnConfirm != null : "fx:id=\"btnConfirm\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert lblErrorMsg != null : "fx:id=\"lblErrorMsg\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert ckbRecurring != null : "fx:id=\"ckbRecurring\" was not injected: check your FXML file 'AddClient.fxml'.";
        assert txfClientName != null : "fx:id=\"txfClientName\" was not injected: check your FXML file 'AddClient.fxml'.";
        
        datepicker.setValue(LocalDate.now());
        cmbStart.setItems(time);
        cmbEnd.setItems(time);
        cmbStart.getSelectionModel().selectFirst();
        cmbEnd.getSelectionModel().selectFirst();
        
        lblErrorMsg.setVisible(false);
	}
    /***********************************************
	 * Class Methods:
	 */
    
    public void setModels(Models model)
    {
    	this.model = model;
    }
    
	public void setDate(LocalDate date)
	{
		this.date = date;
		datepicker.setValue(this.date);
	}
	
	public void setController(SecretaryController control)
	{
		this.control = control;
		control.setAddClient(this);
	}
	
	
	public void setErrorMsg(String text)
	{
		lblErrorMsg.setText(text);
        lblErrorMsg.setVisible(true);
	}
	
    public void setUser(Users user)
    {
    	this.user = user;
    }
    
    public void initiateListeners()
    {
    	datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            date = newValue;
            addChoices();
        });
    	
    	addChoices();
    }
    
    public void addChoices()
    {
//    	list = model.getTimeSlots(date, (Doctor) user);
//    	
//    	for(int i = 0; i < list.size(); i++)
//    	{
//    		if(!((TimeSlot) list.get(i)).isReserved())
//    			cmbSlots.getItems().add(list.get(i).getTimeStart() + " - " + list.get(i).getTimeEnd());
//    		else
//    		{
//    			list.remove(i);
//    			i--;
//    		}
//    	}
    }

}
