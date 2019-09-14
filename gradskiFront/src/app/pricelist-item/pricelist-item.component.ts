import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pricelist-item',
  templateUrl: './pricelist-item.component.html',
  styleUrls: ['./pricelist-item.component.css']
})
export class PricelistItemComponent implements OnInit {

  constructor() { }
  userStatuses  = [{value: 0, status: "STANDARD"},
                          {value: 1, status: "STUDENT"},
                          {value: 2, status: "ELDERLY"}];

  ngOnInit() {
  }

}
