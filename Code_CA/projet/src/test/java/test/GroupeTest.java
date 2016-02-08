package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import modele.Contact;
import modele.Groupe;
import service.ServiceCarnetAdresse;

public class GroupeTest {

	@Test
	public void test() throws Exception {
		ServiceCarnetAdresse service = new ServiceCarnetAdresse();
	    
		//Test de la création de groupe
		Groupe groupeTest = new Groupe();
		groupeTest.setNom("Licence Miage");
		Groupe creationGroupe = service.CreerGroupe(groupeTest);
		assertEquals("Licence Miage", creationGroupe.getNom());
		
		//Test trouver groupe
		creationGroupe = service.TrouverGroupe(creationGroupe.getNom());
		assertEquals("Licence Miage", creationGroupe.getNom());
		
		//Test Modifier groupe
		Groupe groupeModifier = new Groupe();
		groupeModifier.setNom("Master Miage");
		creationGroupe = service.ModifierGroupe(creationGroupe.getNom(),groupeModifier);
		assertEquals("Master Miage", creationGroupe.getNom());
		
		//Test Supprimmer groupe
		boolean testGroupe = service.SupprimerGroupe(creationGroupe.getNom());
        assertTrue(testGroupe);
        testGroupe = service.SupprimerGroupe(creationGroupe.getNom());
        assertFalse(testGroupe);
        
        //test trouver tout groupe
        List<Groupe> listeGroupe = new LinkedList<Groupe>();
        listeGroupe = service.trouverToutGroupe();
        assertNotNull(listeGroupe);
	}

}