# ToDo List
## Introduction
Une ToDo List est un outil permettant de gérer les tâches d’une activité personnelle ou professionnelle sous forme de notes. 
## Spécifications fonctionnelles


### Ouverture de l’application
La première fois que l’utilisateur lance l’application sur son ordinateur, il lui sera demandé obligatoirement un e-mail valide (prendre en compte le format d’un e-mail).  
À chaque connexion, l’utilisateur doit saisir un identifiant qui correspond à son adresse mail. Il n'y aura pas besoin de mot de passe.

### Créer des notes
Nous souhaitons qu’un utilisateur puisse créer une note comprenant obligatoirement un titre, un contenu (soit sous forme de texte, soit sous forme de liste de cases à cocher) et une importance (système d'étoile).  
Il aura, également, le choix d’ajouter un thème correspondant à une catégorie. À ce thème, il pourra associer une couleur.  
Une fois la note créée, elle sera ajoutée à la liste des notes de l’utilisateur.

### Enregistrer
Les sauvegardes des notes se font à la création/modification/suppression d'une note.  
Aussi, lorsque l’utilisateur quitte l’application, ses notes sont sauvegardées. Ainsi, à la réouverture de celle-ci, il aura de nouveau accès à ses notes.

### Supprimer une note
L’utilisateur pourra supprimer les notes de sa liste dès lors qu’il n’en aura plus aucune utilité.  
Un message de confirmation s'affichera lors d'une suppression de note.

### Consulter et modifier une note 
L’utilisateur doit pouvoir changer à tout moment le titre, le contenu, le degré d’importance et le thème d’une note ou simplement la regarder sans rien changer.  
Aussi, la conversion liste vers texte et inversement doit être possible.

### Créer un thème
L’utilisateur peut ajouter de nouveaux thèmes via l’interface graphique sans passer par la création d’une note.

### Supprimer un thème
L’utilisateur peut supprimer un thème. Lorsque l'utilisateur supprime un thème, toutes les notes associées à ce thème sont aussi supprimées.

### Supprimer toutes les notes
L'utilisateur peut choisir de supprimer toutes les notes de sa ToDo List.

### Trier les notes
L’application offrira la possibilité de trier et d’afficher les notes par thème, par date de dernière modification et par importance.  
L’affichage “par défaut” sera le tri par date de dernière modification. Le temps (heure/minutes/secondes) doit être compris dans la date de dernière modification.  
Pour les tris par thème et importance, les titres des notes seront rangés dans l’ordre alphabétique croissant ou décroissant.

### Notifications 
Tous les jours à 0h00, l’utilisateur reçoit un mail lui informant les tâches importantes inscrites dans sa ToDo List.  
De plus, lors de l’ouverture de l’application, l’utilisateur est informé de ses notes qui sont éditées “importantes”.

### Changer les couleurs de fond des thèmes
L’utilisateur peut changer le fond de ses thèmes avec six couleurs différentes (bleu, violet, jaune, vert, orange et rouge).  
Les notes appartenant à ce thème sont de la même couleur que celui-ci.

### Griser et barrer les notes cochées
Dans le cas d’une note se composant d’une “liste de cases à cocher”, l’utilisateur pourra, lors de l’ajout ou de la modification, griser et barrer le texte des cases qui sont cochées.
