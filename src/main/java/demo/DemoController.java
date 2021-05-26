package demo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FilenameUtils;

import demo.dto.CardInfoDTO;
import demo.service.PDFService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class DemoController implements Initializable {
	@FXML
	private Button btnExport;

	@FXML
	private TextField txtCompanyName, txtFullName, txtOrderId, txtOrderNumber, txtAvatar, txtBackground, txtLogo,
			txtFont, txtDataJson;

	@FXML
	private RadioButton rdFront, rdBack;

	@FXML
	private DatePicker txtOrderDate;

	private CardInfoDTO cardInfo = new CardInfoDTO(1);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		try {
//			String dataJsonPath = Paths.get("data.json").toAbsolutePath().toString();
//			txtDataJson.setText(dataJsonPath);
//		} catch (Exception e) {
//			showAlertText("Please select data.json file");
//			btnExport.setDisable(true);
//		}
//
//		try {
//			String fontPath = Paths.get("font").toAbsolutePath().toString();
//			txtFont.setText(fontPath);
//		} catch (Exception e) {
//			showAlertText("Please select font");
//			btnExport.setDisable(true);
//		}
		
	}

	public void exportFrontPdf(ActionEvent event) throws IOException, TranscoderException, URISyntaxException {
		cardInfo.setCompanyName(txtCompanyName.getText());
		cardInfo.setFullName(txtFullName.getText());
		cardInfo.setOrderDate(txtOrderDate.getValue().toString());
		cardInfo.setOrderId(txtOrderId.getText());
		cardInfo.setOrderNumber(txtOrderNumber.getText());
//		cardInfo.setDataJson(new File(txtDataJson.getText()));
//		cardInfo.setUrlFont(txtFont.getText());

		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("example");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf", "*.PDF");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(window);
		event.consume();
		PDFService pdfService = new PDFService();

		String fileName;
		if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("pdf")) {
			fileName = file.getAbsolutePath();
		} else {
			// append .pdf if "fileName.jpg.pdf"
			fileName = file.getName().toString() + ".pdf";

			// ALTERNATIVELY: remove the extension (if any) and replace it with ".pdf"
			fileName = file.getParentFile() + "/" + FilenameUtils.getBaseName(file.getName()) + ".pdf";
		}
		File pdfFile = pdfService.createPDF(fileName, cardInfo);
		showAlert(pdfFile);

	}

	private void showAlert(File file) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");

		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText("Create file sucessfully! File place: " + file.getAbsolutePath());
		alert.showAndWait();
	}

	private void showAlertText(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");

		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void closePopup() {
		Platform.exit();
	}

	@FXML
	public void showAvatarChoose(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			txtAvatar.setText(file.getAbsolutePath());
			cardInfo.setAvatar(file);
		}
		event.consume();

	}

	public void showBackgroundChooser(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			txtBackground.setText(file.getAbsolutePath());
			cardInfo.setBackground(file);
		}
		event.consume();
	}

	public void showLogoChooser(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			txtLogo.setText(file.getAbsolutePath());
			cardInfo.setLogo(file);
		}
		event.consume();
	}

	public void clickFrontRadio() {
		rdBack.setSelected(false);
		rdFront.setSelected(true);
		cardInfo.setCardSide(1);
	}

	public void clickBackRadio() {
		rdBack.setSelected(true);
		rdFront.setSelected(false);
		cardInfo.setCardSide(2);
	}

	public void chooseData(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(window);

		if (file != null) {
			txtDataJson.setText(file.getAbsolutePath());
			cardInfo.setDataJson(file);
		}
		event.consume();
	}

	public void chooseFont(ActionEvent event) {
		Window window = ((Node) (event.getSource())).getScene().getWindow();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File file = directoryChooser.showDialog(window);

		if (file != null) {
			txtFont.setText(file.getAbsolutePath());
			cardInfo.setUrlFont(file.getAbsolutePath());
		}
		event.consume();
	}
}
