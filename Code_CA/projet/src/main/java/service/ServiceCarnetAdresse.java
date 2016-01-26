package service;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import modele.Contact;
import modele.DAO;
import modele.Database;
import modele.Groupe;

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

	public Groupe setNomGroupe(String nomAncienGroupe, String nom) throws Exception
	{
		Groupe nouveauGroupe = this.dao.TrouverGroupe(nomAncienGroupe);
		nouveauGroupe.setNom(nom);
		return this.dao.ModifierGroupe(nomAncienGroupe, nouveauGroupe);
	}

	public Contact setNomContact(int idContactAModifier, String nom) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setNom(nom);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setPrenomContact(int idContactAModifier, String prenom) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setPrenom(prenom);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setFax(int idContactAModifier, String fax) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setFax(fax);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setFavoris (int idContactAModifier, boolean isFavoris) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setFavoris(isFavoris);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setDDN (int idContactAModifier, Date ddn) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setDdn(ddn);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	// TODO
	public Contact setPhoto(Contact ancienContact, InputStream photo)
	{
		return null;
	}

	public Contact setGroupe(int idContactAModifier, Groupe groupe) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setIdGroupe(groupe.getIdGroupe());
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Groupe trieContactAsc(Groupe groupe){
		List<Contact> trieAsc = new LinkedList<Contact>();
		trieAsc = groupe.getListeContacts();
		Collections.sort(trieAsc);
		groupe.setListeContacts(trieAsc);
		return groupe;
	}

	public Groupe trieContactDesc(Groupe groupe){
		Groupe groupeDesc = trieContactAsc(groupe);
		List<Contact> trieDesc = new LinkedList<Contact>();
		for(int i = groupeDesc.getListeContacts().size()-1; i>=0 ; i--){
			trieDesc.add(groupe.getListeContacts().get(i));
		}
		groupe.setListeContacts(trieDesc);
		return groupe;
	}

	public List<Contact> trouverToutContact() throws SQLException{
		return this.dao.trouverToutContact();
	}

	public List<Groupe> trouverToutGroupe() throws SQLException{
		return this.dao.trouverToutGroupe();
	}

	public List<Contact> trouverToutFavoris() throws SQLException{
		return this.dao.trouverToutFavoris();
	}

	public List<Contact> rechercheContactNom(String nom) throws SQLException{
		return this.dao.rechercherContactNom(nom);

	}
		public boolean SupprimerContact(int idContact) throws Exception
	{
		return this.dao.SupprimerContact(idContact);
	}

	public boolean SupprimerGroupe(String nomGroupe) throws Exception
	{
		return this.dao.SupprimerGroupe(nomGroupe);
	}

	// TODO
	// il faut récuperer la liste de contacte des groupes
	// il faut mettre à jour l'id des contacts sur le nouveau groupe crée
	public Groupe FusionnerGroupe(Groupe g1, Groupe g2, String nomGroupe) throws Exception
	{
		List<Contact> listeContacts = new LinkedList<Contact>();
		listeContacts.addAll(g1.getListeContacts());
		listeContacts.addAll(g2.getListeContacts());
//		this.dao.SupprimerGroupe(g1);
//		this.dao.SupprimerGroupe(g2);
		Groupe groupe = new Groupe();
		groupe.setListeContacts(listeContacts);
		groupe.setNom(nomGroupe);
		return this.dao.CreerGroupe(groupe);
	}
}
