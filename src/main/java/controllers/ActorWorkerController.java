
package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HotelService;
import services.ManagerService;
import services.WorkerService;
import domain.Brand;
import domain.Hotel;
import domain.Manager;
import domain.Worker;
import forms.FormActor;

@Controller
@RequestMapping("/actor/worker")
public class ActorWorkerController extends AbstractController {

	//Services ------------------------------------------------------------

	@Autowired
	private WorkerService	workerService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private ManagerService	managerService;


	//Constructors --------------------------------------------------------

	public ActorWorkerController() {
		super();
	}

	//Listing -------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int hotelId) {
		ModelAndView result;
		final Hotel hotel = this.hotelService.findOne(hotelId);
		final FormActor form = new FormActor();
		form.setHotel(hotel);
		result = this.createEditModelAndView(form);
		return result;
	}

	@RequestMapping(value = "/listWorkers", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Manager manager = this.managerService.findByPrincipal();
		Collection<Worker> workers;
		workers = this.managerService.todosMisTrabajadores(manager.getId());

		result = new ModelAndView("actor/worker/listWorkers");
		result.addObject("requestURI", "actor/worker/listWorkers.do");
		result.addObject("workers", workers);
		return result;
	}

	@RequestMapping(value = "/hotel/manajer/list", method = RequestMethod.GET)
	public ModelAndView listhotel(@RequestParam final int hotelId) {
		ModelAndView result;

		final Manager manager = this.managerService.findByPrincipal();
		final Hotel hotel = this.hotelService.findOne(hotelId);
		Collection<Worker> workers;
		workers = hotel.getWorkers();
		Assert.isTrue(hotel.getRequest().getManager().equals(manager));

		result = new ModelAndView("actor/worker/listWorkers");
		result.addObject("requestURI", "actor/worker/hotel/manajer/list.do");
		result.addObject("workers", workers);
		result.addObject("hotelId", hotelId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Worker me = this.workerService.findByPrincipal();
		result = this.createEditModelAndViewE(me);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final FormActor formActor, final BindingResult binding) {
		ModelAndView result;
		Worker worker;
		try {
			worker = this.workerService.reconstruct(formActor, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formActor, "actor.commit.error");
			return result;
		}
		try {
			Assert.isTrue(formActor.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formActor.getHotel()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formActor, "actor.error.hotel");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(formActor);
		else
			try {

				Assert.isTrue(this.workerService.checkExceptions(worker));
				final Worker worker2 = this.workerService.saveUserAccount(formActor, worker);
				this.workerService.save(worker2);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(formActor, "actor.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Worker actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewE(actor);
		else
			try {
				Assert.isTrue(this.workerService.checkExceptions(actor));
				this.workerService.save(actor);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {

				result = this.createEditModelAndViewE(actor, "actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int workerId) {
		ModelAndView result;
		final Worker worker = this.workerService.findOne(workerId);
		final int hotelId = worker.getHotel().getId();
		final Manager me = this.managerService.findByPrincipal();
		final Manager hotel = worker.getHotel().getRequest().getManager();
		try {
			Assert.isTrue(me.getBanned() == false);
			Assert.isTrue(me.getId() == hotel.getId());
			this.workerService.delete(worker);
			result = new ModelAndView("redirect:hotel/manajer/list.do?hotelId=" + hotelId);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:hotel/manajer/list.do?hotelId=" + hotelId);
			result.addObject("message", "actor.commit.error");
		}
		return result;
	}

	//Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FormActor form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}
	protected ModelAndView createEditModelAndViewE(final Worker form) {
		ModelAndView result;

		result = this.createEditModelAndViewE(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormActor form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/worker/create");
		result.addObject("requestURI", "actor/worker/create.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("formActor", form);
		result.addObject("message", message);
		return result;

	}
	protected ModelAndView createEditModelAndViewE(final Worker form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/worker/edit");
		result.addObject("requestURI", "actor/worker/edit.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("actor", form);
		result.addObject("message", message);
		return result;

	}
}
