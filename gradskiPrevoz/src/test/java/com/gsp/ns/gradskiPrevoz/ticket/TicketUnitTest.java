package com.gsp.ns.gradskiPrevoz.ticket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.gsp.ns.gradskiPrevoz.zone.Zone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketUnitTest {
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	private TicketService ticketService;
	@MockBean
	private PricelistService pricelistService;
	@MockBean
	private TicketTypeService ticketTypeService;
	
	private Ticket ticketTest;
	private TicketType ticketTypeTest;
	private Pricelist pricelistTest;
	private PricelistItem itemTest;
	private User userTest;
	private User userTest2;
	private Line lineTest;
	private Zone zoneTest;
	private Zone zoneTest2;
	public static final long HOUR = 3600*1000;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	@Before()
	public void init() throws ParseException{
		ticketTypeTest = new TicketType("Mesecna", null, 720, new ArrayList<Zone>(), UserStatus.STANDARD);
		ticketTypeTest.setId(1L);
		pricelistTest = new Pricelist(1L, new ArrayList<PricelistItem>(), formatter.parse("21-12-2018"), formatter.parse("21-20-2018"));	
		itemTest = new PricelistItem(pricelistTest,ticketTypeTest, 700);
		userTest = new User(1L, "Ime", "Prezime", "user@gmail.com", "pass", UserStatus.STANDARD, 2000, null, null);
		userTest2 = new User(2L, "Ime2", "Prezime2", "user@gmail.com2", "pass2", UserStatus.STANDARD, 2000, null, null);
		ticketTest = new Ticket(1L, userTest, null, new Date(), ticketTypeTest);
		zoneTest = new Zone(1L, "gradska", null);
		zoneTest2 = new Zone(2L, "prigradska", null);
		lineTest = new Line(1L, null, zoneTest, "4");
		
	}
	@Test(expected = PricelistNotFoundException.class)
	public void testBuyTicketWhenCurrentPricelistDontExists(){
		when(pricelistService.getActivePricelist()).thenThrow(PricelistNotFoundException.class);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test(expected = ResourceNotFoundException.class)
	public void testBuyTicketWhenTicketTypeDontExists(){
		when(pricelistService.getActivePricelist()).thenReturn(pricelistTest);
		when(ticketTypeService.findById(1L)).thenThrow(ResourceNotFoundException.class);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test(expected = ResourceNotFoundException.class)
	public void testBuyTicketWhenPricelistItemDontExists(){
		when(pricelistService.getActivePricelist()).thenReturn(pricelistTest);
		when(ticketTypeService.findById(1L)).thenReturn(ticketTypeTest);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test(expected = RequiredStatusException.class)
	public void testBuyTicketWhenUserDontHaveRequiredStatus(){
		when(pricelistService.getActivePricelist()).thenReturn(pricelistTest);
		when(ticketTypeService.findById(1L)).thenReturn(ticketTypeTest);
		pricelistTest.getPricelistItems().add(itemTest);
		ticketTypeTest.setRequiredStatus(UserStatus.ELDERLY);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test(expected = NoFundsException.class)
	public void testBuyTicketWhenUserDontHaveEnoughtFunds(){
		when(pricelistService.getActivePricelist()).thenReturn(pricelistTest);
		when(ticketTypeService.findById(1L)).thenReturn(ticketTypeTest);
		pricelistTest.getPricelistItems().add(itemTest);
		userTest.setBalance(100);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test
	public void testBuyTicketSuccess(){
		when(pricelistService.getActivePricelist()).thenReturn(pricelistTest);
		when(ticketTypeService.findById(1L)).thenReturn(ticketTypeTest);
		pricelistTest.getPricelistItems().add(itemTest);
		ticketService.buyTicket(ticketTypeTest, userTest);
	}
	@Test(expected = ForbiddenException.class)
	public void testActivateTicketWhenUserWantToActivateSomeoneElsesTicket(){
		//ticketTest pripada userTest a ne userTest2
		ticketTest.setUser(userTest);
		ticketService.activate(ticketTest, userTest2);
	}
	@Test(expected = ConflictException.class)
	public void testActivateTicketWhenTicketIsAlreadyActivated(){
		ticketTest.setUser(userTest);
		ticketTest.setTimeOfActivation(new Date());
		ticketService.activate(ticketTest, userTest);
	}
	@Test
	public void testActivateTicketSuccess(){
		ticketTest.setUser(userTest);
		Ticket activatedTicket = ticketService.activate(ticketTest, userTest);
		assertEquals(true, activatedTicket.getTimeOfActivation() != null);
	}
	@Test(expected = ForbiddenException.class)
	public void testTransferTicketWhenUserTryToTransferSomeoneElsesTicket(){
		//ticketTest pripada userTest a ne userTest2
		ticketTest.setUser(userTest);
		ticketService.transfer(ticketTest, userTest2, userTest);
	}
	@Test(expected = ConflictException.class)
	public void testTransferTickettWhenTicketIsActivatedShouldFail(){
		//ticketTest pripada userTest a ne userTest2
		ticketTest.setUser(userTest);
		ticketTest.setTimeOfActivation(new Date());
		ticketService.transfer(ticketTest, userTest, userTest2);
	}
	@Test
	public void testTransferTicketSuccess(){
		ticketTest.setUser(userTest);
		Ticket transferedTicket = new Ticket(1L, userTest2, null, new Date(), ticketTypeTest);
		TicketRepository repoMock = Mockito.mock(TicketRepository.class);
		when(repoMock.save(ticketTest)).thenReturn(transferedTicket);
		Ticket ticket = ticketService.transfer(ticketTest, userTest, userTest2);
		assertEquals(userTest2.getIdUser(), ticket.getUser().getIdUser());	
	}
	@Test
	public void testVerifyTicketWhenTicketIsNotActivated(){
		ticketTest.setTimeOfActivation(null);
		Boolean isVerified = ticketService.verifyTicket(ticketTest, lineTest);
		assertEquals(false, isVerified);
	}
	@Test
	public void testVerifyTicketTicketCantBeUsedInBusLine(){
		ticketTest.setTimeOfActivation(null);
		ticketTest.getTicketType().getZones().add(zoneTest2);
		lineTest.setZone(zoneTest);
		Boolean isVerified = ticketService.verifyTicket(ticketTest, lineTest);
		assertEquals(false, isVerified);
	}
	@Test
	public void testVerifyTicketTicketIsExpired(){
		Date date = new Date(); //now
		//karta je aktivirana pre 2 sata a vreme vazenja karte je 1 sat
		Date activationDate = new Date(date.getTime() - 2 * HOUR);
		ticketTypeTest.setDurationInHours(1);
		ticketTest.setTimeOfActivation(activationDate);
		ticketTest.getTicketType().getZones().add(zoneTest);
		lineTest.setZone(zoneTest);
		Boolean isVerified = ticketService.verifyTicket(ticketTest, lineTest);
		assertEquals(false, isVerified);
	}
	@Test
	public void testVerifyTicketSuccess(){
		Date date = new Date(); //now
		//karta je aktivirana pre pola sata a vreme vazenja karte je 1 sat
		Date activationDate = new Date(date.getTime() - (int)(0.5 * HOUR));
		ticketTypeTest.setDurationInHours(1);
		ticketTest.setTimeOfActivation(activationDate);
		ticketTest.getTicketType().getZones().add(zoneTest);
		lineTest.setZone(zoneTest);
		Boolean isVerified = ticketService.verifyTicket(ticketTest, lineTest);
		assertEquals(true, isVerified);
	}
	
}
