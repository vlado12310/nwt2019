import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, BehaviorSubject} from 'rxjs';
import { Config } from 'src/config';
import {FormGroup, FormControl, Validators} from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
export class LineService {

  constructor(private http : HttpClient) { }
  private lineUrl = `${Config.apiUrl}line`;
  addLine(line){
    return this.http.post<any>(this.lineUrl, line);
  }
  findAll(){
    return this.http.get<any>(this.lineUrl);
  }
  deleteLine(id){
    return this.http.delete<any>(this.lineUrl + "/" + id);
  }
  findOne(id){
    return this.http.get<any>(this.lineUrl + "/" + id);
  }
}
