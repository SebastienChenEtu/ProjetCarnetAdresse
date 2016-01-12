#**Cahier de recette**

##Scénarios à recetter et valider

#### Lancement de l’application
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Lancer l’application     |  | L’application s’exécute|


#### Création de notes 
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Créer une note sans thème    | Titre, contenu  | La note apparaît dans la liste des notes |
| Créer une note avec une importance (étoile cochée) et sans thème   | Titre, contenu, étoile cochée    |La note apparaît dans la liste des notes « Importantes » |
| Créer une note avec thème     | Titre, contenu, thème | La note apparaît dans le dossier correspondant à son thème |
| Créer une note avec une importance (étoile cochée) et avec thème     | Titre, contenu, thème, étoile cochée | La note apparaît dans la liste des notes « Importantes » et dans le dossier correspondant à son thème|

#### Enregistrement de notes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Enregistrer automatiquement une note     || La note est enregistrée automatiquement lors de la fermeture de l’application|

#### Suppression de notes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Supprimer une note     | | La note est supprimée de toutes les listes où elle apparaissait|

#### Consultation de notes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Consulter une note     |  | L’utilisateur peut consulter le contenu de la dernière version de sa note|

#### Modification de notes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Modifier une note    | Titre, contenu  | La modification du titre et du contenu est effectuée |
| Modifier l'importance d’une note  | Titre, importance, contenu  | Lors de la modification d’une note, celle-ci apparaît dans la liste des notes « Importantes » |
| Modifier le thème d’une note    | Titre, thème, contenu  | La note est archivée dans le dossier correspondant au nouveau thème |


#### Création de thèmes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Créer un thème     | Thème | Lors de la création d’un thème via l’interface graphique, ce dernier apparaît dans la liste des thèmes |


#### Tri des notes
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Tri et affichage par défaut par date de dernière modification     |  |Les notes sont affichées, par défaut, par date de dernière modification|
| Tri par thème    |  | Les notes sont ordonnées par ordre alphabétique des titres |
| Tri par importance    |  | Les notes importantes sont affichées en haut de la liste suivies des notes non importantes et sont toutes ordonnées par ordre alphabétique des titres|

#### Envoi de notifications
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Envoi d’une notification lors de sa connexion     |  | Une notification des notes « Importantes » du jour sont envoyées à l'utilisateur|
| Envoi d’une notification par mail     |  | L’application envoie un mail chaque jour selon l'heure configurée par l'utilisateur lui informant les notes « Importantes » de la liste|

#### Changement des couleurs de fond
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Changer les couleurs de fond des notes     |  | Les couleurs de fond des notes sont modifiées|


#### Grisage des notes cochées
| Démarches        | Données          | Résultats  |
| :-------------:    |:-------------:   | :-----:|
| Griser et barrer les notes cochées  |  |Dans le cas d’une liste de cases à cocher, lorsque l’utilisateur cochera une case, celle-ci grisera la case et le texte et le barrera |
| Dégriser les check-box de la note    |  |Lorsque l’utilisateur décoche une case grisée, celle-ci laissera apparaître le texte|


