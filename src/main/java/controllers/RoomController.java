
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.HotelService;
import services.ManagerService;
import services.RoomService;
import services.ServicesService;
import services.WorkerService;
import domain.Actor;
import domain.Hotel;
import domain.KindOfRoom;
import domain.Manager;
import domain.Room;
import domain.Services;
import domain.Worker;
import forms.FormRoom;

@Controller
@RequestMapping("/room")
public class RoomController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RoomService		roomService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ServicesService	servicesService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private WorkerService	workerService;


	// Constructors ----------------------------------------------------------
	public RoomController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/worker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Boolean principal = false;
		final Worker worker = this.workerService.findByPrincipal();

		final Hotel hotel = worker.getHotel();

		Collection<Room> rooms;
		rooms = this.roomService.myRoomazos(hotel.getId());

		result = new ModelAndView("room/list");
		result.addObject("requestURI", "room/worker/list.do");
		result.addObject("rooms", rooms);
		result.addObject("principal", principal);
		return result;
	}
	@RequestMapping(value = "/hotel/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int hotelId) {
		ModelAndView result;
		Boolean principal = false;
		final Actor actor = this.actorService.findByPrincipal();

		final Integer idManager = this.hotelService.idManagerHotel(hotelId);
		if (actor.getId() == idManager)
			principal = true;

		Collection<Room> rooms;
		rooms = this.roomService.allRoomHotel(hotelId);

		result = new ModelAndView("room/list");
		result.addObject("requestURI", "room/hotel/list.do");
		result.addObject("rooms", rooms);
		result.addObject("principal", principal);

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int roomId, final String message) {
		ModelAndView result;
		Boolean principal = false;
		final Room room = this.roomService.findOne(roomId);
		final Actor actor = this.actorService.findByPrincipal();
		final Integer idManager = this.hotelService.idManagerHotel(room.getHotel().getId());
		if (actor.getId() == idManager)
			principal = true;

		final String[] olakase = new String[room.getOccupiedDays().size()];
		int i = 0;
		for (final Date e : room.getOccupiedDays()) {
			olakase[i] = "" + e;
			i++;
		}
		final Collection<Services> services = this.servicesService.AllServicesRoom(roomId);
		result = new ModelAndView("room/display");
		result.addObject("requestURI", "room/display.do");
		result.addObject("room", room);
		result.addObject("datesReserva", olakase);
		result.addObject("miLength", olakase.length);
		result.addObject("principal", principal);
		result.addObject("services", services);
		if ((message != null) && (message != ""))
			result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/addservices", method = RequestMethod.GET)
	public ModelAndView listService(@RequestParam final int roomId, final String message) {
		ModelAndView result;

		final Manager manager = this.managerService.findByPrincipal();
		final Room room = this.roomService.findOne(roomId);
		Assert.isTrue(room.getHotel().getRequest().getManager().equals(manager));
		Collection<Services> services;
		services = this.servicesService.noServicesRoom(roomId);
		result = new ModelAndView("services/list");
		result.addObject("requestURI", "room/addservices.do");
		if ((message != null) && (message != ""))
			result.addObject("message", message);
		result.addObject("services", services);
		result.addObject("roomId", roomId);
		return result;
	}
	@RequestMapping(value = "/addservice", method = RequestMethod.GET)
	public ModelAndView addService(@RequestParam final int roomId, @RequestParam final int serviceId) {
		ModelAndView result;
		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			this.managerService.addService(roomId, serviceId);

			result = new ModelAndView("redirect:display.do?roomId=" + roomId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:addservices.do?roomId=" + roomId + "&message=room.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/removeservice", method = RequestMethod.GET)
	public ModelAndView removeService(@RequestParam final int roomId, @RequestParam final int serviceId) {
		ModelAndView result;
		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			this.managerService.removeService(roomId, serviceId);
			result = new ModelAndView("redirect:display.do?roomId=" + roomId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?roomId=" + roomId + "&message=room.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final FormRoom formRoom) {
		ModelAndView result;
		final Double orginalPrice = this.roomService.calculateAuthomaticPrice(formRoom);
		formRoom.setOriginalPriceDays(orginalPrice);
		final Integer roomNumber = this.roomService.getMaxNumberHotel(formRoom.getHotel().getId());
		formRoom.setNumber(roomNumber + 1);
		result = this.createEditModelAndView(formRoom);
		return result;
	}

	@RequestMapping(value = "/setAttributes", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int hotelId) {
		ModelAndView result;
		final Hotel hotel = this.hotelService.findOne(hotelId);
		final FormRoom form = new FormRoom();
		form.setHotel(hotel);
		result = this.createEditModelAndViewS(form);
		return result;
	}

	@RequestMapping(value = "/setAttributes", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(final FormRoom formRoom, final BindingResult binding) {
		ModelAndView result;
		try {
			Assert.isTrue(formRoom.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formRoom.getHotel()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewS(formRoom, "room.error.hotel");
			return result;
		}
		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());

		} catch (final Throwable oops) {
			result = this.createEditModelAndViewS(formRoom, "room.commit.error");
			return result;
		}
		result = this.create(formRoom);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final FormRoom formRoom, final BindingResult binding) {
		ModelAndView result;
		Room room;

		try {
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formRoom, "room.commit.error");
			return result;
		}
		try {
			if (!formRoom.getCheckAutomaticPrice())
				Assert.isTrue(formRoom.getPersonalPriceDays() != null);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formRoom, "room.commit.check");
			return result;
		}
		try {
			Assert.isTrue(formRoom.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formRoom.getHotel()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(formRoom, "room.error.hotel");
			return result;
		}
		try {
			Assert.isTrue(this.roomService.checkCapacityHotel(formRoom.getHotel(), formRoom.getNumberOfRooms()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(formRoom, "room.error.capacity");
			return result;
		}
		room = this.roomService.reconstruct(formRoom, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(formRoom);
		else
			try {
				Assert.isTrue(formRoom.getNumberOfRooms() > 0);
				final Collection<Services> services = this.servicesService.getServicesByNames(formRoom.getServices());
				room.setServices(services);
				this.roomService.saveMultipleRooms(services, formRoom, room);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(formRoom, "room.commit.error");
			}

		return result;

	}

	//Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FormRoom form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewS(final FormRoom form) {
		ModelAndView result;

		result = this.createEditModelAndViewS(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewS(final FormRoom form, final String message) {
		ModelAndView result;

		result = new ModelAndView("room/setAttributes");
		result.addObject("requestURI", "room/setAttributes.do");

		final Collection<String> kindOfRoom = new HashSet<String>();
		for (final KindOfRoom p : KindOfRoom.values())
			kindOfRoom.add(p.toString());

		result.addObject("kindOfRoom", kindOfRoom);
		result.addObject("formRoom", form);
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditModelAndView(final FormRoom form, final String message) {
		ModelAndView result;

		result = new ModelAndView("room/create");
		result.addObject("requestURI", "room/create.do");

		final Collection<String> kindOfRoom = new HashSet<String>();
		for (final KindOfRoom p : KindOfRoom.values())
			kindOfRoom.add(p.toString());

		final Collection<String> servicesNames = this.servicesService.getAllNames();
		result.addObject("kindOfRoom", kindOfRoom);
		result.addObject("servicesNames", servicesNames);
		result.addObject("formRoom", form);
		result.addObject("message", message);
		return result;

	}

}
