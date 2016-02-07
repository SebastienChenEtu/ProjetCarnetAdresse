package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;

import org.junit.Test;

import modele.Contact;
import modele.DAO;
import modele.Database;
import service.ServiceCarnetAdresse;

public class ContactTest {

	@Test
	public void test() throws Exception {
		ServiceCarnetAdresse service = new ServiceCarnetAdresse();
       
    //Test de la création de contact
		File monImage = new File(".\\adrien.jpg");
		FileInputStream istreamImage = new FileInputStream(monImage);
//		List<Telephone> listTel = new LinkedList<Telephone>();
//		Telephone tel = new Telephone();
//		tel.setIdType(idType);
		
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
        
    // test Modifier contact
//        Contact modifierContact = new Contact(new Contact("bobo","boby",new Date(1454844550), "0123456789", 1,istreamImage,false));
//        c_exp = service.ModifierContact(trouverContact.getIdContact(), modifierContact);
//        assertEquals("bobo", c_exp.getNom());
//        assertEquals("boby",c_exp.getPrenom());
//        assertEquals(1,c_exp.getIdGroupe());
//        assertEquals(false,c_exp.getFavoris());
//        assertNotNull(c_exp.getDdn());
//        assertEquals("0123456789", c_exp.getFax());
       
	}

}
