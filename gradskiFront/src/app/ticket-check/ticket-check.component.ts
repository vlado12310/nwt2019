import { Component, OnInit } from '@angular/core';
import { TicketService } from '../services/ticket.service';
import { Ticket } from '../model/ticket';

@Component({
  selector: 'app-ticket-check',
  templateUrl: './ticket-check.component.html',
  styleUrls: ['./ticket-check.component.css']
})
export class TicketCheckComponent implements OnInit {
  email : string;
  userTickets: Ticket[];
  constructor(
    private ticketService: TicketService
  ) { }

  ngOnInit() {
  }
  find(){
    
    this.ticketService.ticketsObservable.subscribe(userTickets => {
      this.userTickets = userTickets;
      console.log(this.userTickets);
    });
    this.ticketService.getUserActiveTicketsByEmail(this.email);
  }

}
