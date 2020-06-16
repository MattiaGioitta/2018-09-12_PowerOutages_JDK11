package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.model.createGraph();
    	this.txtResult.appendText("Grafo creato\n");
    	this.txtResult.appendText("#Vertici: "+this.model.nVertici()+"\n");
    	this.txtResult.appendText("#Archi: "+this.model.nArchi()+"\n");
    	this.cmbBoxNerc.getItems().addAll(this.model.getVertices());

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Integer k;
    	try {
    		k= Integer.parseInt(this.txtK.getText());
    		this.model.simula(k);
    		this.txtResult.appendText("Numero catastrofi simulate: "+this.model.getCatastrofi()+"\n");
    		for(Nerc n : this.model.getVertices()) {
    			if(n.getBonus()>0) {
    				this.txtResult.appendText(n.toString()+" bonus: "+n.getBonus()+"\n");
    			}
    		}
    	}catch(NumberFormatException e ) {
    		this.txtResult.setText("Inserisci formato corretto per k");
    		return;
    	}

    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	this.txtResult.clear();
    	Nerc n = this.cmbBoxNerc.getValue();
    	if(n== null) {
    		this.txtResult.setText("Scegli un nerc");
    		return;
    	}
    	List<Vicino> vicini = this.model.vicini(n);
    	if(vicini == null) {
    		this.txtResult.setText("Nessun vicino per "+n.toString());
    		return;
    	}
    	this.txtResult.appendText("Vicini di: "+n.toString()+"\n");
    	for(Vicino v : vicini) {
    		this.txtResult.appendText(v.toString()+"\n");
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
