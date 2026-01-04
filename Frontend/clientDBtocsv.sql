SELECT * FROM client
INTO OUTFILE 'Frontend/client-csv.csv'
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n';

/*
Le chemin doit être accessible en écriture par le serveur MySQL (souvent /var/lib/mysql-files/).

Ce fichier sera créé sur le serveur, pas sur ton PC local.
Assure-toi que le serveur MySQL a les permissions nécessaires pour écrire dans le répertoire spécifié.
*/
