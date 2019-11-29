package micv.fx.tabs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import micv.fx.classes.Experiencia;


public class ExperienciaController implements Initializable {

	// View : FXML
	//-------------------------------------------------------------------------
	
    @FXML
    private HBox view;

    @FXML
    private TableView<Experiencia> experienciaTbl;

    @FXML
    private Button addBt;

    @FXML
    private Button removeBt;
    
    //-------------------------------------------------------------------------
    
    // Model
    private ObjectProperty<Experiencia> experiencia = new SimpleObjectProperty<Experiencia>();
    
	public ExperienciaController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public HBox getRootView() {
		return view;
	}

	public final ObjectProperty<Experiencia> contactoProperty() {
		return this.experiencia;
	}
	

	public final Experiencia getContacto() {
		return this.contactoProperty().get();
	}
	

	public final void setContacto(final Experiencia contacto) {
		this.contactoProperty().set(contacto);
	}
	

	

}
