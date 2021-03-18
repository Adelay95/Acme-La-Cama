
package usecases;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ClientService;
import services.ManagerService;
import services.OffertService;
import services.RoomService;
import utilities.AbstractTest;
import domain.KindOfOffert;
import domain.Manager;
import domain.Offert;
import domain.Room;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseOfferts extends AbstractTest {

	@Autowired
	private OffertService offertService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private ClientService clientService;

    //Create an offert
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {
			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"manager2", KindOfOffert.ALL_INCLUDED,216.23,278, null
			},{
				//En este caso intentamos crear una oferta de una habitación de un hotel que no nos pertenece.
				"manager1", KindOfOffert.ALL_INCLUDED,216.23,279, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (KindOfOffert) testingData[i][1], (Double) testingData[i][2], (Integer) testingData[i][3],(Class<?>) testingData[i][4]);
	}
	
	@SuppressWarnings("deprecation")
	protected void template(final String username, final KindOfOffert kindOfOffert,final Double precio, final Integer roomId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			Collection<Date> reservoirDates = new HashSet<Date>();
			this.authenticate(username);
			Room room= roomService.findOne(roomId);
			Offert offert=offertService.create(room);
			Date date=new Date("2018/01/01");
			Date date2=new Date("2018/02/01");
			offert.setBill(null);
			offert.setCheckIn(date);
			offert.setCheckOut(date2);
			offert.setKindOfOffert(kindOfOffert);
			offert.setTotalPrice(precio);
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			final Date now = new Date();
			Assert.isTrue(now.before(offert.getCheckIn()));
			if (!offert.getCheckIn().equals(offert.getCheckOut()))
				Assert.isTrue(offert.getCheckIn().before(offert.getCheckOut()));
			Assert.isTrue(this.offertService.checkGoodHotel(offert.getRooms().getHotel(), offert.getCheckIn(), offert.getCheckOut()));
			Assert.isTrue(this.offertService.checkOffertFreeRoom(offert.getRooms()));
			reservoirDates = this.offertService.calcularDates(offert);
			Assert.isTrue(this.offertService.checkDispnibilidadHabitación(offert, reservoirDates));
			reservoirDates = this.offertService.calcularDates(offert);
			final Offert res = this.offertService.saveAgore(offert, reservoirDates);
			Assert.isTrue(offertService.allOfferts().contains(res));
			Assert.isTrue(!roomService.findOne(roomId).getOccupiedDays().isEmpty());
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Edit an offert
	@Test
	public void driver2() {
		final Object testingData[][] =  {
			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general.
				"manager2", KindOfOffert.ALL_INCLUDED,216.23,658, null
			},{
				//En este caso intentamos editar una oferta que ya esté adjudicada.
				"manager2", KindOfOffert.ALL_INCLUDED,216.23,659, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (KindOfOffert) testingData[i][1], (Double) testingData[i][2], (Integer) testingData[i][3],(Class<?>) testingData[i][4]);
	}

	@SuppressWarnings("deprecation")
	protected void template2(final String username, final KindOfOffert kindOfOffert,final Double precio, final Integer offertId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try{
			Collection<Date> reservoirDates = new HashSet<Date>();
			this.authenticate(username);
			Offert offert= offertService.findOne(offertId);
			Date date=new Date("2018/01/01");
			Date date2=new Date("2018/02/01");
			offert.setBill(null);
			offert.setCheckIn(date);
			offert.setCheckOut(date2);
			offert.setKindOfOffert(kindOfOffert);
			offert.setTotalPrice(precio);
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			final Date now = new Date();
			Assert.isTrue(now.before(offert.getCheckIn()));
			if (!offert.getCheckIn().equals(offert.getCheckOut()))
				Assert.isTrue(offert.getCheckIn().before(offert.getCheckOut()));
			Assert.isTrue(this.offertService.checkGoodHotel(offert.getRooms().getHotel(), offert.getCheckIn(), offert.getCheckOut()));
			Assert.isTrue(this.offertService.checkOffertFreeRoom(offert.getRooms()));
			reservoirDates = this.offertService.calcularDates(offert);
			Assert.isTrue(this.offertService.checkDispnibilidadHabitación(offert, reservoirDates));
			reservoirDates = this.offertService.calcularDates(offert);
			final Offert res = this.offertService.saveAgore(offert, reservoirDates);
			Assert.isTrue(offertService.allOfferts().contains(res));
			Assert.isTrue(!roomService.findOne(offert.getRooms().getId()).getOccupiedDays().isEmpty());
			this.unauthenticate();
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	//Adjudicar una oferta
		@Test
		public void driver3() {
			final Object testingData[][] =  {
				{
					//Este es un caso de prueba normal, para comprobar la funcionalidad general.
					"client1",658, null
				},{
					//En este caso la oferta ya está adjudicada.
					"client2", 659 , IllegalArgumentException.class
				}
			};
			for (int i = 0; i < testingData.length; i++)
				this.template3((String) testingData[i][0], (Integer) testingData[i][1],(Class<?>) testingData[i][2]);
		}

		protected void template3(final String username, final Integer offertId, final Class<?> expected) {
			Class<?> caught;
			caught = null;

			try{

				this.authenticate(username);
				final Offert offert = this.offertService.findOne(offertId);
					final Date now = new Date();
					Assert.isTrue(now.before(offert.getCheckIn()) || now.equals(offert.getCheckIn()));
					Assert.isTrue(offert.getClient() == null);
					offert.setClient(this.clientService.findByPrincipal());
					final Offert res2 = this.offertService.makeTheBill(offert);
					final Offert res3=this.offertService.save(res2);
					Assert.isTrue(res3.getClient()==clientService.findByPrincipal() && res3.getBill()!=null);
				this.unauthenticate();

			}catch(Exception oops){
				caught=oops.getClass();
			}
			this.checkExceptions(expected, caught);
		}
	
	
}
