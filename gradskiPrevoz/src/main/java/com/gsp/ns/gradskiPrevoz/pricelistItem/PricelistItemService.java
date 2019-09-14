package com.gsp.ns.gradskiPrevoz.pricelistItem;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistRepository;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.ticket.TicketRepository;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketTypeRepository;

@Service
public class PricelistItemService {

	@Autowired
	private PricelistItemRepository repository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private PricelistRepository pricelistRepository;
	@Autowired 
	private TicketTypeRepository ticketTypeRepository;
	public List<PricelistItem> findAll(){
		return repository.findAll();
	}
	
	public PricelistItem findOne(PricelistItemId id){
		List<PricelistItem> items = findAll();
		for (PricelistItem item : items){
			if (item.getId().equals(id)){
				return item;
			}
		}
		throw new EntityNotFoundException();

	}
	
	public PricelistItem save(PricelistItem item, Pricelist pl){
		if (item.getPrice() <= 0){
			throw new InvalidParametersException();
		}
		for (PricelistItem i : pl.getPricelistItems()){
			if (i.getTicketType().getId() == item.getId().getTicketTypeId()){
				throw new ConflictException();
			}
		}
		//item.setPricelist(pl);
		item.setPricelist(pl);
		item.setTicketType(ticketTypeRepository.getOne(item.getId().getTicketTypeId()));
		return repository.save(item);
	}
	//stavka ne sme da se menja ukoliko postoji kupljena karta
	public PricelistItem modify(PricelistItem itemFromDb, PricelistItem newItem, Pricelist pl ){
		List<Ticket> tickets = ticketRepository.findAll();
		for (Ticket ticket : tickets){
			Pricelist ticketPricelist = pricelistRepository.findByStartBeforeAndEndAfter(ticket.getPurchaseDate(), ticket.getPurchaseDate());
			if(ticketPricelist.getId() == itemFromDb.getPricelist().getId() && ticket.getTicketType().getId() == itemFromDb.getTicketType().getId()){
				throw new ConflictException();
			}
		}
		
		if (newItem.getPrice() <= 0){
			throw new InvalidParametersException();
		}
		itemFromDb.setPrice(newItem.getPrice());
		PricelistItem changedItem = repository.save(itemFromDb);
		return changedItem;
	}
	//Moze se obrisati stavka iz cenovnika samo ako ne postoji ni jedna kupljena karta
	public void delete(PricelistItem item){
		List<Ticket> tickets = ticketRepository.findAll();
		Pricelist pricelist = item.getPricelist();
		if (pricelist.getStart().after(new Date())){
			repository.delete(item);
			return;
		}
		for (Ticket ticket : tickets){
			Pricelist ticketPricelist = pricelistRepository.findByStartBeforeAndEndAfter(ticket.getPurchaseDate(), ticket.getPurchaseDate());
			if(ticketPricelist.getId() == item.getPricelist().getId() && ticket.getTicketType().getId() == item.getTicketType().getId()){
				throw new ConflictException();
			}
		}
		
		repository.delete(item);
	}
}
