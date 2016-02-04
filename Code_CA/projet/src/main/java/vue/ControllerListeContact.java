
package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerListeContact {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRechercherContact;

    @FXML
    private Button btnSupprimerSelection;

    @FXML
    private Button btnAffichageFavoris;

    @FXML
    private Button btnGestionGroupe;

    @FXML
    private Button btnAjouterContact;

    @FXML
    private Button btnModifierContact;

    @FXML
    void btnAffichageFavoris_onAction(ActionEvent event) {
    }

    @FXML
    void btnAjouterContact_onChange(ActionEvent event)throws IOException {
    	System.out.println("controller ok");
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("ajoutContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();

    }

    @FXML
    void btnGestionGroupe_onChange(ActionEvent event) throws IOException {
    	System.out.println("controller ok");
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("gestionGroupe.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();

    }

    @FXML
    void btnModifierContact_onChange(ActionEvent event) throws IOException {
    	System.out.println("controller ok");
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("detailContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

    @FXML
    void btnRechercherContact_onAction(ActionEvent event) {

    }

    @FXML
    void btnSupprimerSelection_onAction(ActionEvent event) {

    }
}