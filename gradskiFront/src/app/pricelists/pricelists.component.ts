import { Component, OnInit } from '@angular/core';
import {PricelistService} from '../services/pricelist.service';
import {Pricelist} from '../model/pricelist';
import {Observable} from 'rxjs';
import {EditPricelistComponent} from '../edit-pricelist/edit-pricelist.component';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {LoginService} from '../services/login.service';
@Component({
  selector: 'pricelists',
  templateUrl: './pricelists.component.html',
  styleUrls: ['./pricelists.component.css']
})
export class PricelistsComponent implements OnInit {
  pricelists: Pricelist[];
  constructor(
    private pricelistService: PricelistService,
    private dialog : MatDialog,
    private loginService: LoginService) { }

  ngOnInit() {
      console.log(this.loginService.currentUserValue);
      this.pricelistService.pricelistObservable.
      subscribe(pricelists =>
        {
          this.pricelists = pricelists;
          this.setActive();
        });
      this.pricelistService.getPricelists();

  }
  onAdd(){
    this.pricelistService.initializeForm();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    this.dialog.open(EditPricelistComponent, dialogConfig);
  }

  setActive(){
    for(var pricelist of this.pricelists){
      var dateStart = pricelist.start.split(" ");
      var partsStart = dateStart[0].split("-");
      var start = new Date(+partsStart[2], +partsStart[1] - 1, +partsStart[0]);
      var dateEnd= pricelist.end.split(" ");
      var partsEnd = dateEnd[0].split("-");
      var end = new Date(+partsEnd[2], +partsEnd[1] - 1, +partsEnd[0]);
      if (start.getTime() < new Date().getTime() && end.getTime() > new Date().getTime()){
        pricelist.active = true;
      }else{
        pricelist.active = false;
      }
    }
  }

}
