package micv.fx.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import micv.fx.classes.CV;
import micv.fx.classes.Contacto;
import micv.fx.classes.Personal;
import micv.fx.tabs.ConocimientosController;
import micv.fx.tabs.ContactoController;
import micv.fx.tabs.ExperienciaController;
import micv.fx.tabs.FormacionController;
import micv.fx.tabs.PersonalController;
import micv.fx.utils.JAXBUtils;

public class MainController implements Initializable {

	// View : FXML
	//-------------------------------------------------------------------------
	
	@FXML
	private BorderPane view;
	
	@FXML 
	private TabPane tabRoot;

    @FXML
    private MenuItem m_nuevo;

    @FXML
    private MenuItem m_abrir;

    @FXML
    private MenuItem m_guradar;

    @FXML
    private MenuItem m_guardarOtro;

    @FXML
    private MenuItem m_salir;

    @FXML
    private Tab tab_personal;

    @FXML
    private Tab tab_contacto;

    @FXML
    private Tab tab_formacion;

    @FXML
    private Tab tab_experiencia;

    @FXML
    private Tab tab_conocimientos;
	
  //-------------------------------------------------------------------------
    
    // Model
    // Básicamente vamos a tener el objeto CV
    private ObjectProperty<CV> cv = new SimpleObjectProperty<CV>();
    
	// Tabs controllers
	private PersonalController personalController;
	private ContactoController contactoController;
	private FormacionController formacionController;
	private ExperienciaController experienciaController;
	private ConocimientosController conocimientosController;
	
	public MainController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Debemos cargar ahora todos las pestañas
		try {
			personalController = new PersonalController();
			tab_personal.setContent(personalController.getRootView());
			
			contactoController = new ContactoController();
			tab_contacto.setContent(contactoController.getRootView());

			formacionController = new FormacionController();
			tab_formacion.setContent(formacionController.getRootView());
			
			experienciaController = new ExperienciaController();
			tab_experiencia.setContent(experienciaController.getRootView());
			
			conocimientosController = new ConocimientosController();
			tab_conocimientos.setContent(conocimientosController.getRootView());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Las listas de formación, experiencia y conociminetos las 
		// bindeamos directamente a las listas de las pestañas, ya
		// que no forman parte un único objeto
		cv.set(new CV());
		cv.get().setPersonal(new Personal());
		cv.get().setContacto(new Contacto());
		loadMainData();
		
		// Ahora cargamos los eventos de los menus
		m_abrir.setOnAction( evt -> onMenuOpen() );
		m_guardarOtro.setOnAction( evt -> onMenuSaveOther() );
		
	}
	
	private void onMenuSaveOther() {
		
		FileChooser browser = new FileChooser();
		
		browser.setTitle("Guardar CV como....");
		browser.getExtensionFilters().add(new ExtensionFilter("CV", "*.cv"));
		browser.setInitialFileName("nuevoCV.cv");
		browser.setInitialDirectory(new File(System.getProperty("user.dir") + "/files"));
		
		File file = browser.showSaveDialog(getRootView().getScene().getWindow());
		
		if( file != null ) {
			
			try {
				JAXBUtils.save(cv.get(), file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onMenuOpen() {
		
		FileChooser browser = new FileChooser();
		browser.getExtensionFilters().add(new ExtensionFilter("CV", "*.cv"));
		browser.setTitle("Abrir CV");
		browser.setInitialDirectory(new File(System.getProperty("user.dir") + "/files"));
		
		File file = browser.showOpenDialog(getRootView().getScene().getWindow());
		
		if( file != null ) {
			
			// Ahora podemos empezar a cargar el archivo
			// Usamos el JAXB para leer el XML
			
			try {
				
				CV myCV = JAXBUtils.load(CV.class, file);
				
				// Ahora lo cargamos en nuestro ObjectProperty
				cv.set(myCV);
				loadMainData();
				
			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadMainData() {
		
		// Cargamos todos los datos
		
		Personal personal = cv.get().getPersonal();
		personalController.setPersonal(personal);
		
		Contacto contacto = cv.get().getContacto();
		contactoController.setContacto(contacto);
		
		// Ahora bindeamos las listas de las otras pestañas para tenerlos actualizados
		formacionController.titulosProperty().bindBidirectional(cv.get().formacionProperty());
		experienciaController.listExperienciaProperty().bindBidirectional(cv.get().experienciasProperty());
		conocimientosController.conocimientosProperty().bindBidirectional(cv.get().habilidadesProperty());
	}

	public BorderPane getRootView() {
		return view;
	}

}
