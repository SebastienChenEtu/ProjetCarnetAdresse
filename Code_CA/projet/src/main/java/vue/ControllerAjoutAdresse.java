package vue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modele.Adresse;
import modele.Contact;
import modele.Mail;
import modele.Type;
import service.ServiceCarnetAdresse;

public class ControllerAjoutAdresse {
		
	private ControllerDetailContact controller = new ControllerDetailContact();
	
	private ServiceCarnetAdresse service = new ServiceCarnetAdresse();
	
	private Contact c= new Contact();
	
	private List<Adresse> adresses = new ArrayList<Adresse>();
	private Type t = new Type();


    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField txtAdresse;

    @FXML
    private ComboBox<String> cbxType;
    
    @FXML
    void initialize() throws SQLException{
    	c = controller.getContact();
    	adresses = c.getAdresses();
    	List<Type> t = new ArrayList<Type>(service.TrouverTousType());
    	ArrayList<String> libType = new ArrayList<String>();
    	for (Type type : t){
    		libType.add(type.getLibelleType());
    	}
    	cbxType.getItems().addAll(libType);
    }

    @FXML
    void btnAjouter_onAction(ActionEvent event) throws  Exception {
    	if (!cbxType.getValue().equals(null) && !cbxType.getValue().equals("")){
    		t = service.TrouverType(cbxType.getValue());
    		t.toString();
    		if (!txtAdresse.getText().equals(null) && !txtAdresse.getText().equals("")){
    			Adresse a = new Adresse(txtAdresse.getText(),t.getIdType());
    			adresses.add(a);
    			service.setAdresses(c.getIdContact(), adresses);
    			Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("detailContact.fxml"));
    			Scene pageAjoutScene= new Scene(pageAjoutParent);
    			Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    			app_stage.setScene(pageAjoutScene); 
    			app_stage.show();
    		}
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Message d'information");
    		alert.setHeaderText("Le champs texte est vide");
    		alert.showAndWait();
    	}
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Aucun type choisi");
		alert.showAndWait();
    }

    @FXML
    void btnAnnuler_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("detailContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene); 
    	app_stage.show();
    }

}
