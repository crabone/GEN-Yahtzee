# GEN-Yahtzee

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
2. Le joueur spécifie le serveur aucquel il veut se connecter
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

### Modifier son profile

### Créer une partie

### Rejoindre une partie

### Observer une partie

### Modifier la configuration du serveur

### Administrer les utilisateurs du serveur


