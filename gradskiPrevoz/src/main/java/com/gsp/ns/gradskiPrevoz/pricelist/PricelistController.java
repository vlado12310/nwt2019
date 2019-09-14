package com.gsp.ns.gradskiPrevoz.pricelist;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.exceptions.PricelistNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
@RestController
@RequestMapping(value = "api/pricelist")
public class PricelistController {
	@Autowired
	private PricelistService service;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Pricelist>> findAll(){
		List<Pricelist> pricelists = service.findAll();
		return new ResponseEntity<>(pricelists, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/{id}" , produces = "application/json")
	public ResponseEntity<Pricelist> getOne(@PathVariable("id") Long id){
		try{
		Pricelist pricelist = service.findOne(id);
		return new ResponseEntity<>(pricelist, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			throw new ResourceNotFoundException("Cenovnik sa id-em " + id + " nije pronadjen");
		}
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Pricelist> save(@RequestBody Pricelist pricelist){
		try{
			Pricelist pl = service.save(pricelist);
			return new ResponseEntity<Pricelist>(pl,  HttpStatus.OK);

		}catch(InvalidParametersException invalidParams){
			throw new InvalidParametersException("Neuspesno dodavanje. Pocetni datum ne sme biti veci od krajnjeg.");
		}catch(ConflictException invalidDates){
			throw new ConflictException("Neuspesno dodavanje. Uneseni datumi se poklapaju sa datumima drugih cenovnika.");
		}
			
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Pricelist> change(@RequestBody Pricelist pricelist){
		
		
		try{
			Pricelist plFromDb = service.findOne(pricelist.getId());
			Pricelist pl = service.modify(plFromDb , pricelist);
			return new ResponseEntity<Pricelist>(pl,  HttpStatus.OK);

		}catch(InvalidParametersException invalidParams){
			throw new InvalidParametersException("Neuspesna izmena. Pocetni datum ne sme biti veci od krajnjeg.");
		}catch(ConflictException invalidDates){
			throw new ConflictException("Neuspesna izmena. Uneseni datumi se poklapaju sa datumima drugih cenovnika.");
		}catch(ResourceNotFoundException e){
			throw new ResourceNotFoundException("Cenovnik sa id-em " + pricelist.getId() + " nije pronadjen.");
		}
			
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		Pricelist pricelist; 
		try{
			pricelist = service.findOne(id);
			service.delete(pricelist);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ConflictException e){
			throw new ConflictException("Neuspesno brisanje. Ne mozete izbrisati cenovnik koji ima definisane stavke");
		}
		

	}
	//mogu svi korisnici
	@GetMapping(value = "/active")
	public ResponseEntity<Pricelist> getTicketPrices(){
		try{
			Pricelist pl = service.getActivePricelist();
			return new ResponseEntity<>(pl, HttpStatus.OK);
		}catch(PricelistNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
