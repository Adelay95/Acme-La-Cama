
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

import services.BillLineService;
import services.BillService;
import services.ClientService;
import services.HotelService;
import services.ManagerService;
import services.ReservationService;
import services.RoomService;
import services.WorkerService;
import domain.Bill;
import domain.BillLine;
import domain.Client;
import domain.KindOfOffert;
import domain.Reservation;
import domain.Room;

@Controller
@RequestMapping("/reservation")
public class ReservationController extends AbstractController{

	// Services ---------------------------------------------------------------
	@Autowired
	private RoomService		roomService;
	@Autowired
	private ReservationService	reservationService;
	@Autowired
	private BillService billService;
	@Autowired
	private BillLineService billLineService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ClientService	clientService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private WorkerService	workerService;


	// Constructors ----------------------------------------------------------
	public ReservationController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/client/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Client client = this.clientService.findByPrincipal();
		
		Collection<Reservation> reservations;
		reservations = client.getReservations();

		Date now =new Date();
		result = new ModelAndView("reservation/list");
		result.addObject("requestURI", "reservation/client/list.do");
		result.addObject("reservations", reservations);
		result.addObject("now", now);
		return result;
	}
	
	@RequestMapping(value = "/hotel/list", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam int hotelId) {
		ModelAndView result;
		
		Collection<Reservation> reservations;
		reservations = hotelService.getAllReservations(hotelId);
		result = new ModelAndView("reservation/list");
		result.addObject("requestURI", "reservation/hotel/list.do?hotelId="+hotelId);
		result.addObject("reservations", reservations);
		
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/setAttributes", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int roomId) {
		ModelAndView result;
		Client me = clientService.findByPrincipal();
		Room room= roomService.findOne(roomId);
		Reservation reservation=reservationService.create(me,room);
		result = this.createEditModelAndViewS(reservation);
		return result;
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create2(@RequestParam int reservationId) {
		ModelAndView result;
		Reservation reservation = reservationService.findOne(reservationId);
		result = this.createEditModelAndViewS(reservation);
		return result;
	}


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final Reservation reservation) {
		ModelAndView result;
		final Double offertPrice = this.reservationService.calculateAuthomaticPrice(reservation);
		reservation.setPriceDay(offertPrice);
		result = this.createEditModelAndView(reservation);
		return result;
	}

	@RequestMapping(value = "/setAttributes", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(final Reservation reservation, final BindingResult binding) {
		ModelAndView result;
		try {
			if(reservation.getId()!=0){
				Assert.isTrue(clientService.findByPrincipal().getId()==reservation.getClient().getId());
			}
			Assert.isTrue(this.workerService.checkGoodHotel(reservation.getRooms().getHotel()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewS(reservation, "reservation.error.hotel");
			return result;
		}
		result = this.create(reservation);
		return result;

	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@Valid final Reservation reservation, final BindingResult binding) {
		ModelAndView result;
		Collection<Date> reservoirDates=new HashSet<Date>();
		
		try {
			Date now = new Date();
			Assert.isTrue(now.before(reservation.getCheckIn()));
			if(!reservation.getCheckIn().equals(reservation.getCheckOut())){
			Assert.isTrue(reservation.getCheckIn().before(reservation.getCheckOut()));
			}
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(reservation, "reservation.error.dates");
			return result;
		}
		try {
			Assert.isTrue(this.reservationService.checkGoodHotel(reservation.getRooms().getHotel(),reservation.getCheckIn(),reservation.getCheckOut()));
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(reservation, "reservation.error.hotel");
			return result;
		}
		
		try {
			reservoirDates=this.reservationService.calcularDates(reservation);
			Assert.isTrue(this.reservationService.checkDispnibilidadHabitación(reservation,reservoirDates));
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(reservation, "reservation.error.roomOccuppied");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(reservation);
		else{
			try {
				reservoirDates=this.reservationService.calcularDates(reservation);
				reservation.setNumDays(reservoirDates.size());
		        Reservation res2=this.reservationService.saveAgore(reservation,reservoirDates);
		        Reservation res3=this.reservationService.makeTheBill(res2);
				this.reservationService.save(res3);
				result = new ModelAndView("redirect:client/list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(reservation, "reservation.commit.error");
			}
		}
		return result;

	}

	//Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Reservation reservation) {
		ModelAndView result;

		result = this.createEditModelAndView(reservation, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewS(final Reservation reservation) {
		ModelAndView result;

		result = this.createEditModelAndViewS(reservation, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewS(final Reservation reservation, final String message) {
		ModelAndView result;

		result = new ModelAndView("reservation/setAttributes");
		result.addObject("requestURI", "reservation/setAttributes.do");

		final Collection<String> kindOfOffert = new HashSet<String>();
		for (final KindOfOffert p : KindOfOffert.values())
			kindOfOffert.add(p.toString());

		result.addObject("kindOfOffert", kindOfOffert);
		result.addObject("reservation", reservation);
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Reservation reservation, final String message) {
		ModelAndView result;

		result = new ModelAndView("reservation/create");
		result.addObject("requestURI", "reservation/create.do");

		result.addObject("reservation", reservation);
		result.addObject("message", message);
		return result;

	}

}
