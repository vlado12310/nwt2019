import { Component, OnInit , Input} from '@angular/core';
import {TicketType} from '../model/TicketType';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {TicketTypeService} from '../services/ticket-type.service';
import {EditTicketTypeComponent} from '../edit-ticket-type/edit-ticket-type.component';
import {Zone} from '../model/zone';
import {DialogService} from '../services/dialog.service';
@Component({
  selector: 'app-ticket-type',
  templateUrl: './ticket-type.component.html',
  styleUrls: ['./ticket-type.component.css']
})
export class TicketTypeComponent implements OnInit {
  @Input() ticketType: TicketType;
  constructor(private dialog: MatDialog,
              private ticketTypeService: TicketTypeService,
              private dialogService: DialogService
            ) { }

  ngOnInit() {
  }
  onEdit(){
    this.ticketTypeService.initializeForm();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    //console.log(this.ticketType.zones)
    this.ticketTypeService.form.patchValue(this.ticketType);
    console.log(this.ticketTypeService.form.controls['zones'].value);
    this.dialog.open(EditTicketTypeComponent, dialogConfig);
  }
  onDelete(){
    this.dialogService.openConfirmDialog('Da li ste sigurni da želite da izbrišete kartu?')
    .afterClosed().subscribe(res => {
        if (res){
          this.ticketTypeService.deleteTicketType(this.ticketType);
        }
    });

  }
  makeZoneView(zones : Zone[]){

      var zonesStr = "";
      for (var i = 0; i < zones.length; i++){
        zonesStr += zones[i].name + ", ";
      }
      return zonesStr.substring(0, zonesStr.length -2);
    }


}
