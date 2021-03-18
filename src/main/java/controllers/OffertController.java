
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ClientService;
import services.HotelService;
import services.ManagerService;
import services.OffertService;
import services.ReservationService;
import services.RoomService;
import services.ServicesService;
import services.WorkerService;
import domain.Actor;
import domain.Client;
import domain.KindOfOffert;
import domain.Manager;
import domain.Offert;
import domain.Room;

@Controller
@RequestMapping("/offert")
public class OffertController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RoomService			roomService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ReservationService	reservationService;
	@Autowired
	private OffertService		offertService;
	@Autowired
	private ServicesService		servicesService;
	@Autowired
	private HotelService		hotelService;
	@Autowired
	private ClientService		clientService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private WorkerService		workerService;


	// Constructors ----------------------------------------------------------
	public OffertController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/hotel/list", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam final int hotelId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		Collection<Offert> offerts;
		if (actor instanceof Manager)
			offerts = this.hotelService.getAllOfferts(hotelId);
		else
			offerts = this.hotelService.getAllAvailableOfferts(hotelId);
		final Date now = new Date();
		result = new ModelAndView("offert/list");
		result.addObject("requestURI", "offert/hotel/list.do?hotelId=" + hotelId);
		result.addObject("offerts", offerts);
		result.addObject("now", now);
		return result;
	}

	@RequestMapping(value = "/manajer/list", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView result;
		final Date now = new Date();
		Collection<Offert> offerts;
		final Manager manager = this.managerService.findByPrincipal();
		offerts = this.managerService.getAllMyOfferts(manager.getId());
		result = new ModelAndView("offert/list");
		result.addObject("requestURI", "offert/manager/list.do");
		result.addObject("offerts", offerts);
		result.addObject("now", now);
		return result;
	}

	@RequestMapping(value = "/client/list", method = RequestMethod.GET)
	public ModelAndView list3() {
		ModelAndView result;
		Collection<Offert> offerts;
		final Client client = this.clientService.findByPrincipal();
		offerts = client.getOfferts();

		result = new ModelAndView("offert/list");
		result.addObject("requestURI", "offert/client/list.do");
		result.addObject("offerts", offerts);
		final Date now = new Date();
		result.addObject("now", now);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create2(@RequestParam final int offertId) {
		ModelAndView result;
		final Offert offert = this.offertService.findOne(offertId);
		result = this.createEditModelAndViewS(offert);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int roomId) {
		ModelAndView result;
		final Room room = this.roomService.findOne(roomId);
		final Offert offert = this.offertService.create(room);
		result = this.createEditModelAndViewS(offert);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Offert offert, final BindingResult binding) {
		ModelAndView result;
		Collection<Date> reservoirDates = new HashSet<Date>();
		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
            Assert.isTrue(offert.getClient()==null && offert.getBill()==null);
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewS(offert, "offert.commit.error");
			return result;
		}
		try {
			final Date now = new Date();
			Assert.isTrue(now.before(offert.getCheckIn()));
			if (!offert.getCheckIn().equals(offert.getCheckOut()))
				Assert.isTrue(offert.getCheckIn().before(offert.getCheckOut()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndViewS(offert, "offert.error.dates");
			return result;
		}
		try {
			Assert.isTrue(this.offertService.checkGoodHotel(offert.getRooms().getHotel(), offert.getCheckIn(), offert.getCheckOut()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndViewS(offert, "offert.error.hotel");
			return result;
		}
		try {
			Assert.isTrue(this.offertService.checkOffertFreeRoom(offert.getRooms()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndViewS(offert, "offert.error.alreadyOffert");
			return result;
		}
		try {
			reservoirDates = this.offertService.calcularDates(offert);
			Assert.isTrue(this.offertService.checkDispnibilidadHabitación(offert, reservoirDates));
		} catch (final Throwable oops) {

			result = this.createEditModelAndViewS(offert, "offert.error.roomOccuppied");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndViewS(offert);
		else
			try {
				reservoirDates = this.offertService.calcularDates(offert);
				final Offert res = this.offertService.saveAgore(offert, reservoirDates);
				result = new ModelAndView("redirect:manajer/list.do");
			} catch (final Exception oops) {
				result = this.createEditModelAndViewS(offert, "offert.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/adjudicate", method = RequestMethod.GET)
	public ModelAndView save2(@RequestParam final int offertId) {
		ModelAndView result;
		final Offert offert = this.offertService.findOne(offertId);
		Collection<Offert> offerts = this.hotelService.getAllAvailableOfferts(offert.getRooms().getHotel().getId());

		result = new ModelAndView("offert/list");
		final Date now2 = new Date();
		result.addObject("now", now2);
		result.addObject("requestURI", "offert/hotel/list.do?hotelId=" + offert.getRooms().getHotel().getId());
		result.addObject("offerts", offerts);

		try {
			final Date now = new Date();
			Assert.isTrue(now.before(offert.getCheckIn()) || now.equals(offert.getCheckIn()));
		} catch (final Throwable oops) {

			result.addObject("message", "offert.error.passedOffert");
			return result;
		}
		try {
			Assert.isTrue(offert.getClient() == null);
			offert.setClient(this.clientService.findByPrincipal());
			final Offert res2 = this.offertService.makeTheBill(offert);
			this.offertService.save(res2);

			offerts = this.hotelService.getAllAvailableOfferts(offert.getRooms().getHotel().getId());
			result.addObject("message", "offert.commit.ok");
			result.addObject("offerts", offerts);
		} catch (final Throwable oops) {

			result.addObject("message", "offert.commit.error");
		}

		return result;

	}

	//Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndViewS(final Offert offert) {
		ModelAndView result;

		result = this.createEditModelAndViewS(offert, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewS(final Offert offert, final String message) {
		ModelAndView result;

		result = new ModelAndView("offert/create");
		result.addObject("requestURI", "offert/create.do");

		final Collection<String> kindOfOffert = new HashSet<String>();
		for (final KindOfOffert p : KindOfOffert.values())
			kindOfOffert.add(p.toString());

		result.addObject("kindOfOffert", kindOfOffert);
		result.addObject("offert", offert);
		result.addObject("message", message);
		return result;

	}

}
