package micv.fx.tabs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import micv.fx.classes.Conocimiento;

public class ConocimientosController implements Initializable {

	// View : FXML
	
	//-------------------------------------------------------------------------
    @FXML
    private HBox view;

    @FXML
    private TableView<Conocimiento> conocimientoTbl;

    @FXML
    private Button addKBt;

    @FXML
    private Button addLBt;

    @FXML
    private Button delBt;
    
    //-------------------------------------------------------------------------
    
    // Model
    private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<Conocimiento>(FXCollections.observableArrayList(new ArrayList<Conocimiento>()));
    
	public ConocimientosController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		conocimientoTbl.itemsProperty().bind(conocimientos);
	}
	
	public HBox getRootView() {
		return view;
	}

	public final ListProperty<Conocimiento> conocimientosProperty() {
		return this.conocimientos;
	}
	

	public final ObservableList<Conocimiento> getConocimientos() {
		return this.conocimientosProperty().get();
	}
	

	public final void setConocimientos(final ObservableList<Conocimiento> conocimientos) {
		this.conocimientosProperty().set(conocimientos);
	}
	
	

}
