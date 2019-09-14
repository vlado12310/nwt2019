import { Component, OnInit } from '@angular/core';
import {PricelistItemService} from '../services/pricelist-item.service';
import {MatDialogRef} from '@angular/material';
@Component({
  selector: 'app-edit-pricelist-item',
  templateUrl: './edit-pricelist-item.component.html',
  styleUrls: ['./edit-pricelist-item.component.css']
})
export class EditPricelistItemComponent implements OnInit {
  ticketTypes = [];
  constructor(
      private pricelistItemService : PricelistItemService,
      private dialogRef : MatDialogRef<EditPricelistItemComponent>
  ) { }

  ngOnInit() {
    this.pricelistItemService.ticketTypesToChoseObservable
    .subscribe(ticketTypes => this.ticketTypes = ticketTypes);
  }
  onSubmit(){
    var item = this.pricelistItemService.generateItemFromForm();
    if (!this.pricelistItemService.form.controls['isEdit'].value){
      console.log("a");
        this.pricelistItemService.addItemToPricelist(item);
    }else{
        this.pricelistItemService.editItem(item);
    }
    this.onClose();
  }
  onClose(){
    this.pricelistItemService.form.reset();
    this.pricelistItemService.initializeForm();
    this.dialogRef.close();
  }

}
