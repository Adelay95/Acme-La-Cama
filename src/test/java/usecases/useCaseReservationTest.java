
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
import services.FolderService;
import services.ReservationService;
import services.RoomService;
import services.WorkerService;
import utilities.AbstractTest;
import domain.Client;
import domain.KindOfOffert;
import domain.Reservation;
import domain.Room;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class useCaseReservationTest extends AbstractTest {

	@Autowired
	private FolderService		folderService;
	@Autowired
	private ClientService		clientService;
	@Autowired
	private RoomService			roomService;
	@Autowired
	private ReservationService	reservationService;
	@Autowired
	private WorkerService		workerService;


	//Servicios

	//RESERVAR HABITACION

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				//Un actor reserva una habitacion, seria correcto
				"client1", 296, null
			}, {
				//un actor no autenticado reserva una habitacion , por tanto daria error
				null, 296, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String username, final int roomId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Client me = this.clientService.findByPrincipal();
			final Room room = this.roomService.findOne(roomId);
			final Reservation reservation = this.reservationService.create(me, room);
			if (reservation.getId() != 0)
				Assert.isTrue(this.clientService.findByPrincipal().getId() == reservation.getClient().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(reservation.getRooms().getHotel()));
			final Double offertPrice = this.reservationService.calculateAuthomaticPrice(reservation);
			reservation.setPriceDay(offertPrice);
			final Date hoy = new Date("10/10/2018");
			final Date mañana = new Date("20/10/2018");
			reservation.setCheckIn(hoy);
			reservation.setCheckOut(mañana);
			final Room rooms = this.roomService.findOne(roomId);
			reservation.setRooms(rooms);
			reservation.setKindOfOffert(KindOfOffert.ALL_INCLUDED);
			Collection<Date> reservoirDates = new HashSet<Date>();
			final Date now = new Date();
			Assert.isTrue(now.before(reservation.getCheckIn()));
			if (!reservation.getCheckIn().equals(reservation.getCheckOut()))
				Assert.isTrue(reservation.getCheckIn().before(reservation.getCheckOut()));
			Assert.isTrue(this.reservationService.checkGoodHotel(reservation.getRooms().getHotel(), reservation.getCheckIn(), reservation.getCheckOut()));
			reservoirDates = this.reservationService.calcularDates(reservation);
			Assert.isTrue(this.reservationService.checkDispnibilidadHabitación(reservation, reservoirDates));
			reservoirDates = this.reservationService.calcularDates(reservation);
			reservation.setNumDays(reservoirDates.size());
			final Reservation res2 = this.reservationService.saveAgore(reservation, reservoirDates);
			final Reservation res3 = this.reservationService.makeTheBill(res2);
			this.reservationService.save(res3);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//EDITAR RESERVA HABITACION

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				//Un actor reserva una habitacion, seria correcto
				"client1", 657, null
			}, {
				//un actor no autenticado reserva una habitacion , por tanto daria error
				null, 657, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final int reservationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Client me = this.clientService.findByPrincipal();
			final Integer numero = me.getReservations().size();
			final Reservation reservation = this.reservationService.findOne(reservationId);
			reservation.setKindOfOffert(KindOfOffert.FULL_PENSION);
			this.reservationService.save(reservation);
			Assert.isTrue(numero == me.getReservations().size());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
