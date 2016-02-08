package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import modele.Contact;
import modele.Groupe;
import service.ServiceCarnetAdresse;

public class ContactTest {

	@Test
	public void test() throws Exception {
		ServiceCarnetAdresse service = new ServiceCarnetAdresse();
       
    //Test de la création de contact
		File monImage = new File(".\\adrien.jpg");
		FileInputStream istreamImage = new FileInputStream(monImage);
		
        Contact c_exp = service.CreerContact(new Contact("tata","toto",new Date(1454844550), "123456", 2,istreamImage,false));
         assertEquals("tata", c_exp.getNom());
         assertEquals("toto",c_exp.getPrenom());
         assertEquals(2,c_exp.getIdGroupe());
         assertEquals(false,c_exp.getFavoris());
         assertNotNull(c_exp.getDdn());
         assertEquals("123456", c_exp.getFax());
         
    // test trouver contact
        Contact trouverContact = service.TrouverContact(c_exp.getIdContact());
        assertEquals("tata", trouverContact.getNom());
        assertEquals("toto",trouverContact.getPrenom());
        assertEquals(2,trouverContact.getIdGroupe());
        assertEquals(false,trouverContact.getFavoris());
        assertNotNull(trouverContact.getDdn());
        assertEquals("123456", trouverContact.getFax());
        
    // test Modifier contact update photo fou la merde 
        Contact modifierContact = new Contact(new Contact("bobo","boby",new Date(1454844550), "0123456789", 1,istreamImage,false));
        c_exp = service.ModifierContact(trouverContact.getIdContact(), modifierContact);
        assertEquals("bobo", c_exp.getNom());
        assertEquals("boby",c_exp.getPrenom());
        assertEquals(1,c_exp.getIdGroupe());
        assertEquals(false,c_exp.getFavoris());
        assertNotNull(c_exp.getDdn());
        assertEquals("0123456789", c_exp.getFax());
    
     // test supprimer contact	   
        boolean supprimeContact = service.SupprimerContact(c_exp.getIdContact());
        assertTrue(supprimeContact);
        supprimeContact = service.SupprimerContact(c_exp.getIdContact());
        assertFalse(supprimeContact);
        
     // test trouver tout contact   
        List<Contact> listeContact = new LinkedList<Contact>();
        listeContact = service.trouverToutContact();
        assertNotNull(listeContact);
        
     // test trouver tout favoris
        listeContact = service.trouverToutFavoris();
        assertNotNull(listeContact);
        
     // test trouver tout contact groupe
        Contact rechercheContact = service.CreerContact(new Contact("tata","toto",new Date(1454844550), "123456", 2,istreamImage,false));
        Groupe g = service.TrouverGroupe(rechercheContact.getIdGroupe());
        listeContact = service.trouverTousContactsGroupe(g.getNom());
        rechercheContact = listeContact.get(0);
        assertEquals("t", rechercheContact.getNom());
        rechercheContact = listeContact.get(1);
        assertNotEquals("test2", rechercheContact.getNom());
        assertEquals("tata", rechercheContact.getNom());
        
     // recherche contact par nom
        listeContact = service.rechercheContactNom(rechercheContact.getNom());
        assertEquals("tata", listeContact.get(0).getNom());
        
//		List<Telephone> listTel = new LinkedList<Telephone>();
//		Telephone tel = new Telephone();
//		tel.setIdType(idType);
	}

}
