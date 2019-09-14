import { Component, OnInit } from '@angular/core';
import { Pricelist } from '../model/pricelist';
import { ActivatedRoute, Router } from '@angular/router';
import {PricelistService} from '../services/pricelist.service';
import {TicketService} from '../services/ticket.service';
import {MatDialog, MatDialogConfig} from '@angular/material'
import {ConfirmBuyComponent} from '../confirm-buy/confirm-buy.component';
import {EditPricelistItemComponent} from '../edit-pricelist-item/edit-pricelist-item.component';
import {PricelistItemService} from '../services/pricelist-item.service';
import {TicketTypeService} from '../services/ticket-type.service';
import {NotificationService} from '../services/notification.service';
import {DialogService} from '../services/dialog.service';
import {LoginService} from '../services/login.service';

@Component({
  selector: 'app-pricelist-detail',
  templateUrl: './pricelist-detail.component.html',
  styleUrls: ['./pricelist-detail.component.css']
})
export class PricelistDetailComponent implements OnInit {
  pricelist : Pricelist;
  ticketTypes = [];
  constructor(private route: ActivatedRoute,
              private pricelistService: PricelistService,
              private ticketService: TicketService,
              private pricelistItemService: PricelistItemService,
              private ticketTypeService : TicketTypeService,
              private notificationService : NotificationService,
              private dialog : MatDialog,
              private dialogService : DialogService,
              private loginService :LoginService,
              private router : Router
            ) { }

  ngOnInit() {
    this.getPricelist();
    if (this.loginService.currentUserValue && this.loginService.currentUserValue.status == "ADMIN"){
      this.ticketTypeService.ticketTypesObservable
      .subscribe(ticketTypes => this.ticketTypes = ticketTypes);
      this.ticketTypeService.getAll();
      this.pricelistItemService.pricelistItem
      .subscribe(item => this.getPricelist());
    }
  }
  getPricelist(): void {
    //AKO JE ULOGOVAN KORISNIK VRATI AKTIVAN CENOVNIK
    if ((this.loginService.currentUserValue
      && this.loginService.currentUserValue.status !="ADMIN")
      || !this.loginService.currentUserValue){
        this.pricelistService.getActivePricelist()
        .subscribe(pricelist => this.pricelist = pricelist);
      }else{
        const id = +this.route.snapshot.paramMap.get('id');
        this.pricelistService.getPricelist(id)
          .subscribe(data => {this.pricelist = data;  });
      }
  }
  buyTicket(ticketType): void{
    //ukoliko je korisnik ulogaovan
    if (this.loginService.currentUserValue){
      this.dialogService.openConfirmDialog('Da li ste sigurni da želite da kupite ' + ticketType.name + ' kartu?')
      .afterClosed().subscribe(res => {
          if (res){
            this.ticketService.buyTicket(ticketType);

          }
      });
    }else{
        this.notificationService.warn("Za kupovinu karte potrebna je autorizacija");
        this.router.navigate(['/login']);
    }
  }
  userLoggedIn(){
    return localStorage.getItem("userToken");
  }
  confirmBuy(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(ConfirmBuyComponent, dialogConfig);
  }
  onEdit(pricelistItem){
    console.log(pricelistItem);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    this.pricelistItemService.initFormForEdit(this.pricelist, pricelistItem, this.ticketTypes);
    this.dialog.open(EditPricelistItemComponent, dialogConfig);
  }
  onAdd(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "20%";
    dialogConfig.disableClose = true;
    console.log(this.ticketTypes.length)
    if (this.pricelist.pricelistItems.length != this.ticketTypes.length){
      this.pricelistItemService.initFormForAdd(this.pricelist, this.ticketTypes);
      this.dialog.open(EditPricelistItemComponent, dialogConfig);
    }else{
      this.notificationService.warn("Sve karte imaju definisanu cenu.");
    }
  }
  onDelete(item){
      this.dialogService.openConfirmDialog('Da li ste sigurni da zelite da obrisete ovu stavku?')
      .afterClosed().subscribe(res => {
          if (res){
            this.pricelistItemService.deleteItem(item);
          }
      });

  }
  getZones(ticketType){
     var zoneString = "";
     for (var i = 0; i < ticketType.zones.length; i++){
       zoneString += ticketType.zones[i].name + ", ";
     }
     zoneString = zoneString.substring(0, zoneString.length -2);

     return zoneString;
  }

  displayedColumns: string[] = ['name', 'duration in hours', 'price','requiredStatus', 'akcije'];
}
