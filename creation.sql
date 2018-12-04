CREATE TABLE Ligues (
	nomLigue VARCHAR(255),
	nbJoueurMaxParEquipe INTEGER,
	PRIMARY KEY (nomLigue)
);

CREATE TABLE Equipes (
	nomEquipe VARCHAR(255),
	nomLigue VARCHAR(255),
	matriculeCapitaine INTEGER,
	PRIMARY KEY (nomEquipe),
	FOREIGN KEY (nomLigue) REFERENCES Ligues(nomLigue)
);

CREATE TABLE Resultats (
	idResultat SERIAL,
	nomEquipeA VARCHAR(255),
	nomEquipeB VARCHAR(255),
	scoreEquipeA INTEGER,
	scoreEquipeB INTEGER,
	PRIMARY KEY (idResultat),
	FOREIGN KEY (nomEquipeA) REFERENCES Equipes(nomEquipe),
	FOREIGN KEY (nomEquipeB) REFERENCES Equipes(nomEquipe)
);

CREATE TABLE Participants (
	matricule INTEGER,
	nom	VARCHAR(255),
	prenom VARCHAR(255),
	motDePasse VARCHAR(60),
	estAccepter INTEGER DEFAULT 0,
	nomEquipe VARCHAR(255),
	PRIMARY KEY (matricule),
	FOREIGN KEY (nomEquipe) REFERENCES Equipes(nomEquipe)
);



