
package controllers.client;

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
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
import controllers.AbstractController;
import domain.Client;
import domain.Finder;
import domain.KindOfOffert;
import domain.KindOfRoom;
import domain.Offert;
import domain.Room;
import domain.Terrain;

@Controller
@RequestMapping("/finder/client")
public class FinderClientController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ClientService	clientService;


	// Constructors ----------------------------------------------------------
	public FinderClientController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		final Client client = this.clientService.findByPrincipal();
		Finder finder = client.getFinder();
		if (finder == null)
			try {
				finder = this.clientService.createFinder(client);
				result = new ModelAndView("finder/display");
				result.addObject("requestURI", "finder/client/display.do");
				result.addObject("finder", finder);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/?message=client.commit.error");
			}
		else {
			result = new ModelAndView("finder/display");
			result.addObject("requestURI", "finder/client/display.do");
			result.addObject("finder", finder);
		}
		return result;
	}

	@RequestMapping(value = "/room/result", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Client client = this.clientService.findByPrincipal();
		if (client.getFinder() == null)
			result = new ModelAndView("redirect:display.do");
		else {
			Collection<Room> rooms;
			rooms = this.clientService.finderRoomsResults();
			result = new ModelAndView("room/list");
			result.addObject("requestURI", "/finder/client/room/result.do");
			result.addObject("rooms", rooms);
		}
		return result;
	}
	@RequestMapping(value = "/offert/result", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView result;
		final Client client = this.clientService.findByPrincipal();
		if (client.getFinder() == null)
			result = new ModelAndView("redirect:display.do");
		else {
			final Date now = new Date();
			Collection<Offert> offerts;
			offerts = this.clientService.finderOffertsResults();
			result = new ModelAndView("offert/list");
			result.addObject("requestURI", "/finder/client/offert/result.do");
			result.addObject("offerts", offerts);
			result.addObject("now", now);
		}
		return result;
	}

	// Edition ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Client client = this.clientService.findByPrincipal();
		if (client.getFinder() == null)
			result = new ModelAndView("redirect:display.do");
		else {
			Finder finder;
			finder = client.getFinder();
			Assert.notNull(finder);
			result = this.createEditModelAndView(finder);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.clientService.saveFinder(finder);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;
		final Collection<String> terrains = new HashSet<String>();
		for (final Terrain t : Terrain.values())
			terrains.add(t.toString());
		final Collection<String> kindOfOfferts = new HashSet<String>();
		for (final KindOfOffert k : KindOfOffert.values())
			kindOfOfferts.add(k.toString());
		final Collection<String> kindOfRooms = new HashSet<String>();
		for (final KindOfRoom k : KindOfRoom.values())
			kindOfRooms.add(k.toString());
		result = new ModelAndView("finder/edit");
		result.addObject("requestURI", "finder/client/edit.do");
		result.addObject("finder", finder);
		result.addObject("kindOfOfferts", kindOfOfferts);
		result.addObject("kindOfRooms", kindOfRooms);
		result.addObject("terrains", terrains);
		result.addObject("message", message);
		return result;
	}
}
