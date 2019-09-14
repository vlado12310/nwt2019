import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Pricelist} from '../model/pricelist';
import { Observable, of, BehaviorSubject} from 'rxjs';
import { Config } from 'src/config';
import {FormGroup, FormControl, Validators} from '@angular/forms';

import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {NotificationService} from '../services/notification.service';
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports


const moment =  _moment;

@Injectable({
  providedIn: 'root',

})
export class PricelistService {

  constructor(
    private http: HttpClient,
    private notificationService : NotificationService
  ) { }

  //
      private pricelistSource = new BehaviorSubject<Pricelist[]>([]);
      pricelistObservable = this.pricelistSource.asObservable();
      private pricelists = [];
  //
  form : FormGroup = new FormGroup({
    id : new FormControl(null),
    start : new FormControl(moment()),
    end : new FormControl(moment()),
  });

  private pricelistUrl = `${Config.apiUrl}pricelist`;


  addPricelist(pricelist){
    this.http.post<Pricelist>(this.pricelistUrl, pricelist)
    .subscribe(
      addedPricelist =>{
        console.log(addedPricelist);
        this.pricelists.push(addedPricelist);
        this.pricelistSource.next(this.pricelists);
        this.notificationService.success("Uspešno ste dodali cenovnik!");
      },
      error =>
      {
        switch(error.status){
            case 406:{
              this.notificationService.warn("Neuspešno dodavanje! Početni datum mora biti manji od krajnjeg datuma.");
            }
            case 409 :{
              this.notificationService.warn("Neuspešno dodavanje! Datum koji ste uneli se poklapa sa datumima postojeceg cenovnika.")
            }
        }

      }
    )
  }
  editPricelist(pricelist){
    this.http.put<Pricelist>(this.pricelistUrl, pricelist)
    .subscribe(
      pricelist =>{
        this.changePricelist(pricelist);
        this.pricelistSource.next(this.pricelists);
        this.notificationService.success("Uspešno ste izmenili cenovnik!");
      }
    )

  }
  deletePricelist(pricelistToDelete){
    this.http.delete<Pricelist>(this.pricelistUrl + "/delete/" + pricelistToDelete.id)
    .subscribe(
      pricelist =>{
        this.removePricelist(pricelistToDelete);
        this.pricelistSource.next(this.pricelists);
        this.notificationService.success("Uspešno ste izbrisali cenovnik!");
      }
    )
  }
  changePricelist(pricelistToChange){
    for (var i = 0; i < this.pricelists.length; i++){
        if (this.pricelists[i].id === pricelistToChange.id){
          this.pricelists[i] = pricelistToChange;
          return;
        }
    }
  }
  removePricelist(pricelist){
    console.log(this.pricelists.length)
    for (var i = 0; i < this.pricelists.length; i++){
        if (this.pricelists[i].id === pricelist.id){
          this.pricelists.splice(i ,1);
          return;
        }
    }
  }
  setFormValue(pricelist){
    var startData = pricelist.start.split("-");
    var endData = pricelist.end.split("-");
    var startMoment = moment().year(startData[2]).month(startData[1] - 1).date(startData[0]);
    var endMoment = moment().year(endData[2]).month(endData[1] -1).date(endData[0]);
    this.form.setValue({
      id : pricelist.id,
      start : startMoment,
      end : endMoment,
    })
  }
  getPricelists() {
      this.http.get<Pricelist[]>(this.pricelistUrl)
      .subscribe(pricelists => {
          this.pricelists = pricelists;
          this.pricelistSource.next(this.pricelists);
      });
  }
  getPricelist(id : number): Observable<Pricelist>{
    return this.http.get<Pricelist>(this.pricelistUrl + "/" + id);
  }
  getActivePricelist() : Observable<Pricelist>{
    return this.http.get<Pricelist>(this.pricelistUrl + "/active");
  }
  initializeForm(){
    this.form.setValue({
      id : null,
      start : moment(),
      end : moment(),
    })
  }
}
