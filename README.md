# GEN-Yahtzee

## Fonctionnement général de l'applicatif

### Objectifs de base
Dans le cadre du cours de Génie Logiciel (GEN), nous avons pour projet, de concevoir une application client-serveur en adoptant la méthodologie de travail, Unified Process (UP). Nous avons choisis de réaliser une version digitale du Yahtzee, célèbre jeu de société.

### Utilisation de l'applicatif
Le client est accueilli sur une fenêtre d'authentification où il a la possibilité de spécifier l'adresse d'un serveur distant ainsi que ses identifiants, pour s'y connecter. Ensuite, il est redirigé sur la fenêtre principale du serveur. Pour jouer, il peut rejoindre une partie en attente de joueurs ou, en créer une nouvelle. Le cas écheant il doit spécifier le nombre de participants (2 au minimum) et leur temps à disposition pour chaque tours.

Le client a la possibilité de modifier son profil (mot de passe, avatar, description). Le serveur quant à lui dispose d'outils d'administrations permettant, d'ajouter, de modifier ou de supprimer un joueur. Il peut aussi configurer le moteur de jeu et ainsi permettre, par exemple, la création de partie composée de N joueurs.

L'interface de jeu permet d'effectuer les actions permises, de visualiser sa progression (et celle de ses adversaires) et d'obtenir un résumé des combinaison gagnante. Il est prévu aussi de mettre à disposition un système permettant de communiquer lors d'une partie.

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
