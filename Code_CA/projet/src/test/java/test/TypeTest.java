package test;

import static org.junit.Assert.*;

import org.junit.Test;

import modele.Type;
import service.ServiceCarnetAdresse;

public class TypeTest {

	@Test
	public void test() throws Exception {
		ServiceCarnetAdresse service = new ServiceCarnetAdresse();
		
		//test creation de type 
		Type type = new Type();
		type.setLibelleType("portable");
		type = service.CreerType(type);
		assertEquals("portable", type.getLibelleType());
		
	
		//test trouver type
		type = service.TrouverType(type.getLibelleType());
		assertNotEquals("nouveauLibType", type.getLibelleType());
		assertEquals("portable", type.getLibelleType());
		
		type = service.TrouverType(type.getIdType());
		assertNotEquals("nouveauLibType", type.getLibelleType());
		assertEquals("portable", type.getLibelleType());
		
		//test modifier type
		Type typeTest = new Type();
		typeTest.setLibelleType("Maison");
		type = service.ModifierType(type.getLibelleType(), typeTest);
		assertNotEquals("portable", type.getLibelleType());
		assertEquals("Maison", type.getLibelleType());
		
		//test supprimer type
		boolean verife = service.SupprimerType(type.getLibelleType());
		assertTrue(verife);
	}

}
