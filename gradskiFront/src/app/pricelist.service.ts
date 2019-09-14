import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Pricelist} from './pricelist';
import { Observable, of} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  constructor(private http: HttpClient) { }
  private pricelistUrl = 'http://localhost:8090/api/pricelist';
  getPricelists(): Observable<Pricelist[]> {
    return this.http.get<Pricelist[]>(this.pricelistUrl);
  }
  getPricelist(id : number): Observable<Pricelist>{
    return this.http.get<Pricelist>(this.pricelistUrl + "/" + id);
  }
}
