package com.gsp.ns.gradskiPrevoz.line;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.user.UserService;

@RestController
@RequestMapping(value="/api/line")
public class LineController {
	@Autowired
	LineService lineService;
	@Autowired
	UserService userService;
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Line> addLine(@RequestBody Line lineDTO, Authentication auth){
		try{
			Line lineToAdd = new Line(lineDTO.getId(), lineDTO.getStations(), lineDTO.getZone(), lineDTO.getName());
			Line lineDB = lineService.addOne(lineToAdd);
			return new ResponseEntity<Line>(lineDB, HttpStatus.CREATED);
		}catch(ConflictException e){
			throw new ConflictException("Neuspesno dodavanje, linija sa imenom " + lineDTO.getName() + " vec postoji.");
		}
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Line>> getLine(){
		List<Line> lines = lineService.findAll();
		return new ResponseEntity<List<Line>>(lines, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> DeleteLine(@PathVariable Long id){
		try{
			Line line = lineService.findOne(id);
			lineService.delete(line);
			return new ResponseEntity<>( HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Line> getLineById(@PathVariable("id") Long id){
		try{
			Line line = lineService.findOne(id);
			return new ResponseEntity<Line>(line, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
