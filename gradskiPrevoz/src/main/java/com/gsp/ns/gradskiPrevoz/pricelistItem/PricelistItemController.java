package com.gsp.ns.gradskiPrevoz.pricelistItem;

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

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistService;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketTypeService;

@RestController
@RequestMapping(value = "api/pricelistItem")
public class PricelistItemController {
	
	@Autowired
	PricelistItemService service;
	@Autowired
	PricelistService plService;
	@Autowired
	TicketTypeService ticketService;
	
	@GetMapping
	public ResponseEntity<List<PricelistItem>> findAll(){
		List<PricelistItem> items = service.findAll();
		return new ResponseEntity<List<PricelistItem>>(items, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PricelistItem> save(@RequestBody PricelistItem itemToAdd){
		try{
			Pricelist pl = plService.findOne(itemToAdd.getId().getPricelistId());
			PricelistItem addedItem = service.save(itemToAdd, pl);
			return new ResponseEntity<PricelistItem>(addedItem, HttpStatus.OK);
		}catch(EntityNotFoundException entityNotFound){
			throw entityNotFound;
		}catch(InvalidParametersException invalidParameters){
			throw new InvalidParametersException("Cena ne sme biti negativna.");
		}catch(ConflictException e){
			throw new ConflictException("Izabrana karta je vec definisana u cenovniku.");
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value="/{plId}/{ttId}")
	public ResponseEntity<?> delete(@PathVariable("plId") Long plId, @PathVariable("ttId") Long ttId){
		PricelistItem item;
		try{
			item = service.findOne(new PricelistItemId(plId, ttId));
			service.delete(item);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(EntityNotFoundException e){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch(ConflictException e){
			throw new ConflictException("Ne moze se izbrisati ova stavka cenovnika zato sto postoji kupljena karta.");
		}
		

	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<PricelistItem> modify(@RequestBody PricelistItem item){
		try{
			Pricelist pricelist = plService.findOne(item.getId().getPricelistId());
			PricelistItem plToChange = service.findOne(item.getId());
			PricelistItem changedItem = service.modify(plToChange, item, pricelist);
			return new ResponseEntity<>(changedItem, HttpStatus.OK);
		}catch(EntityNotFoundException exception){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(InvalidParametersException e){
			throw new InvalidParametersException("Cena ne sme biti negativna.");
		}catch(ConflictException e){
			throw new ConflictException("Ne moze se izmeniti ova stavku cenovnika zato sto postoji kupljena karta.");

		}
		
		
	}

}
