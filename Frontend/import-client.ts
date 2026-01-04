import * as mysql from 'mysql2/promise';
import * as fs from 'fs';
import * as csv from 'fast-csv';

// Fonction pour importer les données du fichier CSV dans la table client

async function importCSVToClientTable() {
  const connection = await mysql.createConnection({
    host: 'localhost:3306',
    user: 'root',
    password: 'Artterrevil-85*',
    database: 'gardenstore',
    charset: 'utf8mb4',
  });

  const rows: any[] = [];

  fs.createReadStream('client-csv.csv')
    .pipe(csv.parse({ headers: true }))
    .on('data', (row) => {
      rows.push(row);
    })
    .on('end', async () => {
      for (const row of rows) {
        await connection.execute(
          `INSERT INTO client (${Object.keys(row).join(',')}) VALUES (${Object.keys(row)
            .map(() => '?')
            .join(',')})`,
          Object.values(row)
        );
      }
      console.log('Importation terminée.');
      await connection.end();
    });
}

importCSVToClientTable().catch(console.error);

