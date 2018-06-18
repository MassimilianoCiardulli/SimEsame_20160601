package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    
    private Model model ;

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	Airline airline = this.boxAirline.getValue();
    	Airport airport = this.boxAirport.getValue();
    	
    	if(airline == null) {
    		this.txtResult.setText("ERRORE: selezionare una compagnia aerea.\n");
    		return ;
    	}
    	if(airport == null) {
    		this.txtResult.setText("ERRORE: selezionare un aeroporto di partenza.\n");
    		return ;
    	}
    	
    	List<Airport> raggiungibili = model.getReachedAirports(airline, airport) ;
    	
    	if(raggiungibili.isEmpty()) {
    		this.txtResult.appendText("Non ci sono aeroporti raggiungibili.\n");
    		return ;
    	}
    	txtResult.appendText("\nRAGGIUNGIBILI:\n");
    	for(Airport a : raggiungibili)
    		this.txtResult.appendText(a + "\n");
    }

    @FXML
    void doServiti(ActionEvent event) {
    	Airline airline = this.boxAirline.getValue() ;
    	if(airline == null) {
    		this.txtResult.appendText("ERRORE: selezionare una compagnia aerea.\n");
    		return ;
    	}
    	model.createGraph(airline) ;
    	
    	List<Airport> airports = model.getReachedAirports(airline) ;
    	
    	if(airports.isEmpty()) {
    		this.txtResult.appendText("Non sono raggiungibili aeroporti.\n");
    		return ;
    	}
    	
    	for(Airport a : airports) {
    		this.txtResult.appendText(a + "\n");
    		this.boxAirport.getItems().add(a) ;
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		for(Airline a : model.getAirlines()) {
			this.boxAirline.getItems().add(a) ;
		}
	}
}
