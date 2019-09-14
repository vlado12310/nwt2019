import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {PricelistService} from '../services/pricelist.service';
import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MatDialogRef} from '@angular/material';
export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};
@Component({
  selector: 'app-edit-pricelist',
  templateUrl: './edit-pricelist.component.html',
  styleUrls: ['./edit-pricelist.component.css'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ]

})
export class EditPricelistComponent implements OnInit {

  constructor(
    private pricelistService: PricelistService,
    private dialogRef: MatDialogRef<EditPricelistComponent>
  ) { }

  ngOnInit() {
  }
  onSubmit(){
    var pricelist = this.generatePricelist();

    if (this.pricelistService.form.valid){
      //ukoliko postoji id onda je edit, ako ne onda je add
      if(!this.pricelistService.form.controls['id'].value){
          this.pricelistService.addPricelist(pricelist);

      }else{

          this.pricelistService.editPricelist(pricelist);
      }
      this.onClose();
    }
  }
  onClose(){
    this.dialogRef.close();
    this.pricelistService.form.reset();
    this.pricelistService.initializeForm();

  }
  generatePricelist(){
      var startMoment = this.pricelistService.form.controls['start'].value;
      var endMoment = this.pricelistService.form.controls['end'].value;
      var start = startMoment.date() + "-" + (startMoment.month() + 1) + "-" + startMoment.year();
      var end = endMoment.date() + "-" + (endMoment.month() + 1) + "-" + endMoment.year();
      var pricelist = {
        id : this.pricelistService.form.controls['id'].value,
        start : start,
        end : end
      }
      return pricelist;
  }

}
