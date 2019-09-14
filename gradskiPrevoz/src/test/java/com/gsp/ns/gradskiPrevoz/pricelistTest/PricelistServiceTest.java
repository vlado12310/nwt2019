package com.gsp.ns.gradskiPrevoz.pricelistTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistRepository;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistService;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
/**
 * @author Fujitsu E752
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PricelistServiceTest {
	List<Pricelist> testPricelists;
	Pricelist pricelistWithItems;
	Pricelist pricelistWithFutureDate;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	@Autowired
	PricelistService service;
	@Before
	public void init() throws ParseException{
		testPricelists = new ArrayList<Pricelist>();
		testPricelists.add(new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("10-05-2018 20:20"), formatter.parse("20-05-2018 20:20")));
		testPricelists.add(new Pricelist(2L, null, formatter.parse("10-06-2018 20:20"), formatter.parse("20-06-2018 20:20")));
		pricelistWithItems = new Pricelist(2L, null, formatter.parse("10-06-2018 20:20"), formatter.parse("20-06-2018 20:20"));
		pricelistWithItems.setPricelistItems(new ArrayList<PricelistItem>());
		pricelistWithItems.getPricelistItems().add(new PricelistItem());
		pricelistWithFutureDate = new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("10-05-2025 20:20"), formatter.parse("20-05-2026 20:20"));
	}
	@Test(expected = ConflictException.class)
	public void saveTestDatesMatch() throws ParseException{
		//pocetni i krajnji datum cenovnika ne sme da se poklapa sa ostalim cenovnicima
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		PricelistService service = new PricelistService(repository);
		
		Pricelist plToAdd = new Pricelist(null, null, formatter.parse("10-05-2018 20:20"), formatter.parse("21-05-2018 20:20"));
		service.save(plToAdd);
		
	}
	
	@Test(expected = InvalidParametersException.class)
	public void saveTestDatesInvalidDates() throws ParseException{
		//pocetni datum ne sme biti veci od krajnjeg datuma
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		PricelistService service = new PricelistService(repository);
		
		Pricelist plToAdd = new Pricelist(3L, null, formatter.parse("11-05-2018 20:20"), formatter.parse("05-05-2018 20:20"));
		service.save(plToAdd);
		
	}
	@Test
	public void saveTestSuccess() throws ParseException{
		Pricelist plToAdd = new Pricelist(3L, null, formatter.parse("20-07-2018 20:20"), formatter.parse("20-08-2018 20:20"));	
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		when(repository.save(plToAdd)).thenReturn(plToAdd);
		PricelistService service = new PricelistService(repository);	
		Pricelist addedPl = service.save(plToAdd);
		assertEquals(plToAdd.getId(), addedPl.getId());
	}
	@Test(expected = ConflictException.class)
	public void modifyPricelistWhenDatesMatchWithOtherPricelists() throws ParseException{
		Pricelist pricelistToModify = testPricelists.get(0);
		Pricelist newPricelist = new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("11-06-2018 20:20"), formatter.parse("22-06-2018 20:20"));
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		PricelistService service = new PricelistService(repository);
		service.modify(pricelistToModify, newPricelist);
		
	}
	@Test(expected = InvalidParametersException.class)
	public void modifyPricelistWhenDatesAreInvalid() throws ParseException{
		//pocetni datum ne sme biti veci od krajnjeg datuma
		Pricelist pricelistToModify = testPricelists.get(0);
		Pricelist newPricelist = new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("11-06-2018 20:20"), formatter.parse("05-06-2018 20:20"));
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		PricelistService service = new PricelistService(repository);
		service.modify(pricelistToModify, newPricelist);
		
	}
	
	@Test
	public void modifyPricelisSuccess() throws ParseException{
		
		Pricelist pricelistToModify = testPricelists.get(0);
		Date newStartDate = formatter.parse("11-06-2020 20:20");
		Date newEndDate = formatter.parse("21-06-2020 20:20");
		Pricelist newPricelist = new Pricelist(1L, new ArrayList<PricelistItem>(), newStartDate, newEndDate);
		PricelistRepository repository = mock(PricelistRepository.class);
		when(repository.findAll()).thenReturn(testPricelists);
		when(repository.save(pricelistToModify)).thenReturn(pricelistToModify);
		PricelistService service = new PricelistService(repository);
		Pricelist modifiedPricelist = service.modify(pricelistToModify, newPricelist);
		assertEquals(newStartDate, modifiedPricelist.getStart());
		assertEquals(newEndDate, modifiedPricelist.getEnd());
		
	}
	/*
	 * Cenovnik ne sme da se obrise ukoliko sadrzi definisane stavke cenovnika i datum pocetka vazenja je iz proslosti
	*/
	@Test(expected = ConflictException.class)
	public void deleteWhenPricelistContainsItems(){
		service.delete(pricelistWithItems);
	}
	/*
	 * Cenovnik se moze izbrisati ukoliko je datum vazenja cenovnika veci od trenutnog datuma
	*/
	@Test
	public void deleteWhenPricelistStartDateIsDateFromFuture(){
		
		service.delete(pricelistWithFutureDate);
	}
	/*
	 * Cenovnik se moze izbrisati ukoliko ne postoji definisana stavka u cenovniku
	*/
	@Test
	public void deleteWhenPricelistDontContainsDefinedItems(){
		Pricelist pricelistWithNoDefinedItems = testPricelists.get(0);
		service.delete(pricelistWithNoDefinedItems);
	}
}
