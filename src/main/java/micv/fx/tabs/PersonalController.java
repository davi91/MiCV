package micv.fx.tabs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import micv.fx.classes.Nacionalidad;
import micv.fx.classes.Personal;

/**
 * Controlador de la pestaña de Personal, que carga sus propios datos
 * de piases y nacionalidades.
 * @author David Fernández Nieves
 *
 */
public class PersonalController implements Initializable {

	private final String paisesSource = "files/paises.csv";
	private final String nacionalidadesSource = "files/nacionalidades.csv";
	
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
	private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>();
	
	// Una lista para los paises
	private ListProperty<String> paisesList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	
	// Guardamos una lista de las nacionalidades, sólo la cargamos al principio
	private ArrayList<String> nacionalidadesList = new ArrayList<>();
	
	public PersonalController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Cargamos las nacionalidades
		getNacionalidades();
		
		// Bindeamos los paises a su lista correspondiente
		paisCbox.itemsProperty().bind(paisesList);

		// Cargamos los paises
		loadPaises();
		

		// Cada vez que actualizamos el objeto, avisamos
		personal.addListener((o, ol, nv) -> updateBinds(ol, nv));
		personal.set(new Personal()); // Empezamos con uno nuevo
		
		addBt.setOnAction( evt -> onAddAction() );
		removeBt.setOnAction( evt -> onRemoveAction() );
		
		removeBt.disableProperty().bind( nacionalidadList.getSelectionModel().selectedItemProperty().isNull() );
		
	}

	/**
	 * Cargamos las nacionalidades del fichero
	 */
	private void getNacionalidades() {
		
		FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader reader = null;
		
		try {
			
			file = new FileInputStream(nacionalidadesSource);
			in = new InputStreamReader(file, StandardCharsets.ISO_8859_1);
			reader = new BufferedReader(in);
			
			String line = null;
			
			while( (line = reader.readLine()) != null ) {
				// Ponemos la primera letra en mayúscula
				String str = line.trim();
				str = str.substring(0,1).toUpperCase() + str.substring(1);
				nacionalidadesList.add(str);
			}
			
		} catch(IOException e ) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if( reader != null ) {
					reader.close();
				}
				
				if( in != null ) {
					in.close();
				}
				
				if( file != null ) {
					file.close();
				}
				
			} catch (IOException e) {
			}
		}		
	}

	/**
	 * Cargamos los diferentes paises a partir de su archivo
	 */
	private void loadPaises() {
		
		FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader reader = null;
		
		try {
			
			file = new FileInputStream(paisesSource);
			in = new InputStreamReader(file, StandardCharsets.ISO_8859_1);
			reader = new BufferedReader(in);
			
			String line = null;
			
			while( (line = reader.readLine()) != null ) {
				paisesList.add(line.trim());
			}
			
		} catch(IOException e ) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if( reader != null ) {
					reader.close();
				}
				
				if( in != null ) {
					in.close();
				}
				
				if( file != null ) {
					file.close();
				}
				
			} catch (IOException e) {
			}
		}
		
	}

	/***
	 * Quitamos una nacionalidad de la lista
	 */
	private void onRemoveAction() {

		// Cogemos el elemento seleccionado actualmente y lo quitamos de la lista
		Nacionalidad miNacionalidad = nacionalidadList.getSelectionModel().getSelectedItem();
		if( miNacionalidad != null ) {
			getPersonal().getNacionaliades().remove(miNacionalidad);
		}
	}

	/**
	 * Añadimos una nacionalidad a la lista 
	 */
	private void onAddAction() {

		// Mostramos el diálogo correspondiente
		ChoiceDialog<String> dialog = new ChoiceDialog<>();
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad");
		dialog.getItems().addAll(nacionalidadesList);
		dialog.setSelectedItem(nacionalidadesList.get(0)); // Seleccionamos el primero por defecto
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResource("/images/cv64x64.png").toString()));
		
		Optional<String> response = dialog.showAndWait();
		
		if( response.isPresent() && response.get() != null ) {
			getPersonal().nacionaliadesProperty().add(new Nacionalidad(response.get()));
		}
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
	
	/**
	 * Necesitamos actualizar los bindings cada vez
	 * que actualizamos el objeto, ya que creamos
	 * uno nuevo con sus propias properties.
	 */
	private void updateBinds(Personal oldPersonal, Personal nuevoPersonal) {
		
		if( oldPersonal != null) {
			
			nombreTxt.textProperty().unbindBidirectional(oldPersonal.nombreProperty());
			apellidosTxt.textProperty().unbindBidirectional(oldPersonal.apellidosProperty());
			codTxt.textProperty().unbindBidirectional(oldPersonal.codigoPostalProperty());
			localTxt.textProperty().unbindBidirectional(oldPersonal.localidadProperty());
			datePicker.valueProperty().unbindBidirectional(oldPersonal.fechaNacimientoProperty());
			dniTxt.textProperty().unbindBidirectional(oldPersonal.identificacionProperty());
			dirTxt.textProperty().unbindBidirectional(oldPersonal.direccionProperty());
		}
		
		// Bidireccional, puesto que queremos añadir cambios en el CV de una persona
		// Primero ponemos el de la interfaz y luego el del objeto, el orden importa, 
		// al menos si lo usas como arriba.
		Bindings.bindBidirectional(nombreTxt.textProperty(), nuevoPersonal.nombreProperty());
		Bindings.bindBidirectional(apellidosTxt.textProperty(), nuevoPersonal.apellidosProperty());
		Bindings.bindBidirectional(codTxt.textProperty(), nuevoPersonal.codigoPostalProperty());
		Bindings.bindBidirectional(localTxt.textProperty(), nuevoPersonal.localidadProperty());
		Bindings.bindBidirectional(datePicker.valueProperty(), nuevoPersonal.fechaNacimientoProperty());
		Bindings.bindBidirectional(dniTxt.textProperty(), nuevoPersonal.identificacionProperty());
		Bindings.bindBidirectional(dirTxt.textProperty(), nuevoPersonal.direccionProperty());
		Bindings.bindBidirectional(paisCbox.valueProperty(), nuevoPersonal.paisProperty());
		
		nacionalidadList.itemsProperty().bind(nuevoPersonal.nacionaliadesProperty());
		
		// Al bindear se me ajusta la selección, debemos indicar que no hay nada seleccionado
		paisCbox.setValue(null);
	}
	
	public GridPane getRootView() {
		return view;
	}

}
