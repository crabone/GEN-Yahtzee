DROP SCHEMA IF EXISTS Yahtzee;
CREATE schema Yahtzee;
USE Yahtzee;

CREATE TABLE Joueur(
	ID int(10) AUTO_INCREMENT,
    Username varchar(20),
    MDP varchar(64),
    ScoreTotal int(15),
    PRIMARY KEY (ID)
);

CREATE TABLE Partie(
	ID int(15) AUTO_INCREMENT,
    Etat varchar(15),    
    PRIMARY KEY (ID)
);

CREATE TABLE Partie_Joueur(
	ID int(15) AUTO_INCREMENT,
	ID_joueur int(10),
    ID_partie int(15),
    PRIMARY KEY (ID)
);

# Insertion de données pour tests
#INSERT INTO Yahtzee.Joueur
#(Username, MDP)
#VALUES
#("rosanne", "12313"),
#("mado", "1341"),
#("kevin", "234");
#INSERT INTO Yahtzee.Partie
(#Etat)
#VALUES
#("running"),
#("waiting"),
#("ended");
#INSERT INTO Yahtzee.Partie_Joueur
#(ID_joueur, ID_partie)
#VALUES
#(1, 1),
#(2, 1),
#(2, 2),
#(3, 2),
#(1, 3),
#(3, 3);

ALTER TABLE Partie_Joueur ADD FOREIGN KEY (ID_joueur) REFERENCES Joueur (ID);
ALTER TABLE Partie_Joueur ADD FOREIGN KEY (ID_partie) REFERENCES Partie (ID);