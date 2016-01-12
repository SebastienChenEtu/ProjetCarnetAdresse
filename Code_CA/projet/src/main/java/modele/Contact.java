package modele;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Contact {
	// il faut le gÃ©nÃ©rer automatiquement
	private int idContact;
	private String nom;
	private String prenom;
	private Date ddn;
	private String adresse;
	private String telephone;
	private String fax;
	private String mail;
	private int idGroupe;
	private Blob photo;
	private Boolean favoris;

	public Contact(int idContact, String nom, String prenom, Date ddn, String adresse, String telephone, String fax,
			String mail, int idGroupe, Blob photo, Boolean favoris) {
		super();
		this.idContact = idContact;
		this.nom = nom;
		this.prenom = prenom;
		this.ddn = ddn;
		this.adresse = adresse;
		this.telephone = telephone;
		this.fax = fax;
		this.mail = mail;
		this.idGroupe = idGroupe;
		this.photo = photo;
		this.favoris = favoris;
	}
	public Contact()
	{

	}
	public int getIdContact() {
		return idContact;
	}
	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDdn() {
		return ddn;
	}
	public void setDdn(Date ddn) {
		this.ddn = ddn;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getIdGroupe() {
		return idGroupe;
	}
	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	public Boolean getFavoris() {
		return favoris;
	}
	public void setFavoris(Boolean favoris) {
		this.favoris = favoris;
	}

}


