import { Component, OnInit, Input } from '@angular/core';
import { StationService } from '../services/station.service';
import { LineService } from '../services/line.service';
import { ZoneService } from '../services/zone.service';
import { StationMarker } from '../model/stationMarker';
import { NotificationService } from '../services/notification.service';
import { LayerType, removeStationsFromMap, addStationMarkerToMap, redStationIcon, blueStationIcon } from '../model/leafletHelpers';
import { tap } from 'rxjs/operators';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Station } from '../model/Station';
declare let L;
@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css']
})
export class StationsComponent implements OnInit {

  constructor(
    private stationService: StationService,
    private notificationService: NotificationService
  ) { }
  @Input() map: any;
  deleteForm: FormGroup = new FormGroup({
    id: new FormControl('', Validators.required),
  });

  form: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    lat: new FormControl('', Validators.required),
    lng: new FormControl('', Validators.required)
  });

  initForm(latlng) {
    this.form.setValue({
      name: this.form.controls['name'].value,
      lat: latlng.lat.toFixed(4),
      lng: latlng.lng.toFixed(4)
    });
  }

  initFormFromStation(station: any) {
    this.form.setValue({
      name: station.name,
      lat: Number(station.location.lat).toFixed(4),
      lng: Number(station.location.lng).toFixed(4)
    });
    this.deleteForm.setValue({
      id: station.id
    });
  }

  resetForms() {
    this.resetDeleteForm();
    this.resetForm();
  }

  resetDeleteForm() {
    this.deleteForm.reset();
    this.deleteForm.markAsUntouched();
    this.deleteForm.markAsPristine();
  }

  resetForm() {
    this.form.reset();
    this.form.markAsUntouched();
    this.form.markAsPristine();
  }

  get getStationFromForm() {
    let station = {
      id: this.deleteForm.controls['id'].value,
      name: this.form.controls['name'].value,
      location: {
        lat: this.form.controls['lat'].value,
        lng: this.form.controls['lng'].value
      }
    }
    return new Station().deserialize(station);
  }
  

  //
  buttonText: String;
  private clickedMarker: any;
  //
  ngOnInit() {
    this.buttonText = "Add station";
    this.map.on('click', this.onMapClick.bind(this));
    this.stationService.findAll()
      .subscribe(stations => {
        this.addStationsToMap(stations);
      });
  }

  addStationsToMap(stations) {
    removeStationsFromMap(this.map);
    for (let station of stations) {
      this.addStationMarker(station);
    }

  }

  addStationMarker(station) {
    let marker =  addStationMarkerToMap(this.map, station, true);
    marker.on("click", this.onMarkerClick.bind(this, marker));
    marker.on("dragend", this.onMarkerDragend.bind(this, marker));
  }

  onMarkerClick(markerStation: any) {
    if (this.clickedMarker) {
      this.clickedMarker.setIcon(blueStationIcon);
    }
    this.initFormFromStation(markerStation.station);
    this.buttonText = "Edit";
    this.clickedMarker = markerStation;
    markerStation.setIcon(redStationIcon);

  }

  onMarkerDragend(markerStation: any) {
    let editedStation = {
      id: markerStation.station.id,
      name: markerStation.station.name,
      location: this.getLocationFromLatLng(markerStation._latlng)
    };
    this.stationService.editStation(new Station().deserialize(editedStation))
      .pipe(
        tap(
          (data: any) => {
            this.notificationService.success("Edited succesfuly.");
            markerStation.station = editedStation;
            markerStation.setLatLng(editedStation.location);
          },
          error => {
            markerStation.setLatLng(markerStation.station.location);
          }
        )).subscribe(() => this.onMarkerClick(markerStation));
  }

  onMapClick(e) {
    this.resetForms();
    this.initForm(e.latlng);
    var popup = L.popup()
      .setLatLng(this.getLocationFromLatLng(e.latlng))
      .setContent("Nova stanica?" + "<br>" + e.latlng.lat.toFixed(2) + " , " + e.latlng.lng.toFixed(2));
    if (this.map) {
      this.map.openPopup(popup);
    }
    this.buttonText = "Add station";
    if (this.clickedMarker) {
      this.clickedMarker.setIcon(blueStationIcon);
      this.clickedMarker = null;
    }
  }

  onSubmit() {
    this.map.closePopup();
    if (this.buttonText == "Add station") {
      this.stationService.addStation(this.getStationFromForm)
        .subscribe(station => {
          this.notificationService.success("Added station " + station.name + ".");
          this.addStationMarker(station);
          this.resetForms();
        });
    } else {
      this.stationService.editStation(this.getStationFromForm)
        .subscribe(station => {
          this.notificationService.success("Edited station " + station.name + ".");
          this.clickedMarker._popup.setContent(station.name + "<br>" + "id : " + station.id);
        });
    }

  }

  onDelete() {
    this.stationService.deleteStation(this.getStationFromForm)
      .subscribe(() => {
        this.notificationService.success("Station deleted " + this.clickedMarker.station.name + ".");
        this.map.removeLayer(this.clickedMarker);
        this.resetForms();
      });
  }

  getLocationFromLatLng(latLng: any): any {
    return { lat: latLng.lat.toFixed(4), lng: latLng.lng.toFixed(4) }
  }
}
