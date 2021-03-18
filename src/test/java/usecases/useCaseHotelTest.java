
package usecases;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.HotelService;
import services.ManagerService;
import services.RequestService;
import utilities.AbstractTest;
import domain.Hotel;
import domain.Location;
import domain.Manager;
import domain.Request;
import domain.Terrain;
import forms.FormHotel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseHotelTest extends AbstractTest {

	@Autowired
	private ManagerService	managerService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private RequestService	requestService;


	//Servicios

	//Crear un hotel
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un manager crea un hotel
				"manager1", null
			}, {
				//Un cliente intenta crear un hotel, por tanto daría error
				"client1", IllegalArgumentException.class
			}, {
				//Un administrador intenta crear un hotel, por tanto daría error
				"admin", IllegalArgumentException.class
			}, {
				//Un usuario no logueado intenta crear un hotel.
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	@SuppressWarnings("deprecation")
	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Integer antes = this.hotelService.allHotel().size();
			final Manager manager = this.managerService.findByPrincipal();
			final Request request = this.requestService.create(manager);
			final Hotel hotel = this.hotelService.create(request);
			final FormHotel formHotel = this.hotelService.createMyForm(hotel, request);
			formHotel.setDescription("Descripcion");
			final Location location = new Location();
			location.setProvince("Sevilla");
			location.setPopulation("Sevilla");
			location.setGpsCoords("+32.2,-5.2");
			formHotel.setLocation(location);
			formHotel.setHotelChain("Renfe");
			formHotel.setName("Hotel prueba");
			formHotel.setPicture("http://www.google.es");
			formHotel.setRoomPrice(50.0);
			formHotel.setStars(4);
			formHotel.setTerrain(Terrain.CULTURAL);
			final Date date = new Date("2018/10/05 00:00");
			final Date date2 = new Date("2019/10/05 00:00");
			formHotel.setTimeIn(date);
			formHotel.setTimeOut(date2);
			formHotel.setTotalRooms(200);

			final Hotel hotel2 = this.hotelService.reconstruct(formHotel, null);
			this.managerService.saveFormHotel(formHotel, hotel2);

			Assert.isTrue(this.hotelService.allHotel().size() == antes + 1);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
