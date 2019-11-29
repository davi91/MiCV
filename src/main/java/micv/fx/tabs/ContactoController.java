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
import javafx.scene.layout.VBox;
import micv.fx.classes.Contacto;
import micv.fx.classes.Email;
import micv.fx.classes.Web;

public class ContactoController implements Initializable {

	// View : FXML
	//-------------------------------------------------------------------------
	
    @FXML
    private VBox view;

    @FXML
    private TableView<Contacto> tlfTable;

    @FXML
    private Button tlf_addBt;

    @FXML
    private Button tlf_removeBt;

    @FXML
    private TableView<Email> emailTable;

    @FXML
    private Button email_addBt;

    @FXML
    private Button email_removeBt;

    @FXML
    private TableView<Web> webTable;

    @FXML
    private Button web_addBt;

    @FXML
    private Button web_removeBt;
    
    //-------------------------------------------------------------------------
    
    // Model
    private ListProperty<Contacto> telefonos = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    private ListProperty<Email> emails = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    private ListProperty<Web> webs = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
	public ContactoController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tlfTable.itemsProperty().bind(telefonos);
		emailTable.itemsProperty().bind(emails);
		webTable.itemsProperty().bind(webs);
	}
	
	public VBox getRootView() {
		return view;
	}

	public final ListProperty<Contacto> telefonosProperty() {
		return this.telefonos;
	}
	

	public final ObservableList<Contacto> getTelefonos() {
		return this.telefonosProperty().get();
	}
	

	public final void setTelefonos(final ObservableList<Contacto> telefonos) {
		this.telefonosProperty().set(telefonos);
	}
	

	public final ListProperty<Email> emailsProperty() {
		return this.emails;
	}
	

	public final ObservableList<Email> getEmails() {
		return this.emailsProperty().get();
	}
	

	public final void setEmails(final ObservableList<Email> emails) {
		this.emailsProperty().set(emails);
	}
	

	public final ListProperty<Web> websProperty() {
		return this.webs;
	}
	

	public final ObservableList<Web> getWebs() {
		return this.websProperty().get();
	}
	

	public final void setWebs(final ObservableList<Web> webs) {
		this.websProperty().set(webs);
	}
	
	

}
