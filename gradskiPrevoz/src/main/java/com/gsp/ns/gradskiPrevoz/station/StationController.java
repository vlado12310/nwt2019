package com.gsp.ns.gradskiPrevoz.station;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "api/station")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Station>> getAllStations(){
		return new ResponseEntity<List<Station>>(this.stationService.findAll(), HttpStatus.OK);
	}
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE,
				path = "/{id}")
	public ResponseEntity<Station> findOneStation(@PathVariable("id") Long id){
		
		return new ResponseEntity<Station>(this.stationService.findOne(id), HttpStatus.OK);
	}
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, 
				consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> addStation(@RequestBody Station newStation){
			Station addedStation = stationService.save(newStation);
			return new ResponseEntity<>(addedStation, HttpStatus.OK);	
		
	}
	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> deleteStation(@PathVariable("id") Long id){
		try{
			Station stationDb = stationService.findOne(id);
			stationService.delete(stationDb);
			return new ResponseEntity<Station>(stationDb,HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			throw new ResourceNotFoundException("Ne postoji stanica sa odabranim id-em");
		}

	}
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Station> changeStation(@RequestBody Station newStation){
		try{
			Station stationDb = stationService.findOne(newStation.getId());
			Station changedStation = stationService.change(stationDb, newStation);
			return new ResponseEntity<Station>(changedStation, HttpStatus.OK);
		}catch(DataIntegrityViolationException e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}catch(ResourceNotFoundException ex){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
