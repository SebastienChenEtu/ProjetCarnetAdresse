package vue;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Contact;
import modele.Groupe;
import service.ServiceCarnetAdresse;

public class ControllerDetailContact{

	private ControllerListeContact controller=new ControllerListeContact();

	private static ServiceCarnetAdresse service = new ServiceCarnetAdresse();

	private Contact c = new Contact();

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnRetour;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField textNom;

    @FXML
    private TextField textPrenom;

    @FXML
    private TextField textFax;

    @FXML
    private TextField textGroupe;

    @FXML
    private TextArea textlistTelephone;

    @FXML
    private TextArea textlistAdresse;

    @FXML
    private TextArea textlistEmail;


    @FXML
    void btnModifier_onAction(ActionEvent event) {
    }

    @FXML
    void initialize(){
    	c = controller.getContact();
    	textNom.setText(c.getNom());
    	textPrenom.setText(c.getPrenom());
    	textFax.setText(c.getFax());
//    	Groupe g = new Groupe();
 //   	g = service.
 //   	textGroupe.setText();

    }

    @FXML
    void btnRetour_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

    @FXML
    void btnSupprimer_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

}

