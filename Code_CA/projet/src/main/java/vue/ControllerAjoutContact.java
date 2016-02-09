package vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

	@FXML
	private TextField textNom;

	@FXML
	private TextField textPrenom;

	@FXML
	private ImageView avatar;

	@FXML
	private ChoiceBox<Groupe> cbGroupe;

	@FXML
	private TextField textAdresse;

	@FXML
	private TextField textFax;

	@FXML
	private TextField textEmail;

	@FXML
	private TextField textTelephone;


	@FXML
	private Button btnAjoutTelephone;


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
    void btnSupprimerAdresse_onAction(ActionEvent event) {

    }

    @FXML
    void btnSupprimerMail_onAction(ActionEvent event) {

    }

    @FXML
    void btnSupprimerTel_onAction(ActionEvent event) {

    }

	@FXML
	void initialize(){
		modifVisibilite(false);
		textPrenom.setText("");
//		cbGroupe.setValue(service.trouverGroupe(0);
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
	}

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
    void btnAjoutTel_onAction(ActionEvent event) {

    }

	void ajouterContact() throws Exception{
		Contact c = new Contact();
		c.setNom(textNom.getText());
		c.setPrenom(textPrenom.getText());
//		File monImage = new File(".\\adrien.jpg");
//		Contact c = new Contact("test","test",new java.sql.Date(new Date().getTime()),"fax",2,istreamImage, false);
//		c.setIdGroupe(cbGroupe.getValue().getIdGroupe());
//		c.setFax(textFax.getText());

		File monImage = new File(avatar.getImage().impl_getUrl());
		FileInputStream istreamImage = new FileInputStream(monImage);

		List<Adresse> adrPourC =  new LinkedList<Adresse>();
		List<Mail> mailsPourC = new LinkedList<Mail>();
		List<Telephone> telsPourC = new LinkedList<Telephone>();
		//photo différent de null
//		FileInputStream istreamImage = new FileInputStream(avatar.getImage().impl_getUrl());
//		c.setPhoto(istreamImage);


//		c.setPhoto(istreamImage);
		c.setIdGroupe(3);

		adrPourC.add(new Adresse("adresse C", 1));
		adrPourC.add(new Adresse("Adresse D", 1));
		mailsPourC.add(new Mail("mail C", 1));
		mailsPourC.add(new Mail("Mail D", 1));
		telsPourC.add(new Telephone("Tel C", 1));
		telsPourC.add(new Telephone("Tel D", 1));
		c.setAdresses(adrPourC);
		c.setMails(mailsPourC);
		c.setTelephones(telsPourC);
		System.out.println(c.toString());
		service.CreerContact(c);
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
	void btnAvatar_onAction(ActionEvent event)throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			System.out.println(selectedFile.getName());
			FileInputStream fileInputStream = new FileInputStream(selectedFile);
			// service.setPhoto(1, fileInputStream);
			service.ImporterFichier(selectedFile.getName());
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
	void textNom_onAction(ActionEvent event) {
		if (textNom.equals("") || textNom.equals(null)){
			textTelephone.disabledProperty();
		}
		else {
			textTelephone.editableProperty();
		}
	}


}

