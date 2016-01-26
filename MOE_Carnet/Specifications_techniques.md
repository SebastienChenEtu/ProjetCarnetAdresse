## Spécifications techniques

###Introduction

Le projet consiste à réaliser un carnet d’adresse afin que l’utilisateur puisse répertorier toutes les informations de ses contacts.  

Ce document expose les caractérisations techniques qui sont utilisées pour mener à bien ce projet.

###Technologies utilisées
 
Les technologies utilisées sont les suivantes :  

| Utilisation        | Technologie utilisée       |
| :-------------:    |:-------------:   |
| Code    | Java  |
| Tests unitaires    | Junit |
| Interfaces    | JavaFX Scene Builder  |
| Base de données    | SQLite  |
| Partage du code    | GitHub  |


###Contenu

Dans cette partie seront présentées les diverses classes créées pour le développement du projet ainsi que leur utilité.

Tout d'abord, les classes suivantes représentent les objets ainsi que leurs attributs respectifs :

**Adresse.java**
+ idAdresse
+ adresse

**Contact.java**
+ idContact
+ nom
+ prénom
+ date de naissance
+ liste d’adresses
+ liste des numéros de téléphones
+ fax
+ mails
+ idGroupe
+ photo
+ favoris

**Groupe.java**
+ idGroupe
+ nom
+ liste des contacts appartenant à ce groupe

**Mail.java**
+ idMail 
+ adresse e-mail 

**Telephone.java**
+ idTelephone
+ numéro de téléphone

Ensuite, la classe **Database.java** fait l’interface avec la base de données. On y retrouve les méthodes suivantes :

+ public boolean connexion ()
+ public boolean deconnexion ()
+ public ResultSet getResultatRequete (String requete)
+ public void setValeur (String requete)
+ public void creationTables()

Aussi, la classe **DAO.java** se compose des principales méthodes suivantes :

+ public Contact CreerContact(Contact contact)
+ public Contact ModifierContact(Contact contactAModifier, Contact contactSouhaite)
+ public boolean SupprimerContact(Contact contact)
+ public Contact TrouverContact(int id)
+ public List<Contact> rechercherContactNom(String nom)
+ public List<Contact> trouverToutFavoris()
+ public List<Contact> trouverToutContact()
+ public Groupe CreerGroupe(Groupe groupe)
+ public Groupe ModifierGroupe(Groupe groupeAModifier, Groupe groupeSouhaite)
+ public boolean SupprimerGroupe(Groupe groupe)
+ public Groupe TrouverGroupe(Groupe groupe)
+ public List<Groupe> trouverToutGroupe()

Pour finir, la classe **ServiceCarnetAdresse.java** offre toutes les fonctionnalités du Carnet d’adresses : 

+ public Contact CreerContact(Contact contact)
+ public Contact TrouverContact(int idContact)
+ public Groupe CreerGroupe(Groupe groupe)
+ public Groupe setNomGroupe(Groupe ancienGroupe, String nom)
+ public Contact setNomContact(Contact ancienContact, String nom)
+ public Contact setPrenomContact(Contact ancienContact, String prenom)
+ public Contact setFax(Contact ancienContact, String fax)
+ public Contact setFavoris (Contact ancienContact, boolean isFavoris)
+ public Contact setDDN (Contact ancienContact, Date ddn)
+ public Contact setPhoto(Contact ancienContact, InputStream photo)
+ public Contact setGroupe(Contact ancienContact, Groupe groupe)
+ public Groupe trieContactAsc(Groupe groupe)
+ public Groupe trieContactDesc(Groupe groupe)
+ public List<Contact> trouverToutContact()
+ public List<Groupe> trouverToutGroupe()
+ public List<Contact> trouverToutFavoris()
+ public List<Contact> rechercheContactNom(String nom)
+ public boolean SupprimerContact(Contact contact)
+ public boolean SupprimerGroupe(Groupe groupe)
+ public Groupe FusionnerGroupe(Groupe g1, Groupe g2, String nomGroupe)
