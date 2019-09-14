package com.gsp.ns.gradskiPrevoz.login;

import static com.gsp.ns.gradskiPrevoz.constants.TicketConstants.INVALID_TICKETTYPE_ID;
import static org.junit.Assert.assertEquals;

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

import com.gsp.ns.gradskiPrevoz.login.LoginDTO;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	
	private static final String URL_PREFIX = "/api/login";
	
	@Test
	public void logInBadUserName(){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("sf");
		loginDTO.setUsername("admin");
		
		ResponseEntity<Ticket> response = restTemplate.postForEntity(URL_PREFIX, loginDTO, Ticket.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void logInBadPassword(){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("USER");
		loginDTO.setPassword("sad");
		
		ResponseEntity<Ticket> response = restTemplate.postForEntity(URL_PREFIX, loginDTO, Ticket.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void logIn(){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("USER");
		loginDTO.setPassword("admin");
		
		ResponseEntity<Ticket> response = restTemplate.postForEntity(URL_PREFIX, loginDTO, Ticket.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
}
