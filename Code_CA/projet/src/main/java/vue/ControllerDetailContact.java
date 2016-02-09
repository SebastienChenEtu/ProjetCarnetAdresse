package vue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modele.Adresse;
import modele.Contact;
import modele.Groupe;
import modele.Mail;
import modele.Telephone;
import modele.Type;
import service.ServiceCarnetAdresse;

public class ControllerDetailContact{

	private ControllerListeContact controller=new ControllerListeContact();

	private ServiceCarnetAdresse service = new ServiceCarnetAdresse();

	private static Contact c = new Contact();

	public Contact getContact(){
		 return c;
	 }

	private static final String ADRESSE = "adresse";
	 private static final String TYPE = "type";
	 private static final String TELEPHONE = "telephone";
	 private static final String MAIL = "mail";

	 private static ObservableList<Adresse> adresses=FXCollections.observableArrayList();
	 private static ObservableList<Telephone> telephones=FXCollections.observableArrayList();
	 private static ObservableList<Mail> mails=FXCollections.observableArrayList();

	/*********************** Attributs ****************************/

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
    private TableView<Adresse> tvAdresses;

    @FXML
    private TableColumn<Adresse, String> columnAdresse;

    @FXML
    private TableView<Telephone> tvTel;

    @FXML
    private TableColumn<Telephone, String> columnTel;

    @FXML
    private TableView<Mail> tvMail;

    @FXML
    private TableColumn<Mail, String> columnMail;

    @FXML
    private Button btnAjoutAdresse;

    @FXML
    private Button btnSupprimerAdresse;

    @FXML
    private Button btnAjoutTel;

    @FXML
    private Button btnSupprimerTel;

    @FXML
    private Button btnAjoutMail;

    @FXML
    private Button btnSupprimerMail;

    @FXML
	private ImageView imgAvatar;


    /*********************** fonctions ************************/

    @FXML
    void btnModifier_onAction(ActionEvent event) {
    	TextField t = new TextField();
    	t.setText("ok");
    }

    @FXML
    void initialize() throws SQLException, IOException{
    	c = controller.getContact();
    	textNom.setText(c.getNom());
    	textPrenom.setText(c.getPrenom());
    	textFax.setText(c.getFax());
    	Groupe g = new Groupe();
    	g = service.TrouverGroupe(c.getIdGroupe());
    	textGroupe.setText(g.getNom());
    	columnTel.setCellValueFactory(new PropertyValueFactory<>(TELEPHONE));
    	columnAdresse.setCellValueFactory(new PropertyValueFactory<>(ADRESSE));
    	columnMail.setCellValueFactory(new PropertyValueFactory<>(MAIL));
    	adresses.clear();
    	adresses.addAll(c.getAdresses());
    	telephones.clear();
    	telephones.addAll(c.getTelephones());
    	mails.clear();
    	mails.addAll(c.getMails());
    	creerAdresse();
    	creerTel();
    	creerMail();

    	javafx.scene.image.Image image = new javafx.scene.image.Image(service.getPhotoContact(c));
    	imgAvatar.setImage(image);

    }

    private void creerAdresse(){
    	tvAdresses.setItems(adresses);
    	columnAdresse();
    }
    private void creerTel() {
    	tvTel.setItems(telephones);
    	columnTel();
	}

    private void creerMail(){
    	tvMail.setItems(mails);
    	columnMail();
    }

    public void columnMail() {
    	columnMail.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnMail.setOnEditCommit((CellEditEvent<Mail,String>cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setMail(cell.getNewValue());
        });
    }

    public void columnTel() {
    	columnTel.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnTel.setOnEditCommit((CellEditEvent<Telephone,String>cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setTelephone(cell.getNewValue());
        });
    }

    public void columnAdresse() {
    	columnAdresse.setCellFactory(TextFieldTableCell.forTableColumn());
    	columnAdresse.setOnEditCommit((CellEditEvent<Adresse,String>cell) -> {
            cell.getTableView().getItems().get(cell.getTablePosition().getRow()).setAdresse(cell.getNewValue());
        });
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
    void btnSupprimer_onAction(ActionEvent event) throws Exception {
    	service.SupprimerContact(c.getIdContact());
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le contact a bien été supprimé");
		alert.showAndWait();
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }


    @FXML
    void btnAjoutAdresse_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("ajoutAdresse.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

    @FXML
    void btnAjoutMail_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("ajoutMail.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

    @FXML
    void btnAjoutTel_onAction(ActionEvent event) throws IOException {
    	Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("ajoutTel.fxml"));
    	Scene pageAjoutScene= new Scene(pageAjoutParent);
    	Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
    	app_stage.setScene(pageAjoutScene);
    	app_stage.show();
    }

    @FXML
    void btnSupprimerAdresse_onAction(ActionEvent event) throws Exception {
    	adresses.remove(tvAdresses.getSelectionModel().getSelectedItem());
    	service.setAdresses(c.getIdContact(),adresses);
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("L'adresse sélectionné a été supprimé");
		alert.showAndWait();
    }

    @FXML
    void btnSupprimerMail_onAction(ActionEvent event) throws Exception {
    	mails.remove(tvMail.getSelectionModel().getSelectedItem());
    	service.setMails(c.getIdContact(),mails);
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le mail sélectionné a été supprimé");
		alert.showAndWait();
    }

    @FXML
    void btnSupprimerTel_onAction(ActionEvent event) throws Exception {
    	telephones.remove(tvTel.getSelectionModel().getSelectedItem());
    	service.setTelephones(c.getIdContact(),telephones);
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le téléphone sélectionné a été supprimé");
		alert.showAndWait();
    }

}

