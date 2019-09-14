import { Component, OnInit , Input} from '@angular/core';
import {Zone} from '../model/zone';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {EditZoneComponent} from '../edit-zone/edit-zone.component';
import {ZoneService} from '../services/zone.service';
import {DialogService} from '../services/dialog.service';
import {LoginService} from '../services/login.service';
@Component({
  selector: 'app-zone',
  templateUrl: './zone.component.html',
  styleUrls: ['./zone.component.css']
})
export class ZoneComponent implements OnInit {

  @Input() private zone : Zone;
  constructor(
    private dialog : MatDialog,
    private zoneService : ZoneService,
    private dialogService: DialogService,
    private loginService : LoginService

  ) { }

  ngOnInit() {

  }
  onEdit(){
    this.zoneService.addToForm(this.zone);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    this.dialog.open(EditZoneComponent, dialogConfig);
  }
  onDelete(){
    this.dialogService.openConfirmDialog('Da li ste sigurni da želite da izbrišete zonu "' + this.zone.name + '"?')
    .afterClosed().subscribe(res => {
        if (res){
          this.zoneService.deleteZone(this.zone.id);
        }
    });
  }

}
