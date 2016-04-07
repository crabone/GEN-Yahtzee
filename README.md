# GEN-Yahtzee

## Fonctionnement général de l'applicatif

### Objectifs de base
Dans le cadre du cours de Génie Logiciel (GEN), nous avons pour projet, de concevoir une application client-serveur en adoptant la méthodologie de travail, Unified Process (UP). Nous avons choisis de réaliser une version digitale du Yahtzee, célèbre jeu de société.

### Utilisation de l'applicatif
Le client est accueilli sur une fenêtre d'authentification où il a la possibilité de spécifier l'adresse d'un serveur distant ainsi que ses identifiants, pour s'y connecter. Ensuite, il est redirigé sur la fenêtre principale du serveur. Pour jouer, il peut rejoindre une partie en attente de joueurs ou, en créer une nouvelle. Le cas écheant il doit spécifier le nombre de participants (2 au minimum) et leur temps à disposition pour chaque tours.

L'interface de jeu permet d'effectuer les actions permises, de visualiser sa progression (et celle de ses adversaires) et d'obtenir un résumé des combinaison gagnante. Il est prévu aussi de mettre à disposition un système permettant de communiquer lors d'une partie.

Lorsque qu'une partie débute, le serveur choisis aléatoirement le joueur qui commencera. A terme, le serveur enregistre les scores des différents joueurs et dresse un classement.

### Règles du jeu

### Contraintes
* Un joueur ne peut réjoindre une partie en cours (y compris dans le cas où il s'y est déconnecté involontairement);
* Une partie ne peut être composé de plus de 6 joueurs;
* Le protocole client-serveur n'est pas chiffré.
