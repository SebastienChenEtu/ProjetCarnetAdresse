package vue;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerImport {


	@FXML
	private Button btnImportDonnees;

	@FXML
	private Button btnExportTous;

	@FXML
	private Button btnExportFavoris;

	@FXML
	private Button btnExportGroupe;

	@FXML
	private Button btnRetour;

	@FXML
	void btnExportFavoris_onAction(ActionEvent event) {

	}

	@FXML
	void btnExportGroupe_onAction(ActionEvent event) {

	}

	@FXML
	void btnExportTous_onAction(ActionEvent event) {

	}

	@FXML
	void btnImportDonnees_onAction(ActionEvent event) {
		
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

