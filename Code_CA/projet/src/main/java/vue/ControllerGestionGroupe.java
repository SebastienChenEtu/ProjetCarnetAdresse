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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	static ServiceCarnetAdresse service= new ServiceCarnetAdresse();

	ArrayList<Groupe> groupe;

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

		groupe = new ArrayList<Groupe> (service.trouverToutGroupe());
		ArrayList<String> nomGroupes = new ArrayList<String>();
		for (Groupe g : groupe){
			if (g.getIdGroupe()!= 0)
				nomGroupes.add(g.getNom());
		}

		cbModifierGroupe.getItems().addAll(nomGroupes);
		cbFusionG2.getItems().addAll(nomGroupes);
		cbFusionG1.getItems().addAll(nomGroupes);
		cbSupprimerGroupe.getItems().addAll(nomGroupes);
	}
	
	void ajouterGroupe(String nomGroupe){
		cbModifierGroupe.getItems().add(nomGroupe);
		cbFusionG2.getItems().add(nomGroupe);
		cbFusionG1.getItems().add(nomGroupe);
		cbSupprimerGroupe.getItems().add(nomGroupe);
	}

	@FXML
	void btnAjouterGroupe_onAction(ActionEvent event) throws Exception {
		Boolean b = true;
		Groupe g = new Groupe();
		if (!textAjoutGroupe.getText().equals(null) && !textAjoutGroupe.getText().equals("")){
			g.setNom(textAjoutGroupe.getText());
			for (Groupe gr : groupe){
				if (gr.getNom().equals(g.getNom())){
					b =false;
				}
			}
			if (b){
				service.CreerGroupe(g);
				ajouterGroupe(textAjoutGroupe.getText());
				textAjoutGroupe.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Message d'information");
				alert.setHeaderText("Groupe créé avec succés");
				alert.showAndWait();
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Message d'erreur");
				alert.setHeaderText("Un groupe de ce nom existe déjà");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Message d'avertissement");
			alert.setHeaderText("Aucun nom n'est entré pour le groupe");
			alert.showAndWait();
		}

	}

	@FXML
	void btnFusion_onAction(ActionEvent event) throws Exception {
		if (cbFusionG1.getValue().equals(cbFusionG2.getValue())){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Message d'avertissement");
			alert.setHeaderText("Vous ne pouvez pas fusionner le même groupe");
			alert.showAndWait();
		}else {
			Groupe g1 = new Groupe();
			g1.setNom(cbFusionG1.getValue());
			Groupe g2 = new Groupe();
			g2.setNom(cbFusionG2.getValue());
			service.FusionnerGroupe(g1,g2 ,textFusionGroupe.getText());
			this.initialize();
			textFusionGroupe.clear();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message d'information");
			alert.setHeaderText("Les groupes ont été fusionnés");
			alert.showAndWait();
		}
	}

	@FXML
	void btnModifier_onAction(ActionEvent event) throws Exception {
		Boolean b = true;
		Groupe g = new Groupe();
		if (!textModifierGroupe.getText().equals(null) && !textModifierGroupe.getText().equals("")){
			g.setNom(textModifierGroupe.getText());
			for (Groupe gr : groupe){
				if (gr.getNom().equals(g.getNom())){
					b =false;
				}
			}
			if(b){
			service.setNomGroupe(cbModifierGroupe.getValue(),textModifierGroupe.getText() );
			this.initialize();
			textModifierGroupe.clear();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message d'information");
			alert.setHeaderText("Le groupe a été modifié");
			alert.showAndWait();
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Message d'erreur");
				alert.setHeaderText("Un groupe de ce nom existe déjà");
				alert.showAndWait();
			}
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Message d'avertissement");
			alert.setHeaderText("Aucun nom n'est entré pour le groupe");
			alert.showAndWait();
		}
	}



	@FXML
	void btnSupprimer_onAction(ActionEvent event) throws Exception {
		String groupe = ( cbSupprimerGroupe.getValue());
		service.SupprimerGroupe(groupe);
		this.initialize();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le groupe a été supprimé");
		alert.showAndWait();
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