package micv.fx.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import micv.fx.tabs.ConocimientosController;
import micv.fx.tabs.ContactoController;
import micv.fx.tabs.ExperienciaController;
import micv.fx.tabs.FormacionController;
import micv.fx.tabs.PersonalController;

public class MainController implements Initializable {

	@FXML
	private BorderPane view;
	
	@FXML 
	private TabPane tabRoot;
	
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
			tabRoot.getTabs().get(0).setContent(personalController.getRootView());
			
			contactoController = new ContactoController();
			tabRoot.getTabs().get(1).setContent(contactoController.getRootView());

			formacionController = new FormacionController();
			tabRoot.getTabs().get(2).setContent(formacionController.getRootView());
			
			experienciaController = new ExperienciaController();
			tabRoot.getTabs().get(3).setContent(experienciaController.getRootView());
			
			conocimientosController = new ConocimientosController();
			tabRoot.getTabs().get(4).setContent(conocimientosController.getRootView());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BorderPane getRootView() {
		return view;
	}

}
