package vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modele.DAO;
import service.ServiceCarnetAdresse;

public class ControllerAjoutContact {

	ServiceCarnetAdresse service= new ServiceCarnetAdresse();

	@FXML
	private TextField textNom;

	@FXML
	private TextField textPrenom;

	@FXML
	private ChoiceBox<?> cbGroupe;

	@FXML
	private TextField textAdresse;

	@FXML
	private TextField textFax;

	@FXML
	private TextField textEmail;

	@FXML
	private TextField textTelephone;

	@FXML
	private Button btnAjoutAdresse;

	@FXML
	private Button btnAjoutTelephone;

	@FXML
	private Button btnAjoutMail;

	@FXML
	private Button btnValide;

	@FXML
	private Button btnAnnuler;

	@FXML
	private Button btnAvatar;

	@FXML
	void btnAjoutAdresse_onAction(ActionEvent event) {

	}

	@FXML
	void btnAjoutMail_onAction(ActionEvent event) {

	}

	@FXML
	void btnAjoutTelephone_onAction(ActionEvent event) {

	}

	@FXML
	void btnAnnuler_onAction(ActionEvent event) throws IOException {
		Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
		Scene pageAjoutScene= new Scene(pageAjoutParent);
		Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(pageAjoutScene);
		app_stage.show();
	}

	@FXML
	void btnAvatar_onAction(ActionEvent event) throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			FileInputStream fileInputStream = new FileInputStream(selectedFile);
			service.setPhoto(1, fileInputStream);
		}
		// Il faudra vérifier le format (jpg, png...), la taille (pas trop lourde)
		// Puis rendre l'image visible dans un petit cadre ?
		// Mettre également un avatar par défaut
	}

	@FXML
	void btnValide_onAction(ActionEvent event) {
	}

	@FXML
	void textNom_onAction(ActionEvent event) {
		if (textNom.equals("") || textNom.equals(null)){
			textTelephone.disabledProperty();
		}
		else {
			textTelephone.editableProperty();
		}
	}


}

