
package controllers.manager;

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

import services.HotelService;
import services.ManagerService;
import services.RequestService;
import controllers.AbstractController;
import domain.Hotel;
import domain.Manager;
import domain.Request;
import domain.State;
import domain.Terrain;
import forms.FormHotel;

@Controller
@RequestMapping("/hotel/manajer")
public class HotelManagerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private RequestService	requestService;


	// Constructors ----------------------------------------------------------
	public HotelManagerController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Integer managerId = this.managerService.findByPrincipal().getId();
		Collection<Hotel> hotels;
		hotels = this.hotelService.allHotelManager(managerId);
		final Date hoy = new Date();
		result = new ModelAndView("hotel/list");
		result.addObject("requestURI", "hotel/manajer/list.do");
		result.addObject("hotels", hotels);
		result.addObject("hoy", hoy);
		return result;
	}

	// Edition ----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Hotel hotel;
		final Manager manager = this.managerService.findByPrincipal();
		final Request request = this.requestService.create(manager);
		hotel = this.hotelService.create(request);
		final FormHotel formHotel = this.hotelService.createMyForm(hotel, request);
		result = this.createEditModelAndView(formHotel);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int hotelId) {
		ModelAndView result;
		Hotel hotel;
		Request request;
		hotel = this.hotelService.findOne(hotelId);
		Assert.notNull(hotel);
		final Manager manager = this.managerService.findByPrincipal();
		Assert.isTrue(hotel.getRequest().getManager().equals(manager));
		final Integer requestId = hotel.getRequest().getId();
		request = this.requestService.findOne(requestId);
		final FormHotel formHotel = this.hotelService.createMyForm(hotel, request);
		result = this.createEditModelAndView(formHotel);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final FormHotel formHotel, final BindingResult binding) {
		ModelAndView result;
		Hotel hotel;
		try {
			Manager yo=this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formHotel, "hotel.commit.error");
			return result;
		}
		try {
			hotel = this.hotelService.reconstruct(formHotel, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formHotel, "hotel.commit.error");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(formHotel);
		else
			try {
				this.managerService.saveFormHotel(formHotel, hotel);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(formHotel, "hotel.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FormHotel formHotel, final BindingResult binding) {
		ModelAndView result;
		final Hotel hotel = this.hotelService.findOne(formHotel.getId());
		try {
			final Date hoy = new Date();
			final Date checkOut = hotel.getRequest().getTimeOut();
			Assert.isTrue(hotel.getRequest().getState().equals(State.ACCEPTED) == false || hoy.after(checkOut));
			this.managerService.deleteHotel(hotel);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formHotel, "hotel.commit.error");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FormHotel formHotel) {
		ModelAndView result;
		result = this.createEditModelAndView(formHotel, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final FormHotel formHotel, final String message) {
		ModelAndView result;
		final Collection<String> terrains = new HashSet<String>();
		for (final Terrain t : Terrain.values())
			terrains.add(t.toString());
		result = new ModelAndView("hotel/edit");
		result.addObject("requestURI", "hotel/manajer/edit.do");
		result.addObject("formHotel", formHotel);
		result.addObject("terrains", terrains);
		result.addObject("message", message);
		return result;
	}

}
