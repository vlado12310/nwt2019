import { Component, OnInit, Input } from '@angular/core';
import { Station } from '../model/Station';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ZoneService } from '../services/zone.service';
import { Zone } from '../model/zone';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { StationService } from '../services/station.service';
import { LineService } from '../services/line.service';
import { NotificationService } from '../services/notification.service';
import { Observable } from 'rxjs';
import { removeStationsFromMap, addStationMarkerToMap, redStationIcon } from '../model/leafletHelpers';

@Component({
  selector: 'app-line',
  templateUrl: './line.component.html',
  styleUrls: ['./line.component.css']
})
export class LineComponent implements OnInit {

  constructor(
    private zoneService: ZoneService,
    private stationService: StationService,
    private lineService: LineService,
    private notificationService: NotificationService) { }
    @Input() map?: any;
  selectedStations: Station[] = [];
  formLine: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    zone: new FormControl('', Validators.required),
    stationsControl: new FormControl('', Validators.required)
  });
  zones: Zone[] = [];
  public stations: Station[] = [];
  private subscirbed : boolean = false;

  ngOnInit() {
    if (!this.subscirbed) {
      this.zoneService.zonesObservable.subscribe(zones => {
        this.zones = zones;
      });
      this.zoneService.findAll();

      this.stationService.stationsObservable.subscribe(data => {
        this.stations = data;
        this.selectedStations = [];
      });
      this.stationService.findAll().subscribe();

      this.subscirbed = true;
    }
    if(this.map){
      this.addStationsToMap(this.stations);
    }
  }

  addStationsToMap(stations) {
    removeStationsFromMap(this.map);
    for (let station of stations) {
      this.addStationMarker(station);
    }
  }

  addStationMarker(station) {
    addStationMarkerToMap(this.map, station, false);
  }
    
  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }
  onAddLine() {
    var line = {
      name: this.formLine.controls['name'].value,
      stations: this.selectedStations,
      zone: this.formLine.controls['zone'].value
    }
    debugger;
    this.lineService.addLine(line)
      .subscribe(addedLine => {
        this.notificationService.success("Uspesno ste dodali liniju " + addedLine.name + ".");
        this.formLine.reset();
      });
  }

  markStation(station : Station){

  }

}
