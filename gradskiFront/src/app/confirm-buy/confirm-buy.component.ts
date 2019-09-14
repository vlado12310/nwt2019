import { Component, OnInit } from '@angular/core';
declare let L;
@Component({
  selector: 'app-confirm-buy',
  templateUrl: './confirm-buy.component.html',
  styleUrls: ['./confirm-buy.component.css']
})
export class ConfirmBuyComponent implements OnInit {

  constructor() { }
  private map;
  private marker;
  ngOnInit() {
    console.log("usao");
      this.map = L.map('map').setView([45.237957 , 19.813285], 13);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            }).addTo(this.map);
            this.marker = L.marker([45.237957, 19.813285]).addTo(this.map);
  }
  onClick(){
      this.map.removeLayer(this.marker);
  }

}
