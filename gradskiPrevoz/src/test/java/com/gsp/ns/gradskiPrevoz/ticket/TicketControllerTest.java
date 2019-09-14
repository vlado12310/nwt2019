package com.gsp.ns.gradskiPrevoz.ticket;

import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_ACTIVATEDTICKET_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_INVALID_TICKET_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_TICKEVALIDVERIFICATION_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_TICKETNOZONES_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_TICKET_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_LINE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_UNACTIVATEDTICKET_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.DB_USERELDERLY_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.INVALID_TICKETTYPE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE2_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE3_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE4_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.gsp.ns.gradskiPrevoz.DTO.TicketVerification;
import com.gsp.ns.gradskiPrevoz.DTO.TransferTicket;
import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TicketControllerTest {
	@Autowired
    private TestRestTemplate restTemplate;
	
	private static final String URL_PREFIX = "/api/ticket";
	
	@Test
	public void testBuyTicketWhenTicketTypeDoesNotExist_RequiredRoleUser(){
		TicketType ticketType = new TicketType();
		ticketType.setId(INVALID_TICKETTYPE_ID);
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USER", "admin").postForEntity(URL_PREFIX, ticketType, Ticket.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void testBuyTicketWhenPricelistItemDontExists_RequiredRoleUser(){
		TicketType ticketType = new TicketType();
		ticketType.setId(DB_TICKETTYPE4_ID);
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USER", "admin").postForEntity(URL_PREFIX, ticketType, Ticket.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void testBuyTicketWhenUserDontHaveRequiredStatus_RequiredRoleUser(){
		TicketType ticketType = new TicketType();
		ticketType.setId(DB_TICKETTYPE3_ID);
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USERELDERLY", "admin").postForEntity(URL_PREFIX, ticketType, Ticket.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
	}


	@Test
	public void testBuyTicketWhenUserDontHaveEnoughFunds_RequiredRoleUser(){
		TicketType ticketType = new TicketType();
		ticketType.setId(DB_TICKETTYPE_ID);
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USER", "admin").postForEntity(URL_PREFIX, ticketType, Ticket.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
	}
	@Test
	public void testBuyTicketSuccess_RequiredRoleUser(){
		TicketType ticketType = new TicketType();
		ticketType.setId(DB_TICKETTYPE3_ID);
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("ADMIN", "password").postForEntity(URL_PREFIX, ticketType, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX, ticketType, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("USER", "admin").postForEntity(URL_PREFIX, ticketType, Ticket.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Ticket addedTicket = response.getBody();
		assertNotNull(addedTicket.getPurchaseDate());
		assertNull(addedTicket.getTimeOfActivation());
	}
	@Test
	public void testActivateTicketWhenUserWantToActivateSomeoneElsesTicket_RequiredRoleUser(){
		Ticket ticket = new Ticket();
		ticket.setId(DB_TICKET_ID);
		
		HttpEntity<Ticket> ticketEntity = new HttpEntity<Ticket>(ticket);
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USERELDERLY", "admin")
				.exchange(URL_PREFIX + "/activate",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void testActivateTicketWhenTicketIsAlreadyActivated_RequiredRoleUser(){
		Ticket ticket = new Ticket();
		ticket.setId(DB_ACTIVATEDTICKET_ID);
		
		HttpEntity<Ticket> ticketEntity = new HttpEntity<Ticket>(ticket);
		
		
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USER", "admin")
				.exchange(URL_PREFIX + "/activate",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}
	@Test
	public void testActivateTicketSuccess_RequiredRoleUser(){
		Ticket ticket = new Ticket();
		ticket.setId(DB_TICKET_ID);
		
		HttpEntity<Ticket> ticketEntity = new HttpEntity<Ticket>(ticket);
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("ADMIN", "password")
				.exchange(URL_PREFIX + "/activate",HttpMethod.PUT, ticketEntity, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("CONDUCTER", "password")
				.exchange(URL_PREFIX + "/activate",HttpMethod.PUT, ticketEntity, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("USER", "admin")
				.exchange(URL_PREFIX + "/activate",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Ticket activatedTicket = response.getBody();
		assertNotNull(activatedTicket.getTimeOfActivation());
	}
	@Test
	public void testTransferTicketWhenUserWantToActivateSomeoneElsesTicket_RequiredRoleUser(){
		User userWhoReceiveTicket = new User();
		userWhoReceiveTicket.setIdUser(DB_USERELDERLY_ID);

		Ticket ticketToTransfer = new Ticket();
		ticketToTransfer.setId(DB_TICKET_ID);
		TransferTicket ticket = new TransferTicket(userWhoReceiveTicket, ticketToTransfer);
		
		HttpEntity<TransferTicket> ticketEntity = new HttpEntity<TransferTicket>(ticket);
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USERELDERLY", "admin")
				.exchange(URL_PREFIX + "/transfer",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void testTransferTicketWhenTicketIsActivated_RequiredRoleUser(){
		User userWhoReceiveTicket = new User();
		userWhoReceiveTicket.setIdUser(DB_USERELDERLY_ID);

		Ticket ticketToTransfer = new Ticket();
		ticketToTransfer.setId(DB_ACTIVATEDTICKET_ID);
		TransferTicket ticket = new TransferTicket(userWhoReceiveTicket, ticketToTransfer);
		
		HttpEntity<TransferTicket> ticketEntity = new HttpEntity<TransferTicket>(ticket);
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("USER", "admin")
				.exchange(URL_PREFIX + "/transfer",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}
	@Test
	public void testTransferTicketSuccess_RequiredRoleUser(){
		User userWhoReceiveTicket = new User();
		userWhoReceiveTicket.setIdUser(DB_USERELDERLY_ID);

		Ticket ticketToTransfer = new Ticket();
		ticketToTransfer.setId(DB_UNACTIVATEDTICKET_ID);
		TransferTicket ticket = new TransferTicket(userWhoReceiveTicket, ticketToTransfer);
		
		HttpEntity<TransferTicket> ticketEntity = new HttpEntity<TransferTicket>(ticket);
		ResponseEntity<Ticket> response = restTemplate.withBasicAuth("CONDUCTER", "password")
				.exchange(URL_PREFIX + "/transfer",HttpMethod.PUT, ticketEntity, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("ADMIN", "password")
				.exchange(URL_PREFIX + "/transfer",HttpMethod.PUT, ticketEntity, Ticket.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("USER", "admin")
				.exchange(URL_PREFIX + "/transfer",HttpMethod.PUT, ticketEntity, Ticket.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	@Test
	public void testVerifyTicketWhenTicketIsNotFound_RequiredRoleConducter(){
		Ticket ticketToVerify = new Ticket();
		ticketToVerify.setId(DB_INVALID_TICKET_ID);
		Line line = new Line();
		line.setId(DB_LINE_ID);
		TicketVerification verTicket = new TicketVerification(ticketToVerify, line);
		
		ResponseEntity<?> response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX + "/verify" ,verTicket,void.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	@Test
	public void testVerifyTicketTicketIsNotActivated_RequiredRoleConducter(){
		Ticket ticketToVerify = new Ticket();
		ticketToVerify.setId(DB_UNACTIVATEDTICKET_ID);
		Line line = new Line();
		line.setId(DB_LINE_ID);
		TicketVerification verTicket = new TicketVerification(ticketToVerify, line);
		
		ResponseEntity<?> response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX + "/verify" ,verTicket,void.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
	}
	@Test
	public void testVerifyTicketWhenTicketIsExpired_RequiredRoleConducter(){
		Ticket ticketToVerify = new Ticket();
		ticketToVerify.setId(DB_ACTIVATEDTICKET_ID);
		Line line = new Line();
		line.setId(DB_LINE_ID);
		TicketVerification verTicket = new TicketVerification(ticketToVerify, line);
		
		ResponseEntity<?> response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX + "/verify" ,verTicket,void.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
	}
	@Test
	public void testVerifyTicketIsNotValidInLineZone_RequiredRoleConducter(){
		Ticket ticketToVerify = new Ticket();
		ticketToVerify.setId(DB_TICKETNOZONES_ID);
		Line line = new Line();
		line.setId(DB_LINE_ID);
		TicketVerification verTicket = new TicketVerification(ticketToVerify, line);
		
		ResponseEntity<?> response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX + "/verify" ,verTicket,void.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
	}
	@Test
	public void testVerifySuccess_RequiredRoleConducter(){
		System.out.println("a\n\n\n");
		Ticket ticketToVerify = new Ticket();
		ticketToVerify.setId(DB_TICKEVALIDVERIFICATION_ID);
		Line line = new Line();
		line.setId(DB_LINE_ID);
		TicketVerification verTicket = new TicketVerification(ticketToVerify, line);
		
		ResponseEntity<?> response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX + "/verify" ,verTicket,void.class);
		System.out.println("a\n\n\na");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
}
