package com.gsp.ns.gradskiPrevoz.ticket;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.NoFundsException;
import com.gsp.ns.gradskiPrevoz.exceptions.PricelistNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.RequiredStatusException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistService;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketTypeService;
import com.gsp.ns.gradskiPrevoz.user.User;
import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;
import com.gsp.ns.gradskiPrevoz.user.UserService;


@Service
public class TicketService  {
	@Autowired
	TicketRepository repo;
	@Autowired
	PricelistService pricelistService;
	@Autowired
	TicketTypeService ticketTypeService;
	@Autowired
	UserService userService;
	public List<Ticket> findAll(){
		return repo.findAll();
	}
	public Ticket getOne(Long id) {
		Optional<Ticket> ticket = repo.findById(id);
		if (ticket.isPresent()){
			return repo.getOne(id);
		}else{
			throw new ResourceNotFoundException();
		}
		
	}
	public Ticket save(Ticket ticket) {
		return repo.save(ticket);
	}
	//ispravi! karta ne sme da se brise
	public void delete(Ticket ticket) {
		if (ticket.getTimeOfActivation() == null){
			repo.delete(ticket);
		}else{
			throw new ConflictException();
		}
		
	}
	public Ticket activate(Ticket ticketFromDb, User sessionUser) {
		//korisnik sme samo svoje karte da aktivira
		if (ticketFromDb.getUser().getIdUser() != sessionUser.getIdUser()){
			throw new ForbiddenException();
		}
		//ukoliko je karta vec aktivirana ne moze da se aktivira opet
		System.out.println(ticketFromDb.getTimeOfActivation());
		if (ticketFromDb.getTimeOfActivation() != null){
			throw new ConflictException();
		}
		ticketFromDb.setTimeOfActivation(new Date());
		Ticket activatedTicket = save(ticketFromDb);
		return activatedTicket;
	}

	public Ticket transfer(Ticket ticketToTransfer, User userWhoTransfer, User userWhoReceive) {
		//provera da li user sa sesije salje zahtev
		if (ticketToTransfer.getUser().getIdUser() != userWhoTransfer.getIdUser()){
			throw new ForbiddenException();
		}
		//ukoliko je karta aktivirana ne moze da se transferuje
		System.out.println(ticketToTransfer.getTimeOfActivation());
		if (ticketToTransfer.getTimeOfActivation() != null){
			System.out.println("USAO");
			throw new ConflictException();
		}
		
		ticketToTransfer.setUser(userWhoReceive);
		Ticket transferedTicket = repo.save(ticketToTransfer);
		System.out.println(transferedTicket.getUser().getIdUser());
		return transferedTicket;
	}
	public Boolean verifyTicket(Ticket ticket, Line line){	
		Instant now = Instant.now(); //current date
		Instant before = now.minus(Duration.ofHours(ticket.getTicketType().getDurationInHours()));
		Date dateBefore = Date.from(before);

		if (ticket.getTimeOfActivation() == null 
				|| ticket.getTimeOfActivation().before(dateBefore)){
			return false;
		}else{
			return true;
		}	
	}
	public Ticket buyTicket(TicketType ticketTypeToBuy, User sessionUser) {
		Pricelist pricelist;
		TicketType type;
		PricelistItem item;
		try{
			
			pricelist = pricelistService.getActivePricelist();
			type = ticketTypeService.findById(ticketTypeToBuy.getId());
			item = pricelist.findPricelistItem(type);

		}catch(PricelistNotFoundException e){
			throw e;
		}catch(ResourceNotFoundException e){
			throw e;
		}
		
		//ne postoji stavka u cenovniku
		
	
		if (type.getRequiredStatus() != UserStatus.STANDARD){
			if (type.getRequiredStatus() != sessionUser.getStatus()){
				throw new RequiredStatusException();
			}
		}
		//nema dovoljno novca
		if (item.getPrice() > sessionUser.getBalance()){
			throw new NoFundsException();
		}

		sessionUser.setBalance(sessionUser.getBalance() - item.getPrice());
		userService.update(sessionUser);
		Ticket ticketToBuy = new Ticket();
		ticketToBuy.setUser(sessionUser);
		ticketToBuy.setPurchaseDate(new Date());
		ticketToBuy.setTicketType(type);
	
		Ticket savedTicket = repo.save(ticketToBuy);
		return savedTicket;
	}

	public List<Ticket> getTicketsByUser(User user){
		List<Ticket> allTickets = repo.findAll();
		List<Ticket> retVal = new ArrayList<Ticket>();
		for (Ticket t: allTickets) {
			if(t.getUser().getIdUser()==user.getIdUser()){ retVal.add(t);}
		}
		return retVal;
	}
	public List<Ticket> getActiveTiketsByEmail(User user){
		List<Ticket> allTickets = repo.findAll();
		List<Ticket> retVal = new ArrayList<Ticket>();
		for (Ticket t: allTickets) {
			if(t.getUser().getIdUser()==user.getIdUser() && this.verifyTicket(t,null)){
				retVal.add(t);
			}
		}
		return retVal;
	}
}
