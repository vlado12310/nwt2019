package com.gsp.ns.gradskiPrevoz.ticketTypeUnitTest;

import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.INVALID_DURATION;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.INVALID_NAME;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.VALID_DURATION;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.VALID_NAME;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.VALID_USER_STATUS;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST_COUNT;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DELETE_PRICELIST_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
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

import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.zone.Zone;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TicketTypeControllerTest {
	@Autowired
    private TestRestTemplate restTemplate;

	
	private ArrayList<Zone> testZones;
	private static final String URL_PREFIX = "/api/ticketType";
	@Before
	public void setUp(){
		testZones = new ArrayList<Zone>();
		testZones.add(new Zone(1L, "Zona", null));
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindALLPricelistsSuccess() throws Exception {
		ResponseEntity<Object> responseEntity =
	            restTemplate.withBasicAuth("USER", "password").
	            getForEntity(URL_PREFIX, Object.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		responseEntity =
	            restTemplate.withBasicAuth("CONDUCTER", "password").
	            getForEntity(URL_PREFIX, Object.class);
	            
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            getForEntity(URL_PREFIX, Object.class);
		ArrayList<TicketType> ticketTypes = (ArrayList<TicketType>) responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(ticketTypes);
		assertEquals(true, ticketTypes.size() > 0);
	}
	@Test
	public void addNTicketTypeWhenNameIsEmptyString_RequiredRoleAdmin(){
		TicketType ttToAdd = new TicketType();
		ttToAdd.setDurationInHours(VALID_DURATION);
		ttToAdd.setName(INVALID_NAME);
		ttToAdd.setRequiredStatus(VALID_USER_STATUS);
		ttToAdd.setZones(testZones);
		ResponseEntity<TicketType> responseEntity =	
				restTemplate.withBasicAuth("ADMIN", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void addTicketTypeWhenDurationIsLessThan1_RequiredRoleAdmin(){
		TicketType ttToAdd = new TicketType();
		ttToAdd.setDurationInHours(INVALID_DURATION);
		ttToAdd.setName(VALID_NAME);
		ttToAdd.setRequiredStatus(VALID_USER_STATUS);
		ttToAdd.setZones(testZones);
		ResponseEntity<TicketType> responseEntity =	
				restTemplate.withBasicAuth("ADMIN", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void addNTicketTypeWhenSizeOfZonesEquals0_RequiredRoleAdmin(){
		TicketType ttToAdd = new TicketType();
		ttToAdd.setDurationInHours(VALID_DURATION);
		ttToAdd.setName(VALID_NAME);
		ttToAdd.setRequiredStatus(VALID_USER_STATUS);
		ttToAdd.setZones(new ArrayList<Zone>());
		
		ResponseEntity<TicketType> responseEntity =	
				restTemplate.withBasicAuth("ADMIN", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void addTicketTypeWhenRequiredStatusIsNull_RequiredRoleAdmin(){
		TicketType ttToAdd = new TicketType();
		ttToAdd.setDurationInHours(VALID_DURATION);
		ttToAdd.setName(VALID_NAME);
		ttToAdd.setRequiredStatus(null);
		ttToAdd.setZones(testZones);
		ResponseEntity<TicketType> responseEntity =	
				restTemplate.withBasicAuth("ADMIN", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	
	@Test
	public void addTicketTypeSuccess_RequiredRoleAdmin(){
		TicketType ttToAdd = new TicketType();
		ttToAdd.setDurationInHours(VALID_DURATION);
		ttToAdd.setName(VALID_NAME);
		ttToAdd.setRequiredStatus(VALID_USER_STATUS);
		ttToAdd.setZones(testZones);
		ResponseEntity<TicketType> responseEntity =	
				restTemplate.withBasicAuth("USER", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		responseEntity =	
				restTemplate.withBasicAuth("CONDUCTER", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		responseEntity =	
				restTemplate.withBasicAuth("ADMIN", "password")
				.postForEntity(URL_PREFIX, ttToAdd ,TicketType.class);
		
		TicketType addedTicketType = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(VALID_NAME, addedTicketType.getName());
		assertEquals(VALID_DURATION, addedTicketType.getDurationInHours());
		assertEquals(VALID_USER_STATUS, addedTicketType.getRequiredStatus());
		assertEquals(true, addedTicketType.getZones().size() > 0);
		
	}
	@Test
	public void modifyTicketTypeWhenDurationIsLessThan1_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(DB_TICKETTYPE_ID);
		ttToModify.setDurationInHours(INVALID_DURATION);
		ttToModify.setName(VALID_NAME);
		ttToModify.setRequiredStatus(VALID_USER_STATUS);
		ttToModify.setZones(testZones);
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void modifyTicketTypeWhenNameIsEmptyString_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(DB_TICKETTYPE_ID);
		ttToModify.setDurationInHours(VALID_DURATION);
		ttToModify.setName(INVALID_NAME);
		ttToModify.setRequiredStatus(VALID_USER_STATUS);
		ttToModify.setZones(testZones);
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void modifyTicketTypeWhenSizeOfZonesEquals0_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(DB_TICKETTYPE_ID);
		ttToModify.setDurationInHours(VALID_DURATION);
		ttToModify.setName(VALID_NAME);
		ttToModify.setRequiredStatus(VALID_USER_STATUS);
		ttToModify.setZones(new ArrayList<Zone>());
		
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void modifyTicketTypeWhenRequiredStatusIsNull_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(DB_TICKETTYPE_ID);
		ttToModify.setDurationInHours(VALID_DURATION);
		ttToModify.setName(VALID_NAME);
		ttToModify.setRequiredStatus(null);
		ttToModify.setZones(testZones);
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);

		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
		
	}
	@Test
	public void modifyTicketTypeWhenTickeTypeIsNotFound_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(INVALID_ID);
		ttToModify.setDurationInHours(VALID_DURATION);
		ttToModify.setName(VALID_NAME);
		ttToModify.setRequiredStatus(VALID_USER_STATUS);
		ttToModify.setZones(testZones);
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
	}
	@Test
	public void modifyTicketTypeSuccess_RequiredRoleAdmin(){
		TicketType ttToModify = new TicketType();
		ttToModify.setId(DB_TICKETTYPE_ID);
		ttToModify.setDurationInHours(VALID_DURATION);
		ttToModify.setName(VALID_NAME);
		ttToModify.setRequiredStatus(VALID_USER_STATUS);
		ttToModify.setZones(testZones);
		HttpEntity<TicketType> entity = new HttpEntity<TicketType>(ttToModify);
		ResponseEntity<TicketType> responseEntity =
	            restTemplate.withBasicAuth("USER", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		responseEntity =
	            restTemplate.withBasicAuth("CONDUCTER", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity,TicketType.class);
		
		TicketType modifiedTicketType = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(VALID_NAME, modifiedTicketType.getName());
		assertEquals(VALID_DURATION, modifiedTicketType.getDurationInHours());
		assertEquals(VALID_USER_STATUS, modifiedTicketType.getRequiredStatus());
		assertEquals(true, modifiedTicketType.getZones().size() > 0);
	}
	@Test
	public void deleteWhenTicketTypeIsNotFound(){
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/"+ INVALID_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
	@Test
	public void deleteWhenTicketExcistsAsItemInPricelist(){
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/"+ DB_TICKETTYPE_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}
	@Test
	public void deleteSuccess(){
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/"+ DB_TICKETTYPE4_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
