package com.gsp.ns.gradskiPrevoz.zone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;

@RestController
@RequestMapping(value = "api/zones")
public class ZoneController {
	@Autowired
	ZoneService zoneService;
	@GetMapping()
	public ResponseEntity<List<Zone>> findAll(){
		List<Zone> zones = zoneService.findAll();
		return new ResponseEntity<>(zones, HttpStatus.OK);
	}
	@PostMapping()
	public ResponseEntity<Zone> addZone(@RequestBody Zone zone){
		try{
		Zone addedZone = zoneService.save(zone);
		return new ResponseEntity<Zone>(addedZone, HttpStatus.OK);
		}catch(ConflictException e){
			throw new ConflictException("Zona sa imenom " + zone.getName() + " vec postoji.");
		}
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteZone(@PathVariable("id") Long id){
		try{
			Zone zoneToDel = zoneService.findOne(id);
			zoneService.delete(zoneToDel);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(ConflictException e){
			throw e;
		}
	}
	@PutMapping
	public ResponseEntity<?> editZone(@RequestBody Zone zone){
		try{
			Zone zoneDB = zoneService.findOne(zone.getId());
			Zone editedZone = zoneService.edit(zoneDB, zone);
			return new ResponseEntity<Zone>(editedZone, HttpStatus.OK);

		}catch(ConflictException e){
			throw new ConflictException("Zona sa imenom " + zone.getName() + " vec postoji.");

		}
	}
}
