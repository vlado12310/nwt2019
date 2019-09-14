package com.gsp.ns.gradskiPrevoz.PricelistItemUnitTest;

import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_INVALID_PRICELIST_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistItemConstants.TICKETTYPE_DELETE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistItemConstants.PL_MODIFY_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistItemConstants.PL_DELETE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST1_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST2_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistItemConstants.INVALID_PRICE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistItemConstants.VALID_PRICE;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE2_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE3_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.DB_TICKETTYPE_ID;
import static com.gsp.ns.gradskiPrevoz.constants.TicketTypeConstants.INVALID_ID;
import static org.junit.Assert.assertEquals;

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

import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItemId;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;

@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PricelistItemControllerTest {

	private static final String URL_PREFIX = "/api/pricelistItem";
	@Autowired
	private TestRestTemplate restTemplate;
	@Before
	public void setUp(){
		
	}
	@Test
	public void addNewItemWhenItemWithSameTicketTypeIsAlreadyDefinedInPricelist_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_PRICELIST1_ID, DB_TICKETTYPE_ID);
		itemToAdd.setId(id);
		ResponseEntity<PricelistItem> response =
		restTemplate.withBasicAuth("ADMIN","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
		
	}
	@Test
	public void addNewItemWhenPriceOfItemIsLessThan1_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(INVALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_PRICELIST1_ID, DB_TICKETTYPE2_ID);
		itemToAdd.setId(id);
		ResponseEntity<PricelistItem> response =
		restTemplate.withBasicAuth("ADMIN","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		
		
	}
	@Test
	public void addNewItemWhenPricelistDontExist_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_INVALID_PRICELIST_ID, DB_TICKETTYPE2_ID);
		itemToAdd.setId(id);
		ResponseEntity<PricelistItem> response =
		restTemplate.withBasicAuth("ADMIN","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	
	}
	@Test
	public void addNewItemSuccess_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_PRICELIST2_ID, DB_TICKETTYPE2_ID);
		
		itemToAdd.setId(id);
		ResponseEntity<PricelistItem> response =
		restTemplate.withBasicAuth("USER","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
		response =
		restTemplate.withBasicAuth("CONDUCTER","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
		response =
		restTemplate.withBasicAuth("ADMIN","password").postForEntity(URL_PREFIX, itemToAdd, PricelistItem.class);
		
		PricelistItem addedItem = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(DB_PRICELIST2_ID, addedItem.getId().getPricelistId());
		assertEquals(DB_TICKETTYPE2_ID.longValue(), addedItem.getTicketType().getId());
	}
	//pokusaj izmene stavke cenovnika kada postoji kupljena karta
	@Test
	public void modifyFail_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_PRICELIST1_ID, DB_TICKETTYPE_ID);
		itemToAdd.setId(id);
		
		HttpEntity<PricelistItem> itemEntity = new HttpEntity<PricelistItem>(itemToAdd);
		ResponseEntity<PricelistItem> response = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}
	@Test
	public void modifyFailWhenPriceIsLessThan1_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(INVALID_PRICE);
		PricelistItemId id = new PricelistItemId(PL_MODIFY_ID, TICKETTYPE_DELETE_ID);
		itemToAdd.setId(id);
		
		HttpEntity<PricelistItem> itemEntity = new HttpEntity<PricelistItem>(itemToAdd);
		ResponseEntity<PricelistItem> response = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
	}
	@Test
	public void modifyFailWhenItemIsNotFound_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(DB_PRICELIST2_ID, DB_TICKETTYPE2_ID);
		itemToAdd.setId(id);
		
		HttpEntity<PricelistItem> itemEntity = new HttpEntity<PricelistItem>(itemToAdd);
		ResponseEntity<PricelistItem> response = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void modifySuccess_RequiredRoleAdmin(){
		PricelistItem itemToAdd = new PricelistItem();
		itemToAdd.setPrice(VALID_PRICE);
		PricelistItemId id = new PricelistItemId(PL_MODIFY_ID, DB_TICKETTYPE2_ID);
		itemToAdd.setId(id);
		
		HttpEntity<PricelistItem> itemEntity = new HttpEntity<PricelistItem>(itemToAdd);
		ResponseEntity<PricelistItem> response = restTemplate.withBasicAuth("USER", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
		response = restTemplate.withBasicAuth("CONDUCTER", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		
		response = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, itemEntity,PricelistItem.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	//pokusaj brisanja kada postoji kupljena karta
	@Test
	public void deleteItemFail_RequiredRoleAdmin(){
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/"+ DB_PRICELIST1_ID + "/" + DB_TICKETTYPE_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
	}
	@Test
	public void deleteItemSuccess(){
        ResponseEntity<Object> result = restTemplate.withBasicAuth("USER", "password").exchange(URL_PREFIX + "/"+  PL_DELETE_ID + "/" + TICKETTYPE_DELETE_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        
        result = restTemplate.withBasicAuth("CONDUCTER", "password").exchange(URL_PREFIX + "/"+  PL_DELETE_ID + "/" + TICKETTYPE_DELETE_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        
        result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/"+  PL_DELETE_ID + "/" + TICKETTYPE_DELETE_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
}
