module demo {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires fontbox;
	requires pdfbox;
	requires gson;
	requires org.apache.commons.io;
	requires java.sql;
	requires fop;
	requires batik.transcoder;
	requires avalon.framework.api;
	requires java.desktop;
	requires pdfbox.tools;

	exports demo.dto;
	opens demo.dto;
//	requires  org.apache.avalon.framework.configuration.Configurable;
	opens demo;

	exports demo;
}