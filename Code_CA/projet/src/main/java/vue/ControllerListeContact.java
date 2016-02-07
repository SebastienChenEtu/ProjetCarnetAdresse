package vue;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import modele.Contact;

public class ControllerListeContact implements Initializable  {
	
	 public static Contact contact;
	
	 private static final String PRENOM = "prenom";
	 private static final String NOM = "nom";
	 
	 private static ObservableList<Contact> contacts;
	 


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRechercherContact;
    
    @FXML
    private  TableView<Contact> tvListeContact;

    @FXML
    private TableColumn<Contact, String> columnNom;
    
    @FXML
    private TableColumn<Contact, String> columnPrenom;
    
    @FXML
    private TableColumn<Contact,Boolean> columnSelection;
    
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
    private Button btnModifierContact;

    @Override
	public void initialize(URL url, ResourceBundle rb) {
		columnPrenom.setCellValueFactory(new PropertyValueFactory<>(PRENOM));
    	columnNom.setCellValueFactory(new PropertyValueFactory<>(NOM));
    	contacts = FXCollections.observableArrayList();
    	tvListeContact.setItems(contacts);
    	ColonnePrenom();
    	ColonneNom();
		
	}
    
    
    public void ColonnePrenom() {
    	columnPrenom.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnPrenom.setOnEditCommit((CellEditEvent<Contact,String>cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setPrenom(cell.getNewValue());
        });
    }
//    public void buildColumn_NoteItemState() {
//        stateCol.setCellFactory(CheckBoxTableCell.forTableColumn(stateCol));
//        stateCol.setOnEditCommit((CellEditEvent<NoteItem, Boolean> cell) -> {
//            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setDone(cell.getNewValue());
//        });
//    }

    public void ColonneNom() {
    	columnNom.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnNom.setOnEditCommit((CellEditEvent<Contact, String> cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setNom(cell.getNewValue());
        });
    }
    
    private void fixColumnsWidth() {
    	columnNom.prefWidthProperty().bind(tvListeContact.widthProperty().subtract(columnPrenom.getWidth()));
    }
    
    
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