
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Hotel;
import domain.Manager;

@Controller
@RequestMapping("/statistics")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(@RequestParam final int hotelId) {
		final ModelAndView result;

		final Double ocupacion = this.administratorService.ocupacionHotelera(hotelId);
		final Double comment = this.administratorService.mediaStarsComentarios(hotelId);
		final Double bill = this.administratorService.mediaGastado(hotelId);
		final Long reserva = this.administratorService.numClientesReservas(hotelId);
		final Double trip = this.administratorService.mediaAsistenciaExcur(hotelId);

		result = new ModelAndView("administrator/dashboard");
		result.addObject("ocupacion", ocupacion);
		result.addObject("comment", comment);
		result.addObject("bill", bill);
		result.addObject("reserva", reserva);
		result.addObject("trip", trip);

		return result;
	}
	@RequestMapping(value = "/dashboard_admin", method = RequestMethod.GET)
	public ModelAndView dashboard2() {
		final ModelAndView result;

		final Integer min = this.administratorService.min();
		final Double avg = this.administratorService.avg();
		final Integer max = this.administratorService.max();
		final Integer minWorker = this.administratorService.minWorker();
		final Double avgWorker = this.administratorService.avgWorker();
		final Integer maxWorker = this.administratorService.maxWorker();

		result = new ModelAndView("administrator/dashboard2");
		result.addObject("min", min);
		result.addObject("avg", avg);
		result.addObject("max", max);
		result.addObject("minWorker", minWorker);
		result.addObject("avgWorker", avgWorker);
		result.addObject("maxWorker", maxWorker);

		return result;
	}
	@RequestMapping(value = "/hotel", method = RequestMethod.GET)
	public ModelAndView dashboard3() {
		final ModelAndView result;

		final Collection<Hotel> res = this.administratorService.ordenados();

		result = new ModelAndView("hotel/list");
		result.addObject("requestURI", "statistics/hotel.do");
		result.addObject("hotels", res);

		return result;
	}
	@RequestMapping(value = "/manajer", method = RequestMethod.GET)
	public ModelAndView dashboard4() {
		final ModelAndView result;

		final Collection<Manager> res = this.administratorService.ordenado();

		result = new ModelAndView("actor/manajer/list");
		result.addObject("requestURI", "statistics/manajer.do");
		result.addObject("managers", res);

		return result;
	}
}
