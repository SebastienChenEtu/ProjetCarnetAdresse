package vue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Adresse;
import modele.Contact;
import modele.Groupe;
import modele.Mail;
import modele.Telephone;
import service.ServiceCarnetAdresse;

public class ControllerListeContact implements Initializable  {
	
	 private static Contact contact;
	 
	 public Contact getContact(){
		 return contact;
	 }
	
	 private static final String PRENOM = "prenom";
	 private static final String NOM = "nom";
	 private static final String FAVORIS = "favoris";
	 private static final String SELECTION = "selection";
	 private ServiceCarnetAdresse service = new ServiceCarnetAdresse();
	 
	 private static ObservableList<Contact> contacts=FXCollections.observableArrayList();
	 
	 ArrayList<Groupe> groupe;
	 
	 
	/***************** Attributs fxml ********************/
	 
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private  TableView<Contact> tvListeContact;

    @FXML
    private TableColumn<Contact, String> columnNom;
    
    @FXML
    private TableColumn<Contact, String> columnPrenom;
    
    @FXML
    private TableColumn<Object,Boolean> columnSelection;
    
    @FXML
    private TableColumn<Contact,Boolean> columnFavoris;
    
    
    @FXML
    private Button btnSupprimerSelection;

    @FXML
    private Button btnAffichageFavoris;

    @FXML
    private Button btnGestionGroupe;

    @FXML
    private Button btnAjouterContact;

    @FXML
    private Button btnAfficherTousContacts;
    
    @FXML
    private TextField textRechercheContact;
    
    @FXML
    private Button btnRechercherContact;
    
    @FXML
    private ChoiceBox<String> cbGroupe;
    
    @FXML
    private Button btnImport;

    
    /********************* fonctions **********************/

    @Override
	public void initialize(URL url, ResourceBundle rb) {
		columnPrenom.setCellValueFactory(new PropertyValueFactory<>(PRENOM));
    	columnNom.setCellValueFactory(new PropertyValueFactory<>(NOM));
    	columnFavoris.setCellValueFactory(new PropertyValueFactory<>(FAVORIS));
    	columnSelection.setCellValueFactory(new PropertyValueFactory<>(SELECTION));
    	contacts.clear();
    	try {
			contacts.addAll(service.trouverToutContact());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	creerColonnes();
    	//fixColumnsWidth();
    	
    	
    	
    	cbGroupe.getItems().clear();
		try {
			groupe = new ArrayList<Groupe> (service.trouverToutGroupe());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> nomGroupe = new ArrayList<String>();
		for (Groupe g : groupe){
//			if (g.getIdGroupe()!= 0)
			nomGroupe.add(g.getNom());
		}
		cbGroupe.getItems().addAll(nomGroupe);
	}
    
    @FXML
    void cbGroupe_onClick(ActionEvent event) throws NumberFormatException, Exception{
    	contacts.clear();
    	contacts.addAll(service.trouverTousContactsGroupe(cbGroupe.getValue()));
    	creerColonnes();
    }
    
    
    
    void creerColonnes(){
    	tvListeContact.setItems(contacts);
    	ColonnePrenom();
    	ColonneNom();
    	ColonneFavoris();
    	ColonneSelection();
    }
    
    public void ColonnePrenom() {
    	columnPrenom.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnPrenom.setOnEditCommit((CellEditEvent<Contact,String>cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setPrenom(cell.getNewValue());
        });
    }
    public void ColonneFavoris() {
    	columnFavoris.setCellFactory(CheckBoxTableCell.forTableColumn(columnFavoris));
    	columnFavoris.setOnEditCommit((CellEditEvent<Contact, Boolean> cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setFavoris(cell.getNewValue());
        });
    }
    
    public void ColonneSelection() {
    	columnSelection.setCellFactory(CheckBoxTableCell.forTableColumn(columnSelection));
    }

    public void ColonneNom() {
    	columnNom.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnNom.setOnEditCommit((CellEditEvent<Contact, String> cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setNom(cell.getNewValue());
        });
    }
    
//    private void fixColumnsWidth() {
//    	columnNom.prefWidthProperty().bind(tvListeContact.widthProperty().subtract(columnPrenom.getWidth()));
//    }
    
    
    @FXML
    void btnAffichageFavoris_onAction(ActionEvent event) {
    	contacts.clear();
    	try {
			contacts.addAll(service.trouverToutFavoris());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	creerColonnes();
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
    void btnAfficherTousContacts_onChange(ActionEvent event) throws IOException {
    	contacts.clear();
    	try {
			contacts.addAll(service.trouverToutContact());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	creerColonnes();
    }


    @FXML
    void btnRechercherContact_onAction(ActionEvent event) {
    	if (!textRechercheContact.getText().equals(null) && !textRechercheContact.getText().equals("")){
    			contacts.clear();
    	    	try {
    				contacts.addAll(service.rechercheContactNom(textRechercheContact.getText()));
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    	if (contacts.isEmpty()){
    	    		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Message d'information");
    	    		alert.setHeaderText("Aucun contact trouvé");
    	    		alert.showAndWait();
    	    	}
    	    	creerColonnes();
    	}else{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Message d'avertissement");
    		alert.setHeaderText("Aucun nom n'est entré pour le contact");
    		alert.showAndWait();
    	}
    }

    @FXML
    void btnSupprimerSelection_onAction(ActionEvent event) {

    }
    
    
    @FXML
    public void tvListeContact_onClick(MouseEvent event) throws IOException {
            if (event.getClickCount() == 2) {
            	contact = new Contact(tvListeContact.getSelectionModel().getSelectedItem());
            	System.out.println(contact.toString());
            	System.out.println("controller ok");
            	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("detailContact.fxml"));
            	Scene pageAjoutScene= new Scene(pageAjoutParent);
            	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
            	app_stage.setScene(pageAjoutScene); 
            	app_stage.show();
            }
        }
    
    @FXML
    public void btnImport_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("import.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }
    
}