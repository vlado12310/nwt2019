import { Component, OnInit, Input } from '@angular/core';
import { TicketService } from '../services/ticket.service';
import { MatDialog } from '@angular/material';
import { DialogService } from '../services/dialog.service';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {
  @Input() ticket;
  constructor(
    private ticketService : TicketService,
    private dialog : MatDialog,
    private dialogService : DialogService
  ) 
  { }

  ngOnInit() {
  }

}
