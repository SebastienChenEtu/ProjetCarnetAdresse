# Synthèse du projet

### Introduction

Dans le cadre du module Génie Logiciel, nous avons disposé de plusieurs semaines afin de mener à bien un projet.  
Pour ce projet, nous avions deux rôles à tenir, celui de MOA pour la Todo List et celui de MOE pour le Carnet d’adresses.

Notre rôle de MOA impliquait que l’on rédige un cahier des charges pour une équipe (Todo List).  
Dans notre rôle de MOE, nous devions développer une application de Carnet d’adresses en suivant des spécificités précises (transmises par une autre équipe).

### Présentation de l’équipe et méthodes de travail

Notre équipe se compose de six personnes au total et nous avons choisi de nous diviser en trois groupes pour se partager la charge de travail :

+ partie rédactionnelle : Noémie LEROY et Kevin NGO
+ partie développement back-end : Adrien BERTUZZI et Sébastien CHEN
+ partie développement front-end : David CLEMENT et Audelyne JEAN-CHARLES

Afin d’assurer la transmission d’informations entre tous les membres de l’équipe, nous avons utilisé les deux outils collaboratifs suivants : GitHub et Google Drive.

### Partie rédactionnelle

Premièrement, étant en charge de la rédaction de spécifications fonctionnelles de la Todo List, nous avons rédigé plusieurs documents tels que le cahier des charges et le cahier de recette.

En ce qui concerne le cahier des charges, nous avons défini ce que nous voulions comme Todo List et les spécifications désirées.  
La Todo List attendue est plutôt classique, nous lui avons ajouté un système de favoris, un système d’envoi de notifications par mail, un système de tri par thèmes et par importance.

Nous avons, également, réalisé un cahier de recette afin de pouvoir tester point par point toutes les caractéristiques de l’application finale. Ainsi, grâce à ce cahier de recette, nous pouvons dire si nos besoins ont bien été respectés.

Deuxièmement, pour notre projet de Carnet d’adresses, nous avons rédigé une spécification technique afin d’expliciter toutes les technologies utilisées et les diverses contraintes imposées.

Enfin, nous sommes restés disponibles pour répondre aux différentes questions du groupe en charge de la Todo List.

### Partie développement

Le projet de Carnet d’adresses consiste à développer une application permettant de répertorier les contacts de l’utilisateur.  
En plus de comprendre les fonctionnalités basiques d’un carnet d’adresses telles que le tri alphabétique, la création/modification/suppression mais encore la gestion de favoris, ce dernier doit permettre au consommateur de fusionner ses groupes ainsi que d’importer/exporter ses contacts sous le format .sql.

L’outil GitHub nous a permis de poser des questions à l’équipe qui nous a fourni le cahier des charges du projet afin de lever toutes ambiguïtés sur leur besoin.

#### Difficultés rencontrées, solutions trouvées

Nous avons rencontré diverses difficultés lors de l'élaboration du code auxquelles nous avons, également, trouvé des solutions telles que :
+ les photos, la modification a été mise de côté, les problèmes rencontrés ont été le stockage dans le base de données, pour des améliorations futures et il ne faudra pas stocker directement les photos dans la base de données mais les copier dans un dossier sous-jacent du répertoire et stocker le chemin correspondant aux photos dans la base de données
+ la DAO, les difficultés ont été de gérer la grande quantité des cas d'échange entre les requêtes SQL et le programme en Java, pour une évolution nous pouvons envisager une programmation ORM via Hibernate
+ le passage d'un controleur à un autre, la résolution a été d'utiliser un attribut statique et d'appeler l'un des controleurs dans l'autre
+ l'import/export dû à un conflit au niveau des thread, pour pallier ce problème l'idée est d'importer les données en premier et de quitter l'application pour les exporter

#### Tests unitaires  

Nous avons réalisé plusieurs tests unitaires afin de vérifier le fonctionnement des fonctions principales du code.  
À l'aide de ces tests, nous pouvons vérifier la qualité de notre code afin de s'assurer des résultats de nos attentes et de pouvoir le refactorer plus facilement dans le cadre d'un développement plus approfondi du programme.

#### Fonctionnalité bonus  

La fonctionnalité bonus qui nous a été donnée est de permettre à l'utilisateur de définir plusieurs téléphones, adresses et mails, en ajoutant un libellé sur chaque élément.

#### Points perfectibles, points à améliorer

Certaines fonctionnalités n'ont pas pu être développées :
+ La suppression de plusieurs contacts à la fois
+ L'affichage de la photo au moment de l'ajout d'un contact car l'interface n'est pas rafraîchit automatiquement mais elle est visble lors de la modification du contact
+ La photo est non modifiable, ni importable, ni exportable
+ L'ajout et la modification des types ne sont pas possibles, ils sont prédéfinis

### Retours personnels

Ce module était intéressant car il mélangeait deux domaines : le développement et la gestion de projet.  
Cela nous a permis de nous rendre compte de l’ampleur d’un projet dans sa totalité (code, tests, réalisation de documentations). En effet, la gestion du temps et la communication sont primordiales dans ce genre de projet.

Cela nous a également permis d'approfondir notre connaissance et prise en main de GitHub.  
Ce type d'outil, surtout en entreprise, est nécessaire lorsque plusieurs personnes sont amenées à développer sur un projet commun afin de partager leur évolution.  
Aussi, GitHub permet de voir l'historique des modifications de tous les documents (texte, code), ce qui est pratique.

De plus, nous avons appris à utiliser le markdown qui est simple à mettre en place et intuitif. Cela évite d'importer un document texte à chaque changement.

Enfin, nous avons assimiler les bases de JavaFX pour mettre en place les interfaces destinées à l'utilisation du Carnet d'adresses.
