import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { Config } from 'src/config';
import {TicketType} from '../model/ticketType';
import { Router } from '@angular/router'
import {NotificationService} from '../services/notification.service';
import {BehaviorSubject} from 'rxjs';
import { Ticket } from '../model/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',

  })
  };
  constructor(private http: HttpClient, private router: Router, private notificationService : NotificationService) { }

  //
    private ticketSource = new BehaviorSubject<Ticket[]>([]);
    ticketsObservable = this.ticketSource.asObservable();
    private userTickets = [];
  //
  
  private ticketUrl =  `${Config.apiUrl}ticket`;

  buyTicket(ticketType){

    this.http.post<any>(this.ticketUrl, ticketType, this.httpOptions)
    .subscribe(ticket => {this.router.navigate(["/myTickets"]); this.notificationService.success("Uspešno ste kupili " + ticketType.name + " kartu.")},
    
     );
  }

  getUserTickets() {
    this.http.get<Ticket[]>(this.ticketUrl+'/userTickets')
    .subscribe(userTickets => {
        this.userTickets = userTickets;
        this.ticketSource.next(this.userTickets);
    });
  }

  activateTicket(t:Ticket){
    this.http.put<any>(this.ticketUrl+"/activate", t, this.httpOptions)
    .subscribe(ticket => {
    this.notificationService.success("Uspešno ste aktivirali " + ticket.ticketType.name + " kartu.");
    this.router.navigate(["/"]);
  });
  }

  getUserActiveTicketsByEmail(email) {
    this.http.get<Ticket[]>(this.ticketUrl+'/userActiveTicketsEmail/'+email,this.httpOptions)
    .subscribe(t => {
        this.userTickets = t;
        this.ticketSource.next(this.userTickets);
        console.log(t);
    });

  }
}
