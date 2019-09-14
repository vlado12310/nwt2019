package com.gsp.ns.gradskiPrevoz.ticket;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.DTO.TicketVerification;
import com.gsp.ns.gradskiPrevoz.DTO.TransferTicket;
import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.NoFundsException;
import com.gsp.ns.gradskiPrevoz.exceptions.PricelistNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.RequiredStatusException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.line.LineService;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User;
import com.gsp.ns.gradskiPrevoz.user.UserService;

@RestController
@RequestMapping(value="/api/ticket")
public class TicketController {
	@Autowired
	TicketService ticketService;
	@Autowired
	LineService lineService;
	@Autowired
	UserService userService;
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Ticket> buyTicket(@RequestBody TicketType ticketType, Authentication auth){
		try{
			User sessionUser = userService.findByEmail(auth.getName());
			Ticket ticket = ticketService.buyTicket(ticketType, sessionUser);
			System.out.println("control user : " + ticket.getUser());
			return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
		}catch(NoFundsException e){
			throw e;
		}catch(PricelistNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(RequiredStatusException e){
			throw e;
		}
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ticket>> getTickets(){
		List<Ticket> tickets = ticketService.findAll();
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id){
		try{
			Ticket tickets = ticketService.getOne(id);
			return new ResponseEntity<Ticket>(tickets, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> DeleteTicket(@RequestBody Ticket ticketToDelete){
		try{
			Ticket ticket = ticketService.getOne(ticketToDelete.getId());
			ticketService.delete(ticket);
			return new ResponseEntity<>( HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ForbiddenException e){
			//ukoliko je karta vec aktivirana ne moze se izbrisati.
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
	}
	@PutMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Ticket> activateTicket(@RequestBody Ticket ticketToActivate, Authentication auth){
		try{
			User sessionUser = userService.findByEmail(auth.getName());
			Ticket ticketFromDb = ticketService.getOne(ticketToActivate.getId());
			Ticket activatedTicket = ticketService.activate(ticketFromDb, sessionUser);
			return new ResponseEntity<Ticket>(activatedTicket, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Ticket> transferTicket(@RequestBody TransferTicket transferTicket, Authentication auth){
		try{
			User userWhoTransfer = userService.findByEmail(auth.getName());
			Ticket ticketToTransfer = ticketService.getOne(transferTicket.getTicket().getId());

			User userWhoReceive = userService.getOne(transferTicket.getUser().getIdUser());
			Ticket transferedTicket = ticketService.transfer(ticketToTransfer, userWhoTransfer, userWhoReceive);
			return new ResponseEntity<Ticket>(transferedTicket, HttpStatus.OK);
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(ForbiddenException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}catch(ConflictException e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	@PostMapping(value="/verify",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('CONDUCTER')")
	public ResponseEntity<?> verifyTicket(@RequestBody TicketVerification ticketVerification){
		try{
			Ticket ticketToVerify = ticketService.getOne(ticketVerification.getTicket().getId());
			Line lineDB = lineService.findOne(ticketVerification.getLine().getId());
			Boolean isVerified = ticketService.verifyTicket(ticketToVerify, lineDB);
			if (isVerified){
				return new ResponseEntity<>(HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}catch(ResourceNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value="/userTickets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTicketsByUser(Authentication auth){
		User sessionUser = userService.findByEmail(auth.getName());
		List<Ticket> tickets = ticketService.getTicketsByUser(sessionUser);
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}

	@GetMapping(value="/userActiveTicketsEmail/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getActiveTicketsByEmail(@PathVariable("email") String email, Authentication auth){
		User u = userService.findByEmail(email);
		List<Ticket> tickets = ticketService.getActiveTiketsByEmail(u);
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
}