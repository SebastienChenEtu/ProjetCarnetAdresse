package vue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.Groupe;
import service.ServiceCarnetAdresse;

public class ControllerGestionGroupe {

	ServiceCarnetAdresse service= new ServiceCarnetAdresse();
	
    @FXML
    private ChoiceBox<String> cbSupprimerGroupe;

    @FXML
    private Button btnSupprimer;

    @FXML
    private ChoiceBox<String> cbFusionG1;

    @FXML
    private ChoiceBox<String> cbFusionG2;

    @FXML
    private Button btnFusion;

    @FXML
    private Button btnRetour;

    @FXML
    private ChoiceBox<String> cbModifierGroupe;

    @FXML
    private TextField textModifierGroupe;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAjouterGroupe;

    @FXML
    private TextField textAjoutGroupe;

    @FXML
    private TextField textFusionGroupe;

	@FXML
    void initialize() throws SQLException{
		
		cbModifierGroupe.getItems().clear();
		cbFusionG2.getItems().clear();
		cbFusionG1.getItems().clear();
		cbSupprimerGroupe.getItems().clear();
		
		ArrayList<Groupe> groupe = new ArrayList<Groupe> (service.trouverToutGroupe());
		ArrayList<String> nomGroupe = new ArrayList<String>();
		for (Groupe g : groupe){
			nomGroupe.add(g.getNom());
		}
		
    	cbModifierGroupe.getItems().addAll(nomGroupe);
		cbFusionG2.getItems().addAll(nomGroupe);
		cbFusionG1.getItems().addAll(nomGroupe);
		cbSupprimerGroupe.getItems().addAll(nomGroupe);
			
    }
    
    @FXML
    void btnAjouterGroupe_onAction(ActionEvent event) throws SQLException {
    	Groupe g = new Groupe();
    	g.setNom(textAjoutGroupe.getText());
    	try {
			service.CreerGroupe(g);
		} catch (Exception e) {
			System.out.println("création echouee");
		}
    	this.initialize();
    	textAjoutGroupe.clear();
    }

    @FXML
    void btnFusion_onAction(ActionEvent event) {

    }

    @FXML
    void btnModifier_onAction(ActionEvent event) throws Exception {
    	service.setNomGroupe(cbModifierGroupe.getValue(),textModifierGroupe.getText() );
    	this.initialize();
    	textModifierGroupe.clear();
    }

  

    @FXML
    void btnSupprimer_onAction(ActionEvent event) throws Exception {
    	String groupe = ( cbSupprimerGroupe.getValue());
    	service.SupprimerGroupe(groupe);
    	this.initialize();
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