import { createObjectCsvWriter } from 'csv-writer';
import * as mysql from 'mysql2/promise';

// Fonction pour exporter les données de la table client vers un fichier CSV

async function exportClientTableToCSV() {
  const connection = await mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Artterrevil-85*',
    database: 'gardenstore',
    charset: 'utf8mb4', // Important pour UTF-8
  });
  const [rows] = await connection.execute('SELECT * FROM client');

  if (!Array.isArray(rows) || rows.length === 0) {
    console.log('Aucune donnée trouvée.');
    return;
  }
  const headers = Object.keys(rows[0]).map((key) => ({
    id: key,
    title: key,
  }));
  const csvWriter = createObjectCsvWriter({
    path: 'Frontend/table-client-csv.csv',
    header: headers,
    encoding: 'utf8',
  });

  await csvWriter.writeRecords(rows as object[]);
  console.log('Fichier CSV exporté avec succès.');

  await connection.end();
}

exportClientTableToCSV().catch(console.error);

