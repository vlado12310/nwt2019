import { Component, OnInit, ViewChild } from '@angular/core';
import { LoginService } from '../services/login.service';
import { MatTabChangeEvent } from '@angular/material';
import { StationsComponent } from '../stations/stations.component';
import { LineComponent } from '../line/line.component';
import { refreshMap } from '../model/leafletHelpers';
declare let L;
@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.css']
})
export class MapViewComponent implements OnInit {
  @ViewChild(StationsComponent) private stationComponent: StationsComponent;
  @ViewChild(LineComponent) private lineComponent: LineComponent;
  constructor(private loginService : LoginService) { }
  private map;
  ngOnInit() {
    this.map = L.map('map').setView([45.253863 , 19.828606], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    }).addTo(this.map);
  }

  onTabChanged(event : MatTabChangeEvent){
    refreshMap(this.map);
    if(event.index==0){
      this.stationComponent.ngOnInit();
    } else {
      this.lineComponent.ngOnInit();
    }
   
  }

}
