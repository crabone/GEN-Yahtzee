# GEN-Yahtzee

## Itérations

### Itération n°2
#### But
Implémenter le protocole client-serveur. Cas d'utilisation: s'inscrire et s'authentifier sur un serveur
#### Dates
Du 28 avril 2016 au 5 mai 2016.
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Le client et le serveur communiquent selon le protocole mis en place;
* Le protocole contient toutes les intéractions entre le client et le serveur t.q. la création d'une partie et le déroulement d'une partie;
* Ce protocole sert de base pour implémenter la mécanique du jeu.
#### Démonstration
* Le client et le serveur s'échange des messages selon le protocole établi (Visualisation par Wireshark).
* Le client peut créer un compte
* Le client peut s'authentifier avec son compte
#### Partage du travail
* Fabien : Bilan d’itération 1, monitoring, interaction avec la base de données 
* Kevin : Mise à jour du rapport, base de données
* Ibrahim : Canevas, hash mot de passe
* Madolyne : Mise à jour des itérations, interface graphique
* Rosanne : Base de données, mise à jour des itérations, implémentation du protocole
#### Efforts
* Interface graphique : 3h30
* Création de la base de données : 1h00
* Interaction avec la base de données : 3h00
* Rapport et mise à jour des itérations : 4h00
* Implémentation du protocole : 6h00

