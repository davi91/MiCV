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
import micv.fx.classes.Titulo;


public class FormacionController implements Initializable {

	// View : FXML
	//-------------------------------------------------------------------------
	
	@FXML
	private HBox view;

	@FXML
	private TableView<Titulo> formacionTbl;

	@FXML
	private Button addBt;

	@FXML
	private Button removeBt;
	
	//-------------------------------------------------------------------------
	    
	// Model
	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	
	public FormacionController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/FormacionView.FXML"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		formacionTbl.itemsProperty().bind(titulos);
	}
	
	public HBox getRootView() {
		return view;
	}

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final ObservableList<Titulo> titulos) {
		this.titulosProperty().set(titulos);
	}
	

}
