package com.gsp.ns.gradskiPrevoz.timetable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value="/api/timetables")
public class TimetableController {
	
	@Autowired
	TimetableService ttService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Timetable> add(@RequestBody Timetable table){
		//uvek moze da se kreira?! jedinstvena samo po id
		return new ResponseEntity<Timetable>(ttService.save(table), HttpStatus.CREATED);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Timetable>> getTimetables(){
		return new ResponseEntity<List<Timetable>>(ttService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Timetable> getTimetable(@PathVariable("id") Long id){
		try {
			return new ResponseEntity<Timetable>(ttService.getOne(id), HttpStatus.OK);
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTimetable(@RequestBody Timetable tableToDelete){
		try {
			Timetable table = ttService.getOne(tableToDelete.getId());
			ttService.delete(table);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ForbiddenException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}catch(ConflictException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Timetable> editTimetable(@RequestBody Timetable tableToEdit){
		try {
			Timetable table = ttService.getOne(tableToEdit.getId());
			ttService.save(tableToEdit);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ForbiddenException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
}
