package micv.fx.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Web {

	private StringProperty web = new SimpleStringProperty();

	public Web(String web) {
		this.web.set(web);
	}

	public final StringProperty webProperty() {
		return this.web;
	}
	

	public final String getWeb() {
		return this.webProperty().get();
	}
	

	public final void setWeb(final String web) {
		this.webProperty().set(web);
	}
	
}
