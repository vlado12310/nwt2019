package com.gsp.ns.gradskiPrevoz.pricelistTest;



import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_INVALID_PRICELIST_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST1_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST1_STARTDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DB_PRICELIST_COUNT;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DELETE_PRICELIST_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.DELETE_PRICELIST_ID_INVALID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.EDIT_PRICELIST_ENDDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.EDIT_PRICELIST_ID;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.EDIT_PRICELIST_STARTDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_ENDDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_INVALID_ENDDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_INVALID_STARTDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_MATCH_ENDDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_MATCH_STARTDATE;
import static com.gsp.ns.gradskiPrevoz.constants.PricelistConstants.NEW_PRICELIST_STARTDATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;






@TestPropertySource(locations = "classpath:test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class PricelistControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

	
	
	private static final String URL_PREFIX = "/api/pricelist";

	
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	@Before
	public void init() throws ParseException{

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
		ArrayList<Pricelist> pricelists = (ArrayList<Pricelist>) responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(pricelists);
	}
	@Test
	public void testFindByIdSuccess_RequiredRoleAdmin() throws ParseException{
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("USER", "password").
	            getForEntity(URL_PREFIX + "/" + DB_PRICELIST1_ID, Pricelist.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		responseEntity =
	            restTemplate.withBasicAuth("CONDUCTER", "password").
	            getForEntity(URL_PREFIX + "/" + DB_PRICELIST1_ID, Pricelist.class);
	            
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            getForEntity(URL_PREFIX + "/" + DB_PRICELIST1_ID, Pricelist.class);
		Pricelist pricelist = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(pricelist);
		assertEquals(DB_PRICELIST1_ID, pricelist.getId().longValue());
		assertEquals(formatter.parse(DB_PRICELIST1_STARTDATE), pricelist.getStart());
	}
	@Test
	public void testFindByIdWhenPricelistNotFound_RequiredRoleAdmin(){
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            getForEntity(URL_PREFIX + "/" + DB_INVALID_PRICELIST_ID, Pricelist.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddNewPricelistSuccess_RequiredRoleAdmin() throws ParseException{
		Pricelist plToAdd = new Pricelist();
		plToAdd.setStart(formatter.parse(NEW_PRICELIST_STARTDATE));
		plToAdd.setEnd(formatter.parse(NEW_PRICELIST_ENDDATE));
		
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("USER", "password").
	            postForEntity(URL_PREFIX, plToAdd, Pricelist.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		 responseEntity =
	            restTemplate.withBasicAuth("CONDUCTER", "password").
	            postForEntity(URL_PREFIX, plToAdd, Pricelist.class);
			assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		
		 responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            postForEntity(URL_PREFIX, plToAdd, Pricelist.class);
		Pricelist addedPl = responseEntity.getBody();
		
		assertEquals(plToAdd.getStart(), addedPl.getStart());
		assertEquals(plToAdd.getEnd(), addedPl.getEnd());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	@Test
	public void testAddNewPricelistWhenStartDateIsAfterEndDate_RequiredRoleAdmin() throws ParseException{
		Pricelist plToAdd = new Pricelist();
		plToAdd.setStart(formatter.parse(NEW_PRICELIST_INVALID_STARTDATE));
		plToAdd.setEnd(formatter.parse(NEW_PRICELIST_INVALID_ENDDATE));
		
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            postForEntity(URL_PREFIX, plToAdd, Pricelist.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
	}
	@Test
	@Rollback(true)
	public void testAddNewPricelistWhenNewDatesMatchWithOtherPricelistsDates_RequiredRoleAdmin() throws ParseException{
		Pricelist plToAdd = new Pricelist();
		plToAdd.setStart(formatter.parse(NEW_PRICELIST_MATCH_STARTDATE));
		plToAdd.setEnd(formatter.parse(NEW_PRICELIST_MATCH_ENDDATE));
		
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").
	            postForEntity(URL_PREFIX, plToAdd, Pricelist.class);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}


	@Test
	@Rollback(true)
	public void testModifyPricelistSuccess_RequiredRoleAdmin() throws ParseException{
		Pricelist plToModify = new Pricelist();
		plToModify.setId(EDIT_PRICELIST_ID);
		plToModify.setStart(formatter.parse(EDIT_PRICELIST_STARTDATE));
		plToModify.setEnd(formatter.parse(EDIT_PRICELIST_ENDDATE));
		
		HttpEntity<Pricelist> entity = new HttpEntity<Pricelist>(plToModify);
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		
		 responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		
		 responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		
		Pricelist modifiedPricelist = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(plToModify.getId().longValue(), modifiedPricelist.getId().longValue());
		assertEquals(plToModify.getStart(), modifiedPricelist.getStart());
		assertEquals(plToModify.getEnd(), modifiedPricelist.getEnd());
	}
		
	@Test
	public void testModifyPricelistWhenStartDateIsAfterEndDate_RequiredRoleAdmin() throws ParseException{
		Pricelist plToModify = new Pricelist();
		plToModify.setId(DB_PRICELIST1_ID);
		plToModify.setStart(formatter.parse(NEW_PRICELIST_INVALID_STARTDATE));
		plToModify.setEnd(formatter.parse(NEW_PRICELIST_INVALID_ENDDATE));

		
		HttpEntity<Pricelist> entity = new HttpEntity<Pricelist>(plToModify);
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
	}
	
	@Test
	public void testModifyPricelistWhenDatesMatchWithOtherPricelistsDates_RequiredRoleAdmin() throws ParseException{
		Pricelist plToModify = new Pricelist();
		plToModify.setId(EDIT_PRICELIST_ID);
		plToModify.setStart(formatter.parse(NEW_PRICELIST_MATCH_STARTDATE));
		plToModify.setEnd(formatter.parse(NEW_PRICELIST_MATCH_ENDDATE));

		
		HttpEntity<Pricelist> entity = new HttpEntity<Pricelist>(plToModify);
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testModifyPricelistWhenPricelistNotFound_RequiredRoleAdmin() throws ParseException{
		Pricelist plToModify = new Pricelist();
		plToModify.setId(DB_INVALID_PRICELIST_ID);
		plToModify.setStart(formatter.parse(EDIT_PRICELIST_STARTDATE));
		plToModify.setEnd(formatter.parse(EDIT_PRICELIST_ENDDATE));
		
		HttpEntity<Pricelist> entity = new HttpEntity<Pricelist>(plToModify);
		ResponseEntity<Pricelist> responseEntity =
	            restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX, HttpMethod.PUT, entity, Pricelist.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	@Test
	public void testDeletePricelistSuccess_RequiredRoleAdmin() throws ParseException{
	    //restTemplate.withBasicAuth("ADMIN", "password").delete(URL_PREFIX + "/" + DELETE_PRICELIST_ID_INVALID);
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/delete/"+ DELETE_PRICELIST_ID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

	}
	@Test
	public void testDeleteWhenPricelistNotFound_RequiredRoleAdmin() throws ParseException{
	    //restTemplate.withBasicAuth("ADMIN", "password").delete(URL_PREFIX + "/" + DELETE_PRICELIST_ID_INVALID);
        ResponseEntity<Object> result = restTemplate.withBasicAuth("ADMIN", "password").exchange(URL_PREFIX + "/delete/"+ DELETE_PRICELIST_ID_INVALID,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Object.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
		
	
	

	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
