import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, BehaviorSubject, from} from 'rxjs';
import { Config } from 'src/config';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import { Station } from '../model/Station';
import { tap } from 'rxjs/operators';
import { ModelUtils } from '../model/model-utils';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private http : HttpClient) { }
  private stationsBehaviorSubject  = new BehaviorSubject<Station[]>([]);

 public get stationsObservable() : Observable<Station[]>{
   return this.stationsBehaviorSubject.asObservable();
 }
  private stations : Station[] = [];

  private stationUrl = `${Config.apiUrl}station`

  findAll(){
   return this.http.get<Station[]>(this.stationUrl)
    .pipe(tap(stations => {
      this.stations = ModelUtils.deserializeObjects(stations, Station);
      this.stationsBehaviorSubject.next(this.stations);
    }));
  }
  addStation(station : Station){
    return this.http.post<Station>(this.stationUrl, station)
    .pipe(
      tap(
        data =>{
          this.stations.push(data);
          this.stationsBehaviorSubject.next(this.stations); 
        }
      ));
  }

  editStation(station : Station){
    return this.http.put<any>(this.stationUrl, station).pipe(
      tap(
        data =>{
          let index = this.stations.findIndex((element)=> element.id==data.id)
          this.stations[index] = new Station().deserialize(data);
          this.stationsBehaviorSubject.next(this.stations); 
        }
      ));
  }

  deleteStation(station: Station){
      return this.http.delete<any>(`${Config.apiUrl}station/` + station.id).pipe(
        tap(
          data =>{
            let index = this.stations.findIndex((element)=> element.id==data.id)
            this.stations.splice(index,1);
            this.stationsBehaviorSubject.next(this.stations); 
          }
        ));
  }
}
