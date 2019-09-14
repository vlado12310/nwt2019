import { Component, OnInit } from '@angular/core';
import { ZoneService} from '../services/zone.service';
import {Zone} from '../model/zone';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {EditZoneComponent} from '../edit-zone/edit-zone.component';
import {LoginService} from '../services/login.service';
@Component({
  selector: 'app-zones',
  templateUrl: './zones.component.html',
  styleUrls: ['./zones.component.css']
})
export class ZonesComponent implements OnInit {

  constructor(
    private zoneService: ZoneService,
    private dialog : MatDialog,
    private loginService : LoginService

  ) { }
  private zones : Zone[] = [];
  ngOnInit() {

    this.zoneService.zonesObservable.subscribe( zones =>{
        this.zones = zones;
    });
    this.zoneService.findAll();
  }
  onAdd(){
    //this.zoneService.initForm();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    this.dialog.open(EditZoneComponent, dialogConfig);
  }

}
