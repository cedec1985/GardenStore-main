export class Livreur{
livreur: any;
  static id = [
    {
    "DHL" : "1",
    "UPS" : "2",
    "Fedex": "3"
  }]
  static societe: string;
  static email: string;
  static nomContact: string;
  static prenom: string;
  static nom: string;
	constructor(
        public id:number,
		    public nom: string,
        public prenom: string,
        public nomContact:string,
        public email:string,
        public societe:string
        ) { }
}
