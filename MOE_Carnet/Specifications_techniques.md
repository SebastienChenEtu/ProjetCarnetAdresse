## Spécifications techniques

###Introduction

Le projet consiste à réaliser un carnet d’adresse afin que l’utilisateur puisse répertorier toutes les informations de ses contacts.  

Ce document expose les caractérisations techniques qui sont utilisées pour mener à bien ce projet.

###Technologies utilisées
 
Les technologies utilisées sont les suivantes :  

| Utilisation        | Technologie utilisée | Commentaires|
| :-------------:    |:-------------:       |:-------------: |
| Code               | Java                 |Multi-plateforme au niveau du système d'exploitation|
| Tests unitaires    | Junit                |Framework de test pour Java |
| Interfaces         | JavaFX Scene Builder |Le plus adapté à notre projet|
| Base de données    | SQLite               |Léger et multi-plateforme |
| Partage du code    | GitHub               |Simple et pratique|


###Contenu

Dans cette partie seront présentées les diverses classes créées pour le développement du projet ainsi que leur utilité.

Tout d'abord, les classes suivantes représentent les objets ainsi que leurs attributs respectifs :
+ **Adresse.java** : idAdresse, adresse, idType
+ **Contact.java** : idContact, nom, prénom, ...
+ **Groupe.java** : idGroupe, nom, listeContacts
+ **Mail.java** : idMail, mail, idType
+ **Telephone.java** : idTelephone, telephone, idType
+ **Type.java** : idType, libelleType

Ensuite, les méthodes principales nécessaires au bon fonctionnement de l'application ont été développées sous différentes classes :
+ **Database.java** : Cette classe fait l'interface avec la base de données et se compose des méthodes correspondantes telles que connexion(), deconnexion() mais encore getResultatRequete(String requete).
+ **DAO.java** : Elle met en place les fonctionnalités de l'application demandées, par exemple les création/modification/suppression des contatcs et de groupes.
+ **Import_Export.java** : La classe permet l'importation et l'exportation des contacts sous le format .sql.
+ **ServiceCarnetAdresse.java** : Cette dernière classe recense les besoins utilisateurs.

Enfin, pour déployer les services de l'application, les classes suivantes ont été développées :
+ **AppliFx.java** : deploiement de l'interface via les controleurs cités ci-dessous
+ **ControllerAjoutAdresse.java**
+ **ControllerAjoutContact.java**
+ **ControllerAjoutMail.java**
+ **ControllerAjoutTel.java**
+ **ControllerDetailContact.java**
+ **ControllerGestionGroupe.java**
+ **ControllerImport.java** 
+ **ControllerListeContact.java**
