package service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import modele.Contact;
import modele.Groupe;

@Service
public class ServiceCarnetAdresse {

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
}
