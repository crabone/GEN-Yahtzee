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
	ID_joueur int(10),
    ID_partie int(15)
);

ALTER TABLE Partie_Joueur ADD FOREIGN KEY (ID_joueur) REFERENCES Joueur (ID);
ALTER TABLE Partie_Joueur ADD FOREIGN KEY (ID_partie) REFERENCES Partie (ID);