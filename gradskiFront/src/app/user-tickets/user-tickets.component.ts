import { Component, OnInit } from '@angular/core';
import { Ticket } from '../model/ticket';
import { TicketService } from '../services/ticket.service';
import { MatDialog } from '@angular/material';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-user-tickets',
  templateUrl: './user-tickets.component.html',
  styleUrls: ['./user-tickets.component.css']
})
export class UserTicketsComponent implements OnInit {
  userTickets: Ticket[];
  ticketToActive: Ticket;
  constructor(
    private ticketService: TicketService,
    private dialog: MatDialog,
    private loginService: LoginService
  ) { }

  ngOnInit() {
    this.ticketService.ticketsObservable.subscribe(userTickets => {
      this.userTickets = userTickets;
      console.log(this.userTickets);
    });
    this.ticketService.getUserTickets();
    
  }

  activateTicket(t: Ticket){
    this.ticketService.activateTicket(t);
  }

}
