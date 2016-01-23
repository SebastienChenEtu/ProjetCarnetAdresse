package service;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import modele.Contact;
import modele.DAO;
import modele.Database;
import modele.Groupe;
import modele.Telephone;

@Service
public class ServiceCarnetAdresse {
	Database db;
	DAO dao;
	// Ne serait-ce pas plus optimal de faire des requêtes SQL simples pour chaque update qu'on souhaite
	// plutôt qu'instancier un objet contact à chaque fois et utiliser une méthode "modifier" du DAO
	// (même si c'est moins "facile" ?) - Ou alors optimer la méthode "modifier" en soi directement
	// en faisant un update uniquement sur ce qui est différent entre contactAModifier et contactSouhaite

	// Je pense qu'il faudrait optimiser la méthode "Modifier" vu comme c'est parti.
	// Sinon le couple DAO/service a peu d'intérêt ?..


	public ServiceCarnetAdresse()
	{
		this.db = new Database("Database.db");
		this.db.connexion();
		this.dao = new DAO(db);
	}

	public Contact CreerContact(Contact contact) throws Exception
	{
		return this.dao.CreerContact(contact);
	}

	public Contact TrouverContact(int idContact) throws SQLException
	{
		return this.dao.TrouverContact(idContact);
	}

	public Groupe CreerGroupe(Groupe groupe) throws Exception
	{
		return this.dao.CreerGroupe(groupe);
	}

	public Groupe setNomGroupe(Groupe ancienGroupe, String nom) throws Exception
	{
		Groupe nouveauGroupe = new Groupe(ancienGroupe);
		nouveauGroupe.setNom(nom);
		return this.dao.ModifierGroupe(ancienGroupe, nouveauGroupe);
	}

	public Contact setNomContact(Contact ancienContact, String nom) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setNom(nom);
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}

	public Contact setPrenomContact(Contact ancienContact, String prenom) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setPrenom(prenom);
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}

	public Contact setFax(Contact ancienContact, String fax) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setFax(fax);
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}

	public Contact setFavoris (Contact ancienContact, boolean isFavoris) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setFavoris(isFavoris);
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}

	public Contact setDDN (Contact ancienContact, Date ddn) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setDdn(ddn);
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}

	// TODO
	public Contact setPhoto(Contact ancienContact, InputStream photo)
	{
		return null;
	}

	public Contact setGroupe(Contact ancienContact, Groupe groupe) throws Exception
	{
		Contact nouveauContact = new Contact(ancienContact);
		nouveauContact.setIdGroupe(groupe.getIdGroupe());
		return this.dao.ModifierContact(ancienContact, nouveauContact);
	}
}
