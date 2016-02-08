package vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import service.ServiceCarnetAdresse;

public class ControllerImport {
	static ServiceCarnetAdresse service = new ServiceCarnetAdresse();

	@FXML
	private Button btnImportDonnees;

	@FXML
	private Button btnImportDonneesBase;

	@FXML
	private Button btnExportTous;

	@FXML
	private Button btnExportFavoris;

	@FXML
	private Button btnExportGroupe;

	@FXML
	private Button btnRetour;

	@FXML
	void btnExportFavoris_onAction(ActionEvent event) throws Exception {
		service.ExporterFavoris();
	}

	@FXML
	void btnExportGroupe_onAction(ActionEvent event) {
		//service.ExporterContactsGroupe(nomGroupe);
	}

	@FXML
	void btnExportTous_onAction(ActionEvent event) throws Exception {
		service.ExporterBase();
	}

	@FXML
	void btnImportDonnees_onAction(ActionEvent event) throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			service.ImporterFichier(selectedFile.getName());
		}
	}

	@FXML
	void btnImportDonneesBase_onAction(ActionEvent event) throws Exception {
		service.ImporterBase();
	}

	@FXML
	void btnRetour_onAction(ActionEvent event) throws IOException {
		Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
	}

}