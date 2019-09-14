package com.gsp.ns.gradskiPrevoz.userTest;

import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.INVALID_TICKETTYPE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE3_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class UserController {

	@Autowired
    private TestRestTemplate restTemplate;
	
	private static final String URL_PREFIX = "/api/user";
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllUsers(){
		
		ArrayList<User> users;
		
		ResponseEntity<Object> response = restTemplate.getForEntity(URL_PREFIX,  Object.class);
		users = (ArrayList<User>) response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(users);
	}
	
	@Test
	public void getMyTicketsRole() {
		
		ResponseEntity<Object> response = restTemplate.withBasicAuth("ADMIN", "password").getForEntity(URL_PREFIX+"/myTickets", Object.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
		response = restTemplate.getForEntity(URL_PREFIX, Object.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("CONDUCTER", "password").getForEntity(URL_PREFIX+"/myTickets", Object.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

		response = restTemplate.withBasicAuth("USER", "admin").getForEntity(URL_PREFIX+"/myTickets", Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = (ArrayList<Ticket>)response.getBody();
		assertNotNull(tickets);
	}
	
	
	@Test
	public void userRegister() {
		User user = new User();
		ResponseEntity<User> response = restTemplate.postForEntity(URL_PREFIX, user, User.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		user.setEmail("USER");
		response = restTemplate.postForEntity(URL_PREFIX, user, User.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		user.setEmail("Peraperic");
		user.setPassword("Peraperic");
		
		response = restTemplate.withBasicAuth("CONDUCTER", "password").postForEntity(URL_PREFIX, user, User.class);
		assertEquals(HttpStatus.BAD_REQUEST,  response.getStatusCode());
		
		
		response = restTemplate.postForEntity(URL_PREFIX, user, User.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
