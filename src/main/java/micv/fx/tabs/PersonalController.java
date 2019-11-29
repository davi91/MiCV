package micv.fx.tabs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import micv.fx.classes.Nacionalidad;
import micv.fx.classes.Personal;

public class PersonalController implements Initializable {

	// FXML : View
	//---------------------------------------------------------
	@FXML
	private GridPane view;
	
	@FXML
	private TextField dniTxt;

	@FXML
	private TextField nombreTxt;

	@FXML
	private TextField apellidosTxt;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TextArea dirTxt;

	@FXML
	private TextField codTxt;

	@FXML
	private TextField localTxt;

	@FXML
	private ComboBox<String> paisCbox;

	@FXML
	private ListView<Nacionalidad> nacionalidadList;

	@FXML
	private Button addBt;

	@FXML
	private Button removeBt;
	
	//---------------------------------------------------------
	
	// Model, sólo guardamos una referencia al personal
	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>(new Personal());
	
	public PersonalController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		getPersonal().nombreProperty().bindBidirectional(nombreTxt.textProperty());
		getPersonal().nombreProperty().bindBidirectional(nombreTxt.textProperty());
		getPersonal().apellidosProperty().bindBidirectional(apellidosTxt.textProperty());
		getPersonal().codigoPostalProperty().bindBidirectional(codTxt.textProperty());
		getPersonal().localidadProperty().bindBidirectional(localTxt.textProperty());
		getPersonal().fechaNacimientoProperty().bindBidirectional(datePicker.valueProperty());
		nacionalidadList.itemsProperty().bind(getPersonal().nacionaliadesProperty());
		getPersonal().paisProperty().bind(paisCbox.valueProperty());
		
		addBt.setOnAction( evt -> onAddAction() );
		removeBt.setOnAction( evt -> onRemoveAction() );

	}

	private void onRemoveAction() {

	}

	private void onAddAction() {

	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}
	

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}
	

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}
	
	public GridPane getRootView() {
		return view;
	}

}
