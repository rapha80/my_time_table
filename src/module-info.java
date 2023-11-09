module myTimeTable {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	exports Controller;
	
	opens application to javafx.graphics, javafx.fxml, javafx.controls;
	opens Controller to javafx.fxml;
	opens model to javafx.base;
}
