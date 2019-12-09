package micv.fx.utils;


import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import micv.fx.classes.Experiencia;
import micv.fx.classes.Titulo;

public class InsertTableDialog<T> extends Dialog<T> {

	/**
	 * Constructor para insertar una experiencia o formación
	 * @param titulo Cabecera del diálogo
	 * @param tipo Contenido de la segunda fila
	 * @param myClass Clase base, si es Experiencia o Titulo
	 */
	public InsertTableDialog(String titulo, String tipo, Class<T> myClass ) {
		
		setTitle(titulo);
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResource("/images/cv64x64.png").toString()));
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label denLbl = new Label("Denominación");
		TextField denTxt = new TextField();
		root.addRow(0, denLbl, denTxt);
			
		Label secRowLbl = new Label(tipo);
		TextField secRowTxt = new TextField();
		root.addRow(1,  secRowLbl, secRowTxt);
		
		GridPane.setHgrow(denTxt, Priority.ALWAYS);
		GridPane.setHgrow(secRowTxt, Priority.ALWAYS);
		
		Label desdeLbl = new Label("Desde");
		DatePicker desde = new DatePicker();
		root.addRow(2, desdeLbl, desde);

		Label hastaLbl = new Label("Hasta");
		DatePicker hasta = new DatePicker();
		root.addRow(3,  hastaLbl, hasta);
		
		getDialogPane().setContent(root);
		getDialogPane().setPrefWidth(384.f);
		
		ButtonType createBt = new ButtonType("Crear", ButtonData.OK_DONE);
		ButtonType cancelBt = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(createBt, cancelBt);
		
		setResultConverter( bt -> {

			// Aquí nos viene bien usar genéricos, puesto que ambos tienen similares constructores
			if( bt.getButtonData() == ButtonData.OK_DONE ) {
				return myClass.cast( myClass == Experiencia.class ?
						new Experiencia(desde.getValue(), hasta.getValue(), denTxt.getText(), secRowTxt.getText())
					:   new Titulo(desde.getValue(), hasta.getValue(), denTxt.getText(), secRowTxt.getText()));
			}
			
			return null;
		});
	}
}
