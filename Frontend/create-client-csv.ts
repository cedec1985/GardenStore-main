import * as fs from 'fs';
import * as path from 'path';

// Liste des colonnes de la table client
// il est nécessaire de transpiler en javascript avant l'exécution du fichier
const headers = [
  'ID',
  'Nom',
  'Prenom',
  'Addresse_rue',
  'Addresse_ville',
  'Addresse_numero',
  'Addresse_codePostal',
  'Mail',
  'Telephone',
  'Fidelite',
  'N_Reference_Client'
];

// Chemin du fichier CSV à créer
const filePath = path.join(__dirname, 'client-csv.csv');

// Générer la ligne d’en-tête
const csvHeader = headers.join(',') + '\n';

// Écrire le fichier
fs.writeFileSync(filePath, csvHeader, { encoding: 'utf8' });

console.log(`Fichier CSV créé avec succès : ${filePath}`);


