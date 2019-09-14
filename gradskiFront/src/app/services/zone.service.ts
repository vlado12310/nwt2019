import { Injectable } from '@angular/core';
import {Config} from 'src/config';
import {HttpClient} from '@angular/common/http'
import { Observable, of, BehaviorSubject} from 'rxjs';
import {Zone} from '../model/zone';
import {FormGroup, FormControl, Validators} from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
export class ZoneService {

  constructor(private http: HttpClient) { }
  private zoneUrl = `${Config.apiUrl}zones`;
  //
    private zonesBehaviorSubject  = new BehaviorSubject<Zone[]>([]);
    public zonesObservable = this.zonesBehaviorSubject.asObservable();
    private zones : Zone[] = [];
  //
  form : FormGroup = new FormGroup({
    id : new FormControl(''),
    name : new FormControl('', Validators.required)
  });
  initForm(){
    this.form.setValue({
      id : '',
      name: ''
    });


  }
  addToForm(zone){
    this.form.setValue({
      id : zone.id,
      name : zone.name
    });
  }
  getAll() : Observable<Zone[]>{
    return this.http.get<Zone[]>(this.zoneUrl);
  }
  findAll(){
    this.http.get<Zone[]>(this.zoneUrl)
    .subscribe(zones => {
      this.zones = zones;
      this.zonesBehaviorSubject.next(this.zones);
    });
  }
  addZone(zone){
    this.http.post<Zone>(this.zoneUrl, zone)
    .subscribe( newZone =>{
      this.zones.push(newZone);
      this.zonesBehaviorSubject.next(this.zones);
    }

    );
  }
  editZone(zone){
    this.http.put<Zone>(this.zoneUrl, zone)
    .subscribe( editedZone=>{
      for (var i = 0; i < this.zones.length; i++){
        if (editedZone.id === this.zones[i].id){
          this.zones[i] = editedZone;
          this.zonesBehaviorSubject.next(this.zones);
          return;
        }
      }
    });
  }
  deleteZone(id){
    this.http.delete<Zone>(this.zoneUrl + "/" + id)
    .subscribe(response =>{
        for (var i = 0; i < this.zones.length; i++){
          if (id === this.zones[i].id){
            this.zones.splice(i, 1);
            this.zonesBehaviorSubject.next(this.zones);
            return;
          }
        }
    });
  }
}
