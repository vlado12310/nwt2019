import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {TicketType} from '../model/TicketType';
import { Observable, of, BehaviorSubject} from 'rxjs';
import { Config } from 'src/config';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {NotificationService} from '../services/notification.service';

@Injectable({
  providedIn: 'root'
})
export class TicketTypeService {

  constructor(private http: HttpClient,
            private notificationService : NotificationService
  ) { }


  //
      private ticketTypesSource = new BehaviorSubject<TicketType[]>(null);
      ticketTypesObservable = this.ticketTypesSource.asObservable();
      private ticketTypes = [];
  //

  private ticketTypeUrl = `${Config.apiUrl}ticketType`;



  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl('', Validators.required),
    durationInHours: new FormControl('', [Validators.required, Validators.min(1)]),
    requiredStatus: new FormControl('', Validators.required),
    zones : new FormControl([], Validators.required)
  });

  initializeForm(){
    this.form.setValue({
      id: null,
      name: '',
      durationInHours: '',
      requiredStatus: '',
      zones: []
    });
  }

  getAll() {
    this.http.get<TicketType[]>(this.ticketTypeUrl)
    .subscribe(ticketTypes => {
        this.ticketTypes = ticketTypes;
        this.ticketTypesSource.next(this.ticketTypes);
    });

  }
  addTicketType(ticketType: TicketType){

    this.http.post<TicketType>(this.ticketTypeUrl, ticketType)
    .subscribe(ticketType =>{
        this.ticketTypes.push(ticketType);
        this.ticketTypesSource.next(this.ticketTypes);
        this.notificationService.success("Uspesno ste dodali kartu!");
    });
    //this.http.post<TicketType>(this.ticketTypeUrl, ticketType)
    //.subscribe(newTicketType);

  }
  editTicketType(ticketType: TicketType){

    this.http.put<TicketType>(this.ticketTypeUrl, ticketType)
    .subscribe(ticketType =>{
        this.changeTicketType(ticketType);
        this.ticketTypesSource.next(this.ticketTypes);
        this.notificationService.success("Uspesno ste izmenili kartu!");
    });
  }
  deleteTicketType(ticketType){
    console.log(ticketType)
    this.http.delete<TicketType>(`${this.ticketTypeUrl}/${ticketType.id}`)
    .subscribe(
    response =>{
        this.removeTicketType(ticketType);
        this.ticketTypesSource.next(this.ticketTypes);
        this.notificationService.success("Uspesno ste obrisali kartu!");});
  }

  removeTicketType(ticketType){
    for (var i = 0; i < this.ticketTypes.length; i++){
        if (this.ticketTypes[i].id == ticketType.id){
          this.ticketTypes.splice(i,1);
          return;
        }
    }
  }
  changeTicketType(ticketType){
    for (var i = 0; i < this.ticketTypes.length; i++){
      if (ticketType.id === this.ticketTypes[i].id){
          this.ticketTypes[i] = ticketType;
          return;
      }
    }
  }

}
