/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.CittaDistanza;
import it.polito.tdp.nyc.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="cmbQuartiere"
    private ComboBox<String> cmbQuartiere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="clQuartiere"
    private TableColumn<CittaDistanza, String> clQuartiere; // Value injected by FXMLLoader
 
    @FXML // fx:id="clDistanza"
    private TableColumn<CittaDistanza, Double> clDistanza; // Value injected by FXMLLoader
    
    @FXML // fx:id="tblQuartieri"
    private TableView<CittaDistanza> tblQuartieri; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.cmbQuartiere.getItems().clear();
    	String provider= this.cmbProvider.getValue();
    	if(provider!=null) {
    		this.model.creaGrafo(provider);
    		this.txtResult.setText(this.model.nVertici());
    		this.txtResult.appendText(this.model.nArchi());
    		this.cmbQuartiere.getItems().addAll(this.model.getVertici());
    		
    	}else {
    		this.txtResult.setText("Selezionare un provider valido!");
    	}
    	
    }

    @FXML
    void doQuartieriAdiacenti(ActionEvent event) {
    	this.tblQuartieri.getItems().clear();
    	String quartiere= this.cmbQuartiere.getValue();
    	if(quartiere!= null) {
    		List<CittaDistanza> distanze= this.model.quartieriAdiacenti(quartiere);
    		this.tblQuartieri.setItems(FXCollections.observableArrayList(distanze));
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbQuartiere != null : "fx:id=\"cmbQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clDistanza != null : "fx:id=\"clDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clQuartiere != null : "fx:id=\"clQuartiere\" was not injected: check your FXML file 'Scene.fxml'.";
        
        
        this.clQuartiere.setCellValueFactory(new PropertyValueFactory<CittaDistanza, String>("city"));
        this.clDistanza.setCellValueFactory(new PropertyValueFactory<CittaDistanza, Double>("distance"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbProvider.getItems().addAll(this.model.popolaCmb());
    }

}
