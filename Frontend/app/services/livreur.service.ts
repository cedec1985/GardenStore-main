import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Livreur } from '../models/livreur';

@Injectable({
  providedIn: 'root'
})
export class LivreurService {

  constructor(private http: HttpClient) { }

  public apiUrl = 'http://localhost:8080/livreur';

  getDeliveredBy(id: number): Observable<Livreur> {
    return this.http.get<Livreur>(`${this.apiUrl}/${id}`);
  }
  public LivreurID(id:number): any {
    return  id === 1 ? 'DHL' : id === 2 ? 'UPS' : 'FedEx';
  }
 public DelivereryBrandName(): Observable<Livreur> {
    return this.LivreurID(Math.floor(Math.random() * 3) + 1);
  }
}
