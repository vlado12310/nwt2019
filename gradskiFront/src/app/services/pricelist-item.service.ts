import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Pricelist} from '../model/pricelist';
import { Observable, of, BehaviorSubject} from 'rxjs';
import { Config } from 'src/config';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {NotificationService} from '../services/notification.service';
import {TicketType} from '../model/ticketType';
import {PricelistItem} from '../model/pricelistItem';
@Injectable({
  providedIn: 'root'
})
export class PricelistItemService {

//
  private ticketTypesToSelectSubject = new BehaviorSubject<TicketType[]>([]);
  ticketTypesToChoseObservable = this.ticketTypesToSelectSubject.asObservable();

  private pricelistItemSubject = new BehaviorSubject<PricelistItem>(null);
  pricelistItem = this.pricelistItemSubject.asObservable();
//

  constructor(
    private http : HttpClient,
    private notificationService : NotificationService
  ) { }
  private pricelistItemUrl = `${Config.apiUrl}pricelistItem`;
  addItemToPricelist(item){
    console.log(item);
    this.http.post<PricelistItem>(this.pricelistItemUrl, item)
    .subscribe(item => {this.pricelistItemSubject.next(item); this.notificationService.success("Uspešno ste dodali novu stavku.") });
  }
  editItem(item){
    console.log(item);
    this.http.put<PricelistItem>(this.pricelistItemUrl, item)
    .subscribe(
      item => {
        this.pricelistItemSubject.next(item);
        this.notificationService.success("Uspešno ste izmenili stavku.")
      }
  );
  }
  deleteItem(item){
    console.log(item);
    this.http.delete<PricelistItem>(this.pricelistItemUrl + "/" + item.id.pricelistId + "/" + item.id.ticketTypeId)
    .subscribe(
      item =>{
        this.pricelistItemSubject.next(item);
        this.notificationService.success("Uspešno ste obrisali stavku.");
      },
      error =>{
        switch(error.status){
            case 409:{
              this.notificationService.warn("Ne možete izbrisati stavku cenovnika ako postoji kupljena karta!");
            }

        }
      });
  }
  generateItemFromForm(){
    var item = {
      id : {
        pricelistId : this.form.controls['pricelistId'].value,
        ticketTypeId : this.form.controls['ticketTypeId'].value
      },
      price : this.form.controls['price'].value
    }
    return item;
  }
  form : FormGroup = new FormGroup({
    pricelistId : new FormControl(null),
    ticketTypeId : new FormControl('', Validators.required),
    price : new FormControl('', [Validators.required, Validators.min(1)]),
    isEdit : new FormControl('')
  });
  initializeForm(){
    this.form.setValue({
      pricelistId : null,
      ticketTypeId : '',
      price : '',
      isEdit : false
    })
  }
  initFormForEdit(pricelist, pricelistItem, ticketTypes){
      var ticketTypesToChose = JSON.parse(JSON.stringify(ticketTypes));
      for (var i = 0; i < pricelist.pricelistItems.length; i++){
          for (var j = 0; j < ticketTypesToChose.length; j++){
            if (pricelist.pricelistItems[i].ticketType.id === ticketTypesToChose[j].id){
                ticketTypesToChose.splice(j, 1);
            }
          }
      }
      ticketTypesToChose.push(pricelistItem.ticketType);
      this.ticketTypesToSelectSubject.next(ticketTypesToChose);
      this.form.setValue({
        pricelistId : pricelist.id,
        ticketTypeId : pricelistItem.ticketType.id,
        price : pricelistItem.price,
        isEdit : true
      })
  }
  initFormForAdd(pricelist, ticketTypes){
    var ticketTypesToChose = JSON.parse(JSON.stringify(ticketTypes));
    console.log("plItemsLength:" , pricelist.pricelistItems, "ticetTypes", ticketTypes.length);
    for (var i = 0; i < pricelist.pricelistItems.length; i++){
        for (var j = 0; j < ticketTypesToChose.length; j++){
          if (pricelist.pricelistItems[i].ticketType.id === ticketTypesToChose[j].id){
              ticketTypesToChose.splice(j, 1);
          }
        }
    }

    this.ticketTypesToSelectSubject.next(ticketTypesToChose);
    this.form.setValue({
      pricelistId : pricelist.id,
      ticketTypeId : '',
      price : '',
      isEdit : false
    })
  }
}
