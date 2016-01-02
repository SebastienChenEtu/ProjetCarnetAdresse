package connexion;

public class TestConnexion {
	 
    public static void main(String[] args) {
        Connexion connexion = new Connexion("Database.db");
        connexion.connect();
        connexion.close();
    }
 
}
