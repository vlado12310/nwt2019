package com.gsp.ns.gradskiPrevoz.vehicle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.NoFundsException;
import com.gsp.ns.gradskiPrevoz.exceptions.PricelistNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.user.User;
import com.gsp.ns.gradskiPrevoz.user.UserService;

@RestController
@RequestMapping(value="/api/vehicle")
public class VehicleController {
	@Autowired
	VehicleService vehicleService;
	@Autowired
	UserService userService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle, Authentication auth){
		try{
			Vehicle vehicleDB = vehicleService.addOne(vehicle);
			return new ResponseEntity<Vehicle>(vehicleDB, HttpStatus.CREATED);
		}catch(NoFundsException e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Vehicle>> getVehicles(){
		List<Vehicle> vehicles = vehicleService.findAll();
		return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> DeleteVehicle(@RequestBody Vehicle vehicleToDelete){
		try{
			Vehicle vehicle = vehicleService.findOne(vehicleToDelete.getId());
			vehicleService.delete(vehicle);
			return new ResponseEntity<>( HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") Long id){
		try{
			Vehicle vehicle = vehicleService.findOne(id);
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
