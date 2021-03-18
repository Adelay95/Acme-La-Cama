
package controllers;

import java.util.Collection;
import java.util.Date;

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
import services.OptionalTripService;
import services.WorkerService;
import domain.Actor;
import domain.Client;
import domain.Hotel;
import domain.Manager;
import domain.OptionalTrip;
import forms.FormTrip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private OptionalTripService	optionalTripService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ClientService		clientService;
	@Autowired
	private WorkerService		workerService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private HotelService		hotelService;


	// Constructors ----------------------------------------------------------
	public TripController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list1", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int ofertaId) {
		ModelAndView result;

		Collection<OptionalTrip> ot;
		ot = this.optionalTripService.excursionesDisponiblesOfertas(ofertaId);
		final Client yo = this.clientService.findByPrincipal();
		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list1.do");
		result.addObject("trips", ot);
		result.addObject("ofertaId", ofertaId);
		result.addObject("principal", yo);

		return result;
	}
	@RequestMapping(value = "/attend_by_offert", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam final int oT, @RequestParam final int ofertaId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final OptionalTrip jeje = this.optionalTripService.findOne(oT);
		final Collection<Client> aquiEstoy = this.optionalTripService.sixtoCarry(oT, jeje.getHotel().getId());

		try {
			Assert.isTrue(aquiEstoy.contains(actor));
			this.optionalTripService.asistir(oT, actor.getId());
			result = new ModelAndView("redirect:list1.do?ofertaId=" + ofertaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list1.do?ofertaId=" + ofertaId + "&message=actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public ModelAndView list3(@RequestParam final int reservaId) {
		ModelAndView result;

		Collection<OptionalTrip> ot;
		ot = this.optionalTripService.excursionesDisponiblesReserva(reservaId);
		final Client yo = this.clientService.findByPrincipal();

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list2.do");
		result.addObject("trips", ot);
		result.addObject("reservaId", reservaId);
		result.addObject("principal", yo);
		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list4() {
		ModelAndView result;

		Collection<OptionalTrip> ot;
		final Manager yo = this.managerService.findByPrincipal();
		ot = this.optionalTripService.excursionesMia(yo.getId());

		result = new ModelAndView("trip/list");
		result.addObject("requestURI", "trip/list.do");
		result.addObject("trips", ot);
		result.addObject("principal", yo);

		return result;
	}
	@RequestMapping(value = "/attend_by_reservation", method = RequestMethod.GET)
	public ModelAndView list4(@RequestParam final int oT, @RequestParam final int reservaId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();

		try {
			this.optionalTripService.asistir(oT, actor.getId());
			result = new ModelAndView("redirect:list2.do?reservaId=" + reservaId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list2.do?reservaId=" + reservaId + "&message=actor.commit.error");
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int hotelId) {
		ModelAndView result;
		Hotel hotel;
		hotel = this.hotelService.findOne(hotelId);
		final Manager manager = this.managerService.findByPrincipal();
		Assert.isTrue(hotel.getRequest().getManager().getId() == manager.getId());
		final OptionalTrip trip = this.optionalTripService.create(hotel);

		final FormTrip formTrip = this.optionalTripService.createMyForm(trip);
		result = this.createEditModelAndView(formTrip);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;

		OptionalTrip trip;
		trip = this.optionalTripService.findOne(tripId);

		final FormTrip formtrip = this.optionalTripService.createMyForm(trip);
		result = this.createEditModelAndView(formtrip);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final FormTrip formTrip, final BindingResult binding) {
		ModelAndView result;
		OptionalTrip trip;

		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			Assert.isTrue(formTrip.getCheckIn().before(formTrip.getCheckOut()));
			final Date now = new Date();
			Assert.isTrue(formTrip.getCheckIn().after(now));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formTrip, "trip.commit.error");
			return result;
		}

		try {
			Assert.isTrue(formTrip.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formTrip.getHotel()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(formTrip, "trip.error.hotel");
			return result;
		}

		trip = this.optionalTripService.reconstruct(formTrip, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(formTrip);
		else
			try {
				this.optionalTripService.save(trip);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(formTrip, "trip.commit.error");
			}

		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FormTrip formTrip) {
		ModelAndView result;
		final OptionalTrip trip = this.optionalTripService.findOne(formTrip.getId());
		final Actor actor = this.actorService.findByPrincipal();

		try {
			Assert.isTrue(trip.getHotel().getRequest().getManager().getId() == actor.getId());
			this.optionalTripService.delete(formTrip.getId());
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "trip.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final FormTrip form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormTrip form, final String message) {
		ModelAndView result;

		result = new ModelAndView("trip/edit");
		result.addObject("requestURI", "trip/create.do");

		result.addObject("formTrip", form);
		result.addObject("message", message);
		return result;

	}

}
