LOAD DATA LOCAL INFILE 'Frontend/client-csv.csv'
INTO TABLE client
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

/*
IGNORE 1 LINES si ton CSV contient un en-tête.
*/
