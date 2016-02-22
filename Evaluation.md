# Evaluation

Note globale : 15/20

> Partie rédactionnelle excellente mais implémentation du code plus lacunaire. Vous n'avez pas exploité les concepts du cours mais vous avez avez essayé d'utiliser Spring, sans succès visiblement, mais cet essai vous fait gagner un point à l'arrivée. N'ayez jamais peur d'essayer quelque chose. Groupe de présentation homogène sinon.

Noémie : 16/20

> Participation active sur les soutenances des autres groupes

Kevin : 15/20

Adrien : 15/20

Sébastien : 15/20

David : 16/20

> Participation active sur les soutenances des autres groupes

Audelyne : 15/20

## Démonstration

* Mauvaise gestion de la résolution de l'écran qui gâche un peu l'affichage
* Si vous voulez annoncer les technos utilisées, faites le avant la démonstration. Une démonstration ça déborde toujours en terme de temps
* Prenez le temps de vous présenter (l'équipe) et de captiver votre auditoire, c'est votre accroche

## Git

* Présence de fichiers de configuration Eclipse sur le repo
* Gestion du *.gitignore* un peu bancale : pourquoi ne pas avoir exclu le dossier `target` ? Le repo GitHub ne doit pas contenir de binaires, seulement du code. C'est le Nexus qui contient les binaires.
* Pourquoi avoir des jars de `lib` alors que vous utilisez Maven ?
* Travail réparti sur les lundi et mardi après-midi, et un peu le dimanche après-midi.
* Une *pull-request* et deux *issues* toujours ouvertes
* Utilisation des branches et commentaires variés. Attention quand vous committez depuis GitHub, changez le commentaire par défaut

## Code

* Utilisation de Maven
* Attention à l'encodage des fichiers
* Essai d'utilisation de Spring, intéressant
* Eviter les `SELECT *`, nommez vos colonnes
* Le DAO fait trop de choses. Pour créer un contact vous créez toutes ses dépendances, soit autant de DAO potentiels. Pensez au SRP
* Vous n'avez que deux *god-classes* : Service, un proxy vers DAO
* Vous ne devriez pas à avoir à rattraper des erreurs SQL dans la partie vue (Controller JavaFX), un service intermédiaire devrait absorber cette notion technique. Si vous stockez sur fichier ou sur base NoSQL, l'exception changera montrant ainsi que votre MVC n'est pas respecté

## Tests

* Eviter les "tata", "toto", "titi", ça décrédibilise votre code
* Vos tests unitaires ne le sont pas, ils testent plusieurs méthodes d'un coup (Créer/Trouver)
* L'IoC vous aurait éviter l'instanciation d'objet dans les tests et un framework de *mocking* d'avoir une telle dépendance dans vos méthodes
* Vous ne testez que les cas nominaux, pas les cas d'erreurs (e.g. Si la recherche ne trouve pas)
* Si vous mettez des commentaires pour séparer du code dans une méthode, faites plusieurs méthodes plutôt

## Documentation

* Format markdown maitrisé, l'utilisation de tableaux est plutôt rare et complexe, bien joué
* Expression de besoins et cahier de recettes complets sans être trop phrasés, c'est précis et concis
* Spécifications techniques qui ne rentrent pas trop dans les détails d'implémentation et expliquent bien la structure de code
* Assocation fonctionnalité - test rendue facile car les deux fichiers adoptent la même structure, rien à dire c'est bien !

## Rapport

* Bon rapport, bonne description du rôle de chaque personne
* Format Markdown conservé
* Concernant les difficultés ou les points perfectibles, vous ne parlez que du produit et pas de l'organisation ou des outils utilisés
* Sur les fonctionnalités trop peu élaborées (e.g. les photos), pourquoi n'y etes vous pas parvenu ? Par manque de temps ? A cause d'une implémentation bancale ? Par volonté de ce concentrer sur autre chose ?
* La partie "retour personnel" fait un peu *listing* des choses et manque de ressenti mais elle est là !
* Quels avis avez-vous sur l'utilisation de Maven ? De JavaFX ? de Markdown ? Ces outils nouveaux que vous avez découverts sur le projet