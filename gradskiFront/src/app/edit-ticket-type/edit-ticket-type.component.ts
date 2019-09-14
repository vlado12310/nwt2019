import { Component, OnInit } from '@angular/core';
import {TicketTypeService} from '../services/ticket-type.service';
import {MatDialogRef} from '@angular/material';
import {TicketTypesComponent} from '../ticket-types/ticket-types.component';
import {Zone} from '../model/zone';
import {ZoneService} from '../services/zone.service';
import {NotificationService} from '../services/notification.service';


@Component({
  selector: 'app-edit-ticket-type',
  templateUrl: './edit-ticket-type.component.html',
  styleUrls: ['./edit-ticket-type.component.css']
})
export class EditTicketTypeComponent implements OnInit {
  userStatuses = ["STANDARD","STUDENT","ELDERLY"]
  zones : Zone[] = [];
  constructor(private ticketTypeService: TicketTypeService,
              private zoneService : ZoneService,
              private dialogRef: MatDialogRef<EditTicketTypeComponent>,
              private notificationService : NotificationService

            ) { }

  ngOnInit() {
      this.zoneService.getAll()
      .subscribe(zones => this.zones = zones);
  }
  onSubmit(){
    if (this.ticketTypeService.form.valid){
      //ukoliko postoji id onda je edit, ako ne onda je add
      if(!this.ticketTypeService.form.controls['id'].value){
          this.ticketTypeService.addTicketType(this.ticketTypeService.form.value)

      }else{
          this.ticketTypeService.editTicketType(this.ticketTypeService.form.value)
      }
      this.ticketTypeService.form.reset();
      this.ticketTypeService.initializeForm();
      this.onClose();
    }
  }
  submitTicketType(){

  }
  onClose(){
    this.ticketTypeService.form.reset();
    this.ticketTypeService.initializeForm();
    this.dialogRef.close();
  }

}
