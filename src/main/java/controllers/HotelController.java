
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.HotelService;
import services.ManagerService;
import domain.Actor;
import domain.Comment;
import domain.Hotel;
import domain.Manager;

@Controller
@RequestMapping("/hotel")
public class HotelController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private CommentService	commentService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;


	// Constructors ----------------------------------------------------------
	public HotelController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Hotel> hotels;
		hotels = this.hotelService.allHotelAccepted();
		final Date hoy = new Date();
		result = new ModelAndView("hotel/list");
		result.addObject("requestURI", "hotel/list.do");
		result.addObject("hotels", hotels);
		result.addObject("hoy", hoy);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int hotelId) {
		ModelAndView result;
		Manager casteel = null;
		try{
		final Actor actor = this.actorService.findByPrincipal();
		
		if (actor instanceof Manager)
			casteel = this.managerService.findByPrincipal();
		} catch(Exception oops){
			
		}
		final Hotel hotel = this.hotelService.findOne(hotelId);

		final Collection<Comment> comments = this.commentService.cometariosOrdenadosHotel(hotelId);
		result = new ModelAndView("hotel/display");
		result.addObject("requestURI", "hotel/display.do");
		result.addObject("hotel", hotel);
		result.addObject("comments", comments);
		result.addObject("hi", casteel);

		return result;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView report(@RequestParam final int hotelId) {
		ModelAndView result;
		this.hotelService.reportHotel(hotelId);
		result = list();
		result.addObject("message","hotel.commit.ok");
		return result;
	}
}
