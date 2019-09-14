package com.gsp.ns.gradskiPrevoz.vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.line.LineService;
import com.gsp.ns.gradskiPrevoz.location.Location;
import com.gsp.ns.gradskiPrevoz.station.Station;
import com.gsp.ns.gradskiPrevoz.vehicle.Vehicle.vehicleType;

@Component
public class VehicleDummyLocator {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	LineService lineService;
	
	Map<Line, List<Vehicle>> lineVehicle  =  new HashMap<>();
	
	@Scheduled(fixedDelay = 10000)
	public void moveVehicle() {
		
		List<Line> lines = lineService.findAll();
		
		
		for(Line line : lines) {
			if(!lineVehicle.containsKey(line)) {
				lineVehicle.put(line, new ArrayList<Vehicle>());
			} 
			List<Vehicle> vehicles = lineVehicle.get(line);
			updateLine(line, vehicles);
		}
		//Removing deleted lines from map
		List<Line> linesToRemove = new ArrayList<>(lineVehicle.keySet());
		linesToRemove.removeAll(lines);
		for(Line lineToRemove : linesToRemove) {
			lineVehicle.remove(lineToRemove);
		}
	}

	private void updateLine(Line line, List<Vehicle> vehicles) {
		for(Vehicle vehicle : vehicles) {
			updateVehicleLocation(vehicle, line);
		}		
		if(vehicles.size()<5) {
			//Generating new vehicle
			Random r = new Random();
			if(r.nextInt(100)>50) {
				Vehicle vehicle = new Vehicle();
				vehicle.type = vehicleType.randomType();
				vehicle.line = line;
				vehicles.add(vehicle);
				vehicleService.save(vehicle);
			}
		}
	}
	
	private void updateVehicleLocation(Vehicle vehicle, Line line) {
		List<Station> stations = line.getStations();
		if(stations==null || stations.size()==0) {
			return;
		}
		if(vehicle.currentLocation==null) {
			vehicle.currentLocation = stations.get(0).getLocation();
		} else {
			//Finding vehicle current station
			Station currentStation = stations
					.stream()
					.filter(station-> station.getLocation().equals(vehicle.currentLocation))
					.findFirst()
					.get();
			//Moving vehicle to next station
			if(currentStation!=null) {
				int indexOf = stations.indexOf(currentStation);
				Station nextStation = stations.get((indexOf+1)%stations.size());
				vehicle.currentLocation = nextStation.getLocation();
			}
		}
		vehicleService.save(vehicle);
	}


}
