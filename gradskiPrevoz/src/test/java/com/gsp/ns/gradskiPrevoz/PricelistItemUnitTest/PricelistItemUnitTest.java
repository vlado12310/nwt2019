package com.gsp.ns.gradskiPrevoz.PricelistItemUnitTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.pricelist.PricelistRepository;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItemRepository;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItemService;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.ticket.TicketRepository;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PricelistItemUnitTest {
	private Pricelist pricelistMock;
	private TicketType ticketTypeMock;
	private PricelistItem itemInvalidPrice;
	private PricelistItem item;
	private Pricelist pricelistMock2;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	@MockBean
	private PricelistItemRepository repository;
	@Autowired
	private PricelistItemService service;
	@MockBean
	private TicketRepository ticketRepository;
	@MockBean
	private PricelistRepository pricelistRepository;
	@Before
	public void init() throws ParseException{
		ticketTypeMock = new TicketType("Mesecna", null, -1, null, UserStatus.STANDARD);
		pricelistMock2 = new Pricelist(2L, new ArrayList<PricelistItem>(), formatter.parse("10-05-2030 20:20"), formatter.parse("20-05-2030 20:20"));
		pricelistMock = new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("10-05-2018 20:20"), formatter.parse("20-05-2018 20:20"));
		itemInvalidPrice = new PricelistItem(pricelistMock , ticketTypeMock, -10);
		item = new PricelistItem(pricelistMock, ticketTypeMock, 1000);
	}
	@Test(expected = InvalidParametersException.class)
	public void savePricelistItemWhenPriceIsLowerThan1(){

		when(repository.save(itemInvalidPrice)).thenReturn(itemInvalidPrice);

		service.save(itemInvalidPrice, new Pricelist());
	}
	@Test(expected = ConflictException.class)
	public void saveItemWhenSameItemExistsInPricelist(){
		PricelistItem itemWithSameTicketType = new PricelistItem(pricelistMock, item.getTicketType(), 700);
		pricelistMock.getPricelistItems().add(itemWithSameTicketType);
		when(repository.save(itemInvalidPrice)).thenReturn(item);

		service.save(item, pricelistMock);
	}
	//pokusaj brisanja stavke cenovnika kada postoji kupljena karta
	@Test(expected = ConflictException.class)
	public void deleteItemFail() throws ParseException{
		PricelistItem itemToDelete = new PricelistItem(pricelistMock, ticketTypeMock, 500);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2018 20:20"), ticketTypeMock);
		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(pricelistMock);
		service.delete(itemToDelete);
	}
	//uspesno brisanje
	@Test
	public void deleteSuccess() throws ParseException{
		PricelistItem itemToDelete = new PricelistItem(pricelistMock, ticketTypeMock, 500);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2025 20:20"), ticketTypeMock);
		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(pricelistMock2);
		service.delete(itemToDelete);
	}
	//uspesno brisanje kada je datum pocetka vazenja cenovnika buduci datum
	@Test
	public void deleteSuccessWhenPricelistDateIsFutureDate() throws ParseException{
		PricelistItem itemToDelete = new PricelistItem(pricelistMock2, ticketTypeMock, 500);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2025 20:20"), ticketTypeMock);
		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(pricelistMock2);
		service.delete(itemToDelete);
	}
	//pokusaj promene stavke cenovnika kada postoji kupljena karta
	@Test(expected = ConflictException.class)
	public void modifyFailWhenTicketExists() throws ParseException{
		PricelistItem itemToModify = new PricelistItem(pricelistMock, ticketTypeMock, 500);
		PricelistItem newItem = new PricelistItem(pricelistMock, ticketTypeMock, 700);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2018 20:20"), ticketTypeMock);

		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(pricelistMock);
		service.modify(itemToModify, newItem, itemToModify.getPricelist());
	}
	//pokusaj promene stavke cenovnika kada je cena negativna
	@Test(expected = InvalidParametersException.class)
	public void modifyFailWhenPriceisLowerThan1() throws ParseException{
		PricelistItem itemToModify = new PricelistItem(pricelistMock, ticketTypeMock, 500);
		PricelistItem newItem = new PricelistItem(pricelistMock, ticketTypeMock, -3);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2018 20:20"), ticketTypeMock);

		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(pricelistMock2);
		service.modify(itemToModify, newItem, itemToModify.getPricelist());
	}
	//usespna promena
	@Test
	public void modifySuccess() throws ParseException{
		PricelistItem itemToModify = new PricelistItem(pricelistMock, ticketTypeMock, 500);
		PricelistItem newItem = new PricelistItem(pricelistMock, ticketTypeMock, 300);
		Ticket ticket = new Ticket(1L, null, null, formatter.parse("11-05-2018 20:20"), ticketTypeMock);

		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(ticket);
		when(ticketRepository.findAll()).thenReturn(allTickets);
		when(pricelistRepository.findByStartBeforeAndEndAfter(Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(pricelistMock2);
		service.modify(itemToModify, newItem, itemToModify.getPricelist());
	}
	@Test
	public void saveSuccess(){
		when(repository.save(item)).thenReturn(item);

		PricelistItem addedItem = service.save(item, pricelistMock);
		assertEquals(item.getId(), addedItem.getId());
	}
}
