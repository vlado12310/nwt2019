import { Component, OnInit } from '@angular/core';
import {TicketType} from '../model/TicketType';
import { Observable} from 'rxjs';
import {TicketTypeService} from '../services/ticket-type.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {EditTicketTypeComponent} from '../edit-ticket-type/edit-ticket-type.component';
@Component({
  selector: 'app-ticket-types',
  templateUrl: './ticket-types.component.html',
  styleUrls: ['./ticket-types.component.css']
})
export class TicketTypesComponent implements OnInit {
  ticketTypes : TicketType[] = [];
  constructor(private ticketTypeService: TicketTypeService,
              private dialog : MatDialog) { }

  ngOnInit() {
    this.ticketTypeService.ticketTypesObservable
    .subscribe(ticketTypes => this.ticketTypes = ticketTypes);

    this.ticketTypeService.getAll();

    //.subscribe(response => this.ticketTypes = response);

  }



  onAdd(){
    this.ticketTypeService.initializeForm();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    this.dialog.open(EditTicketTypeComponent, dialogConfig);
  }
}
