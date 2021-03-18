
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
import services.CommentService;
import services.HotelService;
import domain.Bill;
import domain.Client;
import domain.Comment;
import domain.Hotel;
import domain.Reservation;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ClientService	clientService;


	public CommentController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int hotelId) {
		ModelAndView result;
		Hotel hotel = hotelService.findOne(hotelId);
		Comment comment = commentService.create(hotel);
		result = createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int commentId) {
		ModelAndView result;
		Comment comment = commentService.findOne(commentId);
		result = createEditModelAndView(comment);

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;

		Collection<Comment> comments;
		Client client=this.clientService.findByPrincipal();
		comments = client.getComments();

		result = new ModelAndView("comment/list");
		result.addObject("requestURI", "comment/list.do");
		result.addObject("comments", comments);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		Client client=clientService.findByPrincipal();
		try{
			if(comment.getId()!=0){
			
			Assert.isTrue(comment.getClient().getId()==client.getId());
			}
		} catch(Exception oops){
			result = createEditModelAndView(comment, "comment.commit.notAuthorized");
			return result;
		}
		try{
			if(comment.getId()==0){
			Date now=new Date();
			List<Reservation> reservationThisHotel= this.clientService.reservationThisHotel(comment.getHotel().getId());
			Assert.isTrue(reservationThisHotel.get(0).getCheckIn().before(now));
			}
		} catch(Exception oops){
			result = createEditModelAndView(comment, "comment.commit.invalidHotel");
			return result;
		}
		if (binding.hasErrors()) {
			result = createEditModelAndView(comment);
		} else {
			try {
				commentService.save(comment);
				result = new ModelAndView("redirect:list.do");
			} catch (Exception oops) {
				result = createEditModelAndView(comment, "comment.commit.error");
			}

		}
		return result;

	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Comment comment, BindingResult binding) {
		ModelAndView result;
		Client client=clientService.findByPrincipal();
		try{
			Assert.isTrue(comment.getClient().getId()==client.getId());
		} catch(Exception oops){
			result = createEditModelAndView(comment, "comment.commit.notAuthorized");
			return result;
		}
		
			try {
				commentService.delete(comment);
				result = new ModelAndView("redirect:list.do");
			} catch (Exception oops) {
				result = createEditModelAndView(comment, "comment.commit.error");
			}
		return result;

	}

	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;

		result = createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Comment comment, String message) {
		ModelAndView result;
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/create.do?hotelId="+comment.getId());
		result.addObject("message", message);
		return result;

	}

}
