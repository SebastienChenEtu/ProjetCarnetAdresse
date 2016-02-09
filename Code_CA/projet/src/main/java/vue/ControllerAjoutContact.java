package vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modele.Adresse;
import modele.Contact;
import modele.Groupe;
import modele.Mail;
import modele.Telephone;
import modele.DAO;
import service.ServiceCarnetAdresse;

public class ControllerAjoutContact {

	ServiceCarnetAdresse service = new ServiceCarnetAdresse();

	FileInputStream	fileInputStream = null;

	//	private static List<Mail>mails = new ArrayList<Mail>();
	//	private static List<Telephone>telephones = new ArrayList<Telephone>();
	//	private static List<Adresse>adresses = new ArrayList<Adresse>();

	private static ObservableList<Adresse> adresses=FXCollections.observableArrayList();
	private static ObservableList<Telephone> telephones=FXCollections.observableArrayList();
	private static ObservableList<Mail> mails=FXCollections.observableArrayList();

	private static final String ADRESSE = "adresse";
	private static final String TYPE = "type";
	private static final String TELEPHONE = "telephone";
	private static final String MAIL = "mail";
	private static boolean ajoutModif;

	private static String  nom="";
	private static String prenom="";
	private static String fax="";

	File monImage = new File(".\\Profil_par_defaut.jpeg");

	public boolean getAjoutModif(){
		return ajoutModif;
	}

	public void setAjoutModif(Boolean b){
		ajoutModif=b;
	}

	public void addTelephones(Telephone t){
		telephones.add(t);
	}
	public void addMails(Mail m){
		mails.add(m);
	}
	public void addAdresses(Adresse a){
		adresses.add(a);
	}

	ArrayList<Groupe> groupe;


	@FXML
	private TextField textNom;

	@FXML
	private TextField textPrenom;

	@FXML
	private ImageView imgAvatar;

	@FXML
	private ChoiceBox<String> cbGroupe;

	@FXML
	private TextField textFax;

	@FXML
	private Button btnValide;

	@FXML
	private Button btnAnnuler;

	@FXML
	private Button btnAvatar;


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
	void initialize() throws SQLException{
		textNom.setText(nom);
		if(nom.equals(null) || nom.equals("")){
			modifVisibilite(false);
		}
		textPrenom.setText(prenom);
		textFax.setText(fax);
		ajoutModif = true;
		cbGroupe.getItems().clear();
		groupe = new ArrayList<Groupe> (service.trouverToutGroupe());
		ArrayList<String> nomGroupe = new ArrayList<String>();
		for (Groupe g : groupe){
			nomGroupe.add(g.getNom());
		}
		cbGroupe.getItems().addAll(nomGroupe);
		cbGroupe.setValue(service.TrouverGroupe(0).getNom());


		columnTel.setCellValueFactory(new PropertyValueFactory<>(TELEPHONE));
		columnAdresse.setCellValueFactory(new PropertyValueFactory<>(ADRESSE));
		columnMail.setCellValueFactory(new PropertyValueFactory<>(MAIL));
		creerAdresse();
		creerTel();
		creerMail();
		//imgAvatar.setImage(monImage);
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

	void modifVisibilite(Boolean b){
		textFax.setVisible(b);
		btnValide.setVisible(b);
		textPrenom.setVisible(b);
		cbGroupe.setVisible(b);
		btnAjoutAdresse.setVisible(b);
		btnSupprimerAdresse.setVisible(b);
		btnAjoutTel.setVisible(b);
		btnSupprimerTel.setVisible(b);
		btnAjoutMail.setVisible(b);
		btnSupprimerMail.setVisible(b);
	}

	@FXML
	void textNom_onKeyReleased(KeyEvent event){
		if (!textNom.getText().equals(null)){
			if (!textNom.getText().equals("")){
				modifVisibilite(true);
			}else
				modifVisibilite(false);
		}else
			modifVisibilite(false);
		nom = textNom.getText();
	}

	@FXML
	void textPrenom_onKeyReleased(KeyEvent event){
		prenom = textPrenom.getText();
	}

	@FXML
	void textFax_onKeyReleased(KeyEvent event){
		fax = textFax.getText();
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

	void ajouterContact() throws Exception{
		ajoutModif = false;
		Contact c = new Contact();
		String nomContact = !textNom.getText().equals("") ? textNom.getText() : "";
		String prenomContact = !textPrenom.getText().equals("") ? textPrenom.getText() : "";

		Groupe g = service.TrouverGroupe(cbGroupe.getValue());
		//int idGroupe = 0; // en attendant
		String fax = !textFax.getText().equals("") ? textFax.getText() : "";

		//		List<Adresse> adrPourC =  new LinkedList<Adresse>();
		//		List<Mail> mailsPourC = new LinkedList<Mail>();
		//		List<Telephone> telsPourC = new LinkedList<Telephone>();

		FileInputStream inputStream = new FileInputStream(monImage);

		if(this.fileInputStream != null){
			inputStream = this.fileInputStream;
		}


		Contact contactACreer = new Contact(nomContact,prenomContact,new java.sql.Date(new Date().getTime()),fax,g.getIdGroupe(),inputStream, false);

		//		adrPourC.add(new Adresse("adresse C", 1));
		//		adrPourC.add(new Adresse("Adresse D", 1));
		//		mailsPourC.add(new Mail("mail C", 1));
		//		mailsPourC.add(new Mail("Mail D", 1));
		//		telsPourC.add(new Telephone("Tel C", 1));
		//		telsPourC.add(new Telephone("Tel D", 1));
		contactACreer.setAdresses(adresses);
		contactACreer.setMails(mails);
		contactACreer.setTelephones(telephones);

		service.CreerContact(contactACreer);
		mails.clear();
		telephones.clear();
		adresses.clear();
	}

	@FXML
	void btnAnnuler_onAction(ActionEvent event) throws IOException {
		ajoutModif = false;
		mails.clear();
		telephones.clear();
		adresses.clear();
		Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("listeContact.fxml"));
		Scene pageAjoutScene= new Scene(pageAjoutParent);
		Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(pageAjoutScene);
		app_stage.show();
	}

	@FXML
	void btnAvatar_onAction(ActionEvent event)throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println(selectedFile.getName());
			this.fileInputStream = new FileInputStream(selectedFile);
		}
		// Il faudra vérifier le format (jpg, png...), la taille (pas trop lourde)
		// Puis rendre l'image visible dans un petit cadre ?
		// Mettre également un avatar par défaut
	}

	@FXML
	void btnValide_onAction(ActionEvent event) throws Exception {
		ajouterContact();
		Parent pageAjoutParent = FXMLLoader.load(getClass().getResource("ListeContact.fxml"));
		Scene pageAjoutScene= new Scene(pageAjoutParent);
		Stage app_stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		app_stage.setScene(pageAjoutScene);
		app_stage.show();
	}

	@FXML
	void btnSupprimerAdresse_onAction(ActionEvent event) throws Exception {
		adresses.remove(tvAdresses.getSelectionModel().getSelectedItem());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("L'adresse sélectionné a été supprimé");
		alert.showAndWait();
	}

	@FXML
	void btnSupprimerMail_onAction(ActionEvent event) throws Exception {
		mails.remove(tvMail.getSelectionModel().getSelectedItem());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le mail sélectionné a été supprimé");
		alert.showAndWait();
	}

	@FXML
	void btnSupprimerTel_onAction(ActionEvent event) throws Exception {
		telephones.remove(tvTel.getSelectionModel().getSelectedItem());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message d'information");
		alert.setHeaderText("Le téléphone sélectionné a été supprimé");
		alert.showAndWait();
	}

}

