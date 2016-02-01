package vue;

import java.io.IOException;

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

public class ControllerAjoutContact {

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
	void btnAvatar_onAction(ActionEvent event) {

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

