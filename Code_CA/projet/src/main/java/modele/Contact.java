package modele;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

public class Contact {
	private int idContact;
	private String nom;
	private String prenom;
	private Date ddn;
	private String adresse;
	private String telephone;
	private String fax;
	private String mail;
	private Groupe idGroupe;
	private Blob photo;
	private Boolean favoris;
}
