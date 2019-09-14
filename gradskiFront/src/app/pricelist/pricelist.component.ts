import { Component, OnInit, Input } from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {PricelistService} from '../services/pricelist.service';
import {EditPricelistComponent} from '../edit-pricelist/edit-pricelist.component';
import {DialogService} from '../services/dialog.service';
@Component({
  selector: 'app-pricelist',
  templateUrl: './pricelist.component.html',
  styleUrls: ['./pricelist.component.css']
})
export class PricelistComponent implements OnInit {
  @Input() pricelist;
  constructor(
    private pricelistService : PricelistService,
    private dialog : MatDialog,
    private dialogService : DialogService
  ) { }

  ngOnInit() {
  }
  onEdit(){
    this.pricelistService.initializeForm();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    //console.log(this.ticketType.zones)
    this.pricelistService.setFormValue(this.pricelist);
    this.dialog.open(EditPricelistComponent, dialogConfig);
  }
  onDelete(){
    this.dialogService.openConfirmDialog('Da li ste sigurni da želite da izbrišete cenovnik?')
    .afterClosed().subscribe(res => {
        if (res){
          this.pricelistService.deletePricelist(this.pricelist);
        }
    });
  }

}
