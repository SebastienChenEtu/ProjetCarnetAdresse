package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * Cette classe fait l'interface avec la base de données.
 * @author CA Team
 * @versio 0.1
 */
public class Database
 
{
    private String      dbName;
    public  Connection  connexion;
    private Statement   requete;
     
    /**
     * Constructeur de la classe Database
     * @param dbName Le nom de la base de données
     */
    public Database (String dbName){
        // Charge le driver sqlite JDBC en utilisant le class loader actuel
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e1){
            System.err.println(e1.getMessage());
        }
        this.dbName     = dbName;
        this.connexion = null;
    }
     
    /**
     * Ouvre la base de données spécifiée
     * @return True si la connection à été réussie. False sinon.
     */
    public boolean connexion (){
        try{
            // Etabli la connection
            connexion = DriverManager.getConnection("jdbc:sqlite:"+this.dbName);
            // Déclare l'objet qui permet de faire les requêtes
            requete = connexion.createStatement();             
            requete.executeUpdate("PRAGMA synchronous = OFF;");
            requete.setQueryTimeout(30);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * Ferme la connection à la base de données
     * @return True si la connection a bien été fermée. False sinon.
     */
    public boolean deconnexion (){
        try{
            if(connexion != null)
                connexion.close();
             return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * Permet de faire une requête SQL
     * @param requete La requête SQL (avec un ";" à la fin)
     * @return Un ResultSet contenant le résultat de la requête
     */
    public ResultSet getResultatRequete (String requete){
        try{
            return this.requete.executeQuery(requete);
        }
        catch (SQLException e){
            e.printStackTrace();
        } 
        return null;
    }
 
    /**
     * Permet de modifier une entrée de la base de données.</br>
     * @param requete La requete SQL de modification
     */
    public void setValeur (String requete){
        try{
            this.requete.executeUpdate(requete);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}