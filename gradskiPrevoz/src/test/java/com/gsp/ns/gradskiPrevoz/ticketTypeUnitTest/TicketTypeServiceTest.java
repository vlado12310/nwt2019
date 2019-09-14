package com.gsp.ns.gradskiPrevoz.ticketTypeUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketTypeRepository;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketTypeService;
import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;
import com.gsp.ns.gradskiPrevoz.zone.Zone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketTypeServiceTest {
		@Autowired
		TicketTypeService service;
		ArrayList<Zone> testZones;

		@Before
		public void init(){
			testZones = new ArrayList<Zone>();
			testZones.add(new Zone());
		}

		@Test(expected = InvalidParametersException.class)
		public void addTicketTypeWhenDurationIsLessThan1(){
			//trajanje karte ne sme da bude 0

			TicketType newType = new TicketType("Mesecna", null, 0, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(newType)).thenReturn(newType);
			TicketTypeService service = new TicketTypeService(repository);
			service.addNew(newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void addNTicketTypeWhenNameIsEmptyString(){
			//ime tipa karte ne sme da bude prazan string
			TicketType newType = new TicketType("", null, 2, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(newType)).thenReturn(newType);
			TicketTypeService service = new TicketTypeService(repository);
			service.addNew(newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void addTicketTypeWhenSizeOfZonesEquals0(){
			//kad se dodaje novi tip karte mora da se unese barem jedna zona u kojoj vazi karta
			ArrayList<Zone> emptyZones = new ArrayList<Zone>();
			TicketType newType = new TicketType("Mesecna", null, 2, emptyZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(newType)).thenReturn(newType);
			TicketTypeService service = new TicketTypeService(repository);
			service.addNew(newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void addTicketTypeWhenRequiredStatusIsNull(){


			TicketType newType = new TicketType("Mesecna", null, 2, testZones, null);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(newType)).thenReturn(newType);
			TicketTypeService service = new TicketTypeService(repository);
			service.addNew(newType);
		}

		@Test
		public void addNewTestSuccess(){
			//sve ok
			TicketType newType = new TicketType("Mesecna", null, 2, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(newType)).thenReturn(newType);
			TicketTypeService service = new TicketTypeService(repository);
			TicketType addedType = service.addNew(newType);
			assertNotNull(addedType);
		}
		@Test(expected = InvalidParametersException.class)
		public void modifyTicketTypeWhenNameIsEmptyString(){
			//ime tipa karte ne sme da bude prazan string, trajanje karte ne sme da bude negativno
			TicketType typeFromDb = new TicketType("Mesacna", null, 720, testZones, UserStatus.STANDARD);

			TicketType newType = new TicketType("", null, 720, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(typeFromDb)).thenReturn(typeFromDb);
			TicketTypeService service = new TicketTypeService(repository);
			service.modify(typeFromDb, newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void modifyTicketTypeWhenDurationIsLesThan1(){
			//trajanje ne sme biti manje ili jednako nuli
			TicketType typeFromDb = new TicketType("Mesacna", null, 720, testZones, UserStatus.STANDARD);

			TicketType newType = new TicketType("Mesecna", null, 0, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(typeFromDb)).thenReturn(typeFromDb);
			TicketTypeService service = new TicketTypeService(repository);
			service.modify(typeFromDb, newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void modifyTicketTypeWhenZonesAreEmpty(){
			//U tipu karte mora se navesti u kojim zonama vazi
			TicketType typeFromDb = new TicketType("Mesacna", null, 720, testZones, UserStatus.STANDARD);
			ArrayList<Zone> emptyZones = new ArrayList<Zone>();
			TicketType newType = new TicketType("Mesecna", null, 0, emptyZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(typeFromDb)).thenReturn(typeFromDb);
			TicketTypeService service = new TicketTypeService(repository);
			service.modify(typeFromDb, newType);
		}
		@Test(expected = InvalidParametersException.class)
		public void modifyTicketTypeWhenRequiredStatusIsNull(){
			//Potreban status za kupovinu karte ne sme biti null
			TicketType typeFromDb = new TicketType("Mesacna", null, 720, testZones, UserStatus.STANDARD);

			TicketType newType = new TicketType("Mesecna", null, 0, testZones, null);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(typeFromDb)).thenReturn(typeFromDb);
			TicketTypeService service = new TicketTypeService(repository);
			service.modify(typeFromDb, newType);
		}
		@Test()
		public void modifyTestSuccess(){
			TicketType typeFromDb = new TicketType("Mesacna", null, 720, testZones, UserStatus.STANDARD);

			TicketType newType = new TicketType("Novo ime", null, 800, testZones, UserStatus.STANDARD);
			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			when(repository.save(typeFromDb)).thenReturn(typeFromDb);
			TicketTypeService service = new TicketTypeService(repository);
			TicketType afterModifyType = service.modify(typeFromDb, newType);

			assertEquals(afterModifyType.getDurationInHours(), newType.getDurationInHours() );
			assertEquals(afterModifyType.getName(), newType.getName());
		}
		@Test(expected = ForbiddenException.class)
		public void deletTicketTypePricelistItemsSizeNot0(){
			//ne sme se izbrisati tip karte ako postoji kao stavka u cenovniku
			ArrayList<PricelistItem> items = new ArrayList<PricelistItem>();
			items.add(new PricelistItem());
			TicketType type = new TicketType("Mesacna", items, 720, testZones, UserStatus.STANDARD);


			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			TicketTypeService service = new TicketTypeService(repository);

			service.delete(type);

		}
		@Test
		public void deleteSuccess(){
			ArrayList<PricelistItem> items = new ArrayList<PricelistItem>();
			TicketType type = new TicketType("Mesacna", items, 720, testZones, UserStatus.STANDARD);


			TicketTypeRepository repository = mock(TicketTypeRepository.class);
			TicketTypeService service = new TicketTypeService(repository);

			service.delete(type);
		}

}
