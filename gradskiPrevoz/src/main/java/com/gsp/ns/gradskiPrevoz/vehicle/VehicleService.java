package com.gsp.ns.gradskiPrevoz.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.station.Station;

@Service
public class VehicleService {
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public List<Vehicle> findAll(){
		return vehicleRepository.findAll();
	}
	
	public Vehicle findOne(long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		if (!vehicle.isPresent()){
			throw new ResourceNotFoundException();
		};
		return vehicle.get();
	}
	
	public Vehicle save(Vehicle newVehicle) {
		Vehicle addedVehicle = vehicleRepository.save(newVehicle);
		return addedVehicle;
	}
	
	public Vehicle addOne(Vehicle newVehicle) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(newVehicle.getId());
		if (vehicle.isPresent()){
			throw new ConflictException();
		};
		
		return save(newVehicle);
	}
	
	public void delete(Vehicle vehicle) {
		vehicleRepository.delete(vehicle);
	}
	
	public Vehicle change(Vehicle fromDbVehicle, Vehicle newVehicle) {
		fromDbVehicle.setCurrentLocation(newVehicle.getCurrentLocation());
		fromDbVehicle.setLine(newVehicle.getLine());
		fromDbVehicle.setType(newVehicle.getType());
		Vehicle savedVehicle = vehicleRepository.save(fromDbVehicle);
		
		return savedVehicle;
	}
}
