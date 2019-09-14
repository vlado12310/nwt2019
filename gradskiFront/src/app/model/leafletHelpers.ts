import { Station } from './Station';

export enum LayerType
{
    StationMarker = 1
}
declare let L;

export let blueStationIcon = L.icon({
  iconUrl: 'assets/leaflet/images/icon.png',
  iconSize: [42, 42], // size of the icon
  shadowSize: [50, 64], // size of the shadow
  iconAnchor: [21, 42], // point of the icon which will correspond to marker's location
  shadowAnchor: [4, 62],  // the same for the shadow
  popupAnchor: [0, -56] // point from which the popup should open relative to the iconAnchor
});
export let redStationIcon = L.icon({
  iconUrl: 'assets/leaflet/images/iconSel.png',
  iconSize: [42, 42], // size of the icon
  shadowSize: [50, 64], // size of the shadow
  iconAnchor: [21, 42], // point of the icon which will correspond to marker's location
  shadowAnchor: [4, 62],  // the same for the shadow
  popupAnchor: [0, -56] // point from which the popup should open relative to the iconAnchor
});

export function removeStationsFromMap(map :any){
        map.eachLayer(function (layer){
        if(layer.layerType && layer.layerType == LayerType.StationMarker){
          map.removeLayer(layer);
        }
      });
}


export function addStationMarkerToMap(map :any, station : Station, draggable : boolean) : any{
  let marker = L.marker([station.location.lat, station.location.lng], { icon: blueStationIcon, draggable: draggable })
      .addTo(map)
      .bindPopup(station.name + "<br>" + "id : " + station.id);
    marker.station = station;
    marker.layerType = LayerType.StationMarker;
    return marker;
}

export function refreshMap(map : any){
  removeStationsFromMap(map);
  map.off('click');
}