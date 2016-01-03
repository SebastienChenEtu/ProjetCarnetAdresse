package modele;

public class TestConnexion {
	 
    public static void main(String[] args) {
        Database connexion = new Database("Database.db");
        connexion.connexion();
        connexion.deconnexion();
    }
 
}