### Itération n°3
#### But
Implémentation de la création d’une partie. Cas d’utilisation : créer une partie et rejoindre une partie
#### Dates
Du 5 mai 2016 au 12 mai 2016 (avec une réserve d'une semaine).
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Le client pourra créer une partie
* Le serveur crée la partie
* Le client peut rejoindre une partie créée
#### Démonstration
* Création d’une partie
* Rejoindre une partie créée
#### Partage du travail
* Fabien : Gestion des parties : programmation
* Kevin : Gestion des parties : programmation
* Ibrahim : Gestion des parties : communication avec la base de données
* Madolyne : Interface graphique
* Rosanne : Gestion des parties : programmation

#### Efforts
* Inscription + Authentification : 2h
* Protocole : 1h
* Interface graphique : 4h30
* Gestion des parties : 6h00
* Rapport : 1h

### Itération n°4
#### But
Implémentation du jeu et de ses règles. Cas d’utilisation : observer une partie et jouer(Variantes 1 à 3)
#### Dates
Du 12 mai 2016 au 19 mai 2016.
#### Phase
Implémentation et conception.
#### Fonctionnalités
Le client peut observer une partie
Le client peut jouer une partie
#### Démonstration
Démonstrations lors de l’itération 5
#### Partage du travail
* Fabien : Gestion des parties : implémentation de la mécanique du jeu.
* Kevin : Gestion des parties : implémentation de la mécanique du jeu.
* Ibrahim : Gestion des parties : communication avec la base de données.
* Madolyne : Interface graphique : plateau de jeu.
* Rosanne : Rédaction du bilan d’itération 3. Implémentation de la mécanique du jeu. 
#### Efforts
* Gestion partie: 15h
* Interface graphique: 6h

### Itération n°5
#### But
Implémentation du jeu et de ses règles. Cas d’utilisation :  Jouer (Echecs 1 et 2)
#### Dates
Du 19 mai 2016 au 26 mai 2016.
#### Phase
Implémenation et conception
#### Fonctionnalités
* Le client peut jouer et quitter une partie en cours.
#### Démonstration
Démonstrations du déroulement d’une partie
#### Partage du travail
* Fabien : 
* Kevin : 
* Ibrahim : 
* Madolyne : 
* Rosanne :
#### Efforts

### Itération n°6
#### But
Implémentation des droits spécifiques à l’administrateur. Cas d’utilisation : Administrer les joueurs
#### Dates
Du 26 mai 2016 au 2 juin 2016.
#### Phase
Implémenation et conception
#### Fonctionnalités
* Suppression d’un compte
* Ban d’un joueur
#### Démonstration
#### Partage du travail
* Fabien : 
* Kevin : 
* Ibrahim : 
* Madolyne : 
* Rosanne :
#### Efforts

### Itération n°7
#### But
Dernières retouches et modification, derniers tests
#### Dates
Du 2 juin 2016 au 9 juin 2016.
#### Phase
Implémenation et conception
#### Fonctionnalités
#### Démonstration
#### Partage du travail
* Fabien : 
* Kevin : 
* Ibrahim : 
* Madolyne : 
* Rosanne :
#### Efforts


## Fonctionnement général de l'applicatif

### Objectifs de base
Dans le cadre du cours de Génie Logiciel (GEN), nous avons pour projet, de concevoir une application client-serveur en adoptant la méthodologie de travail, Unified Process (UP). Nous avons choisis de réaliser une version digitale du Yahtzee, célèbre jeu de société.

### Utilisation de l'applicatif
Le client est accueilli sur une fenêtre d'authentification où il a la possibilité de spécifier l'adresse d'un serveur distant ainsi que ses identifiants, pour s'y connecter. Ensuite, il est redirigé sur la fenêtre principale du serveur. Pour jouer, il peut rejoindre une partie en attente de joueurs ou, en créer une nouvelle. Le cas écheant il doit spécifier le nombre de participants (2 au minimum) et leur temps à disposition pour chaque tours.

Le client a la possibilité de modifier son profil (mot de passe, avatar, description). Le serveur quant à lui dispose d'outils d'administrations permettant, d'ajouter, de modifier ou de supprimer un joueur. Il peut aussi configurer le moteur de jeu et ainsi permettre, par exemple, la création de partie composée de N joueurs.

L'interface de jeu permet d'effectuer les actions permises, de visualiser sa progression (et celle de ses adversaires) et d'obtenir un résumé des combinaison gagnante. Il est prévu aussi de mettre à disposition un système permettant de communiquer lors d'une partie. Le joueur à la possibilité d'observer une partie et visualiser les classements si il ne désire pas jouer.

Lorsque qu'une partie débute, le serveur choisis aléatoirement le joueur qui commencera. A terme, le serveur enregistre les scores des différents joueurs et dresse un classement.

### Règles du jeu
Le Yahtzee se joue avec 5 dés et se finit une fois toutes les cases de la fiche de score remplies. Chaque joueur joue tout à tour et dispose de 3 lancers à chaque coup. L’objectif étant de réaliser des combinaisons qui rapportent des points. Le joueur a le choix de reprendre tous ou une partie des dés à chaque lancé, selon son gré, pour tenter d’obtenir la combinaison voulue. A chaque tour, le joueur doit obligatoirement inscrire les points qu’il a obtenu dans la fiche de score.

Le premier joueur lance les dés et choisit de les garder tous, de les rejouer tous ou rejouer seulement certains dés afin d’obtenir l’une des combinaisons. 

Après le dernier jet de dé, il doit remplir la fiche de score. S’il n’obtient aucune combinaison ou n’est pas satisfait du score obtenu et pense pouvoir faire mieux, il a la possibilité d’inscrire 0 point dans la case de son choix, sachant qu’il ne pourra plus refaire cette combinaison par la suite. 
Le gagnant est celui qui obtient le plus de points. 
Pour compter les points, se référer à la fiche de score.

Lorsque le total intermédiaire est égal ou supérieur à 63 points, un bonus de 35 points supplémentaires est accordé, ce qui peut faire la différence au décompte final. Il faut donc être stratégique. 

### Contraintes
* Un joueur ne peut réjoindre une partie en cours (y compris dans le cas où il s'y est déconnecté involontairement);
* Une partie ne peut être composé de plus de 6 joueurs;
* Le protocole client-serveur n'est pas chiffré.

## Responsabilités client et serveur
### Serveur
* Gestion de la base de données
* Instancier les parties
* Dresser les classements
* Mise en relation des joueurs
* Logique du jeu
* Configuration du serveur
* Valider authentifications utilisateurs

### Client
* Authentification auprès du serveur
* Mise-à-jours des vues

## Cas d'utilisation
### S'inscrire sur un serveur
#### Scénario principal (succès)
1. Le joueur se rend sur le menu principal
2. Le joueur se rend sur le menu "S'inscrire..."
3. Le joueur enregistre l'adresse IP du serveur, un nom d'utilisateur et un mot-de-passe
4. Le joueur confirme l'inscription

#### Autres scénarios (échecs, variantes)
##### Opération 3: Le serveur est introuvable
1. Le joueur est notifié
2. Le joueur corrige l'adresse IP

##### Opération 3: Le nom d'utilisateur existe déjà
1. Le joueur est notifié
2. Le joueur enregistre un nom d'utilisateur différent

##### Opération 4: Le joueur annule la procédure
1. Le joueur abandonne l'enregistrement

### S'authentifier sur un serveur
#### Scénario principal (succès)
1. Le joueur se rend sur le menu principal
2. Le joueur spécifie le serveur auquel il veut se connecter
3. Le joueur spécifie son nom d'utilisateur et le mot-de-passe associé
4. Le joueur confirme la connexion

#### Autres scénarios (échecs, variantes)
##### Opération 2: Le serveur est introuvable
1. Le joueur est notifié
2. Le joueur corrige l'adresse IP du serveur

##### Opération 2: Le serveur ne répond pas
1. Le joueur est notifié
2. Le joueur renouvelle sa demande plus tard

#### Opération 3: Le mot-de-passe est incorrect
1. Le joueur est notifié
2. Le joueur corrige son mot-de-passe

### Créer une partie
#### Scénario Principal (succès)
1. Le joueur se rend dans l'espace prévu à cet effet
2. Le joueur spécifie le nombre de joueurs admissible
3. La partie est mise en attente tant qu'il n'y a pas assez de joueur

### Rejoindre une partie
#### Scénario Principal (succès)
1. Le joueur se rend sur la liste des parties en attente
2. Le joueur notifie au serveur quelle partie il veut rejoindre

### Observer une partie
#### Scénario Principal (succès)
1. Le joueur se rend sur la liste de toute les parties
2. Le joueur notifie au serveur quelle partie il veut observer

### Administrer les utilisateurs du serveur
#### Scénario Principal (succès)
1. L'administrateur se rend sur la fenêtre de configuration du serveur
2. L'administrateur seléctionne un utilisateur du serveur
3. L'administrateur accompli l'action souhaitée (Suppression, ban, ...)

### Quitter le serveur
#### Scénario Principal (succès)
1. Le joueur quitte le serveur avec le dispositif prévu à cet effet
2. Le joueur est redirigé sur l'écran d'accueil

## Protocole client-serveur
### Authentification
![Authentification](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/diagrams/authentificationClient.png)

### Rejoindre/Observer une partie
![Rejoindre, Observer](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/diagrams/DemanderObserverJoindre.png)

### Modification du profil
![Modification du profil](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/diagrams/ComptePersonnel.png)

### Classement des joueurs
![Classement](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/diagrams/listeClassementPartie.png)

### Jouer une partie
![Jouer](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/diagrams/LanceDes.png)

## Ebauche UI
### Fenêtre d'authentification
![Fenêtre d'authentification](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/ui/Authentification.bmp)

### Fenêtre principale
![Fenêtre principale](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/ui/accueil.bmp)

## Base de données
![Base de données](https://github.com/crabone/GEN-Yahtzee/blob/master/figures/BDD.bmp)

## Plan d'itération
### Itération n°1
#### But
Développer une architecture (minimale) d'une application client-serveur, permettant l'échange de messages entre plusieurs clients et un serveur.
#### Dates
Du 21 avril 2016 jusqu'au 28 avril 2016.
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Serveur pouvant gérer plusieurs clients simultanément;
* Connexion d'un ou plusieurs clients sur le serveur;
* Communication textuelle entre le client et le serveur;
* Gestion propre des états client (Connexion, déconnexion);
* Monitoring de la part du serveur, des clients associés.

#### Démonstration
* Des clients doivent pouvoir se connecter au serveur;
* Visualisation des échanges échanges entre les clients et le serveur (Par Wireshark).

#### Rôles
Fabien: Responsable normes et procédures
Kevin: Programmeur
Ibrahim: Analyste
Madolyne: Responsable documentation 
Rosanne: Responsable des tests

### Itération n°2
#### But
Implémenter le protocole client-serveur.
#### Dates
Du 28 avril 2016 au 5 mai 2016.
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Le client et le serveur communique selon le protocole mis en place;
* Le protocole contient toutes les intéractions entre le client et le serveur t.q. la création d'une partie et le déroulement d'une partie;
* Ce protocole sert de base pour implémenter la mécanique du jeu.

#### Démonstration
* Le client et le serveur s'échange des messages selon le protocole établi (Visualisation par Wireshark).

### Itération n°3
#### But
Implémentation du moteur et de la mécanique du jeu.
#### Dates
Du 5 mai 2016 au 12 mai 2016 (avec une réserve d'une semaine).
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Plusieurs clients pourront jouer des parties;
* Le serveur peut accepter plusieurs parties simultanées.

#### Démonstration
* Une partie (en ligne de commande) entre 3 joueurs.

### Itération n°4
#### But
Implémentation de la gestion des parties et du classement.
#### Dates
Du 12 mai 2016 au 19 mai 2016.
#### Phase
Implémentation et conception.
#### Fonctionnalités
* Le client peut créer une partie;
* Le client peut rejoindre une partie;
* Le client peut observer une partie.

#### Démonstration
* Démonstrations des 3 fonctionnalités.

### Itération n°5
#### But
Implémentation de l'interface graphique
#### Dates
Du 19 mai 2016 au 2 juin 2016.
#### Phase
Implémenation et conception
#### Fonctionnalités
#### Démonstration
