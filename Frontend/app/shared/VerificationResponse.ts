export class VerificationResponse {
    tokenValid!: boolean;
    jwt!: string;
   // username!: string;
    
    constructor(tokenValid: boolean, jwt: string) {
        this.tokenValid = tokenValid;
        this.jwt = jwt;
       // this.username = username;
    }
}