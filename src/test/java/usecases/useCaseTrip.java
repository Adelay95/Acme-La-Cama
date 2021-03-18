
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
import services.HotelService;
import services.ManagerService;
import services.OptionalTripService;
import utilities.AbstractTest;
import domain.Client;
import domain.Hotel;
import domain.Location;
import domain.Manager;
import domain.OptionalTrip;
import domain.Reservation;
import domain.Room;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseTrip extends AbstractTest {

	//Servicios
	@Autowired
	private OptionalTripService	optionalTripService;
	@Autowired
	private HotelService		hotelService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private ClientService		clientService;


	//Crear excursion como manager
	//Asistir a excursion como cliente

	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				//Crear trip correctamente
				"manager2", 216, null
			}, {
				//Crear trip no correctamente ya que esta intentando crear la trip en otro hotel
				"manager1", 216, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int hotel, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			final Date hoy = new Date();
			final Date despues = new Date("2018/01/01 00:00");
			this.authenticate(username);

			final Hotel rip = this.hotelService.findOne(hotel);

			final Manager actor = this.managerService.findByPrincipal();
			Assert.isTrue(rip.getRequest().getManager().getId() == actor.getId());
			final OptionalTrip ot = this.optionalTripService.create(this.hotelService.findOne(hotel));
			ot.setClosingTime(despues);
			ot.setOpeningTime(hoy);
			ot.setPrice(32.2);
			ot.setPicture("http://www.twitter.com");
			ot.setTitle("Title");
			final Location lo = new Location();
			lo.setGpsCoords("+32.22,-52.22");
			lo.setPopulation("Seville");
			lo.setProvince("Anda");
			ot.setLocation(lo);

			final OptionalTrip save = this.optionalTripService.save(ot);

			rip.getOptionalTrips().add(save);

			final Hotel rip2 = this.hotelService.save(rip);

			Assert.isTrue(rip2.getOptionalTrips().contains(save));

			this.unauthenticate();
		} catch (final Exception oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver2() {
		final Object testingData[][] = {

			{
				//Asisto a una excursion correctamente
				"client7", 615, null
			}, {
				//Como admin no puedo asistir a una excursion
				"admin", 615, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final int trip, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			final Client actor = this.clientService.findByPrincipal();
			Assert.isTrue(actor instanceof Client);
			this.optionalTripService.asistir(trip, actor.getId());

			this.unauthenticate();
		} catch (final Exception oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}














