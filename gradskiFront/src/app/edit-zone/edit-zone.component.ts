import { Component, OnInit } from '@angular/core';
import {ZoneService} from '../services/zone.service';
import {MatDialogRef} from '@angular/material';
@Component({
  selector: 'app-edit-zone',
  templateUrl: './edit-zone.component.html',
  styleUrls: ['./edit-zone.component.css']
})
export class EditZoneComponent implements OnInit {

  constructor(
    private zoneService : ZoneService,
    private dialogRef: MatDialogRef<EditZoneComponent>,

  ) { }

  ngOnInit() {

  }
  onSubmit(){
    if (this.zoneService.form.controls['id'].value){
      this.zoneService.editZone(this.zoneService.form.value);

    }else{
      this.zoneService.addZone(this.zoneService.form.value);
    }
    this.onClose();
  }
  onClose(){
    this.zoneService.form.reset();
    this.zoneService.initForm();
    this.dialogRef.close();
  }

}
