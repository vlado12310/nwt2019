package com.gsp.ns.gradskiPrevoz.ticketType;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "api/ticketType")
public class TicketTypeController {
	@Autowired
	TicketTypeService service;
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<TicketType>> findAll(){
		List<TicketType> types =  service.findAll();
		return new ResponseEntity<List<TicketType>>(types, HttpStatus.OK);
	}
/*	@GetMapping(value = "/{id}")
	public ResponseEntity<TicketType> getOne(@PathVariable("id") Long id ){
		try{
			TicketType ticketType = service.findById(id);
			return new ResponseEntity<>(ticketType, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}*/
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TicketType> create(@RequestBody TicketType type){
		TicketType typeToCreate = service.addNew(type);
		return new ResponseEntity<TicketType>(typeToCreate, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		TicketType typeToDelete;
		try{
			typeToDelete = service.findById(id);
			service.delete(typeToDelete);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(EntityNotFoundException e){
			
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch(ForbiddenException e){
			throw new ForbiddenException("Neuspesno brisanje. Ne mozete izbrisati kartu ako postoji kao stavka u cenovniku.");
		}
		
		
		
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<TicketType> modify(@RequestBody TicketType newType){
		 
		try{
			TicketType typeToModify = service.findById(newType.getId());
			TicketType modifiedType = service.modify(typeToModify, newType);
			return new ResponseEntity<TicketType>(modifiedType, HttpStatus.OK);
		}catch(EntityNotFoundException e){
			throw new ResourceNotFoundException();
		}catch(InvalidParametersException e){
			throw new InvalidParametersException("Uneti podaci nisu validni");
		}
		
	}
}
