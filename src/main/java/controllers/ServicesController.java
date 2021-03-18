
package controllers;

import java.util.Collection;

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
import services.ManagerService;
import services.ServicesService;
import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.Services;

@Controller
@RequestMapping("/services")
public class ServicesController extends AbstractController {

	@Autowired
	private ServicesService	servicesService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;


	public ServicesController() {
		super();
	}

	@RequestMapping(value = "/petition", method = RequestMethod.GET)
	public ModelAndView petition() {
		ModelAndView result;
		final Services services = this.servicesService.createManager();
		result = this.createEditModelAndView2(services);
		return result;

	}

	@RequestMapping(value = "/petition", method = RequestMethod.POST, params = "save2")
	public ModelAndView save2(@Valid final Services services, final BindingResult binding) {
		ModelAndView result;
		  final Manager actor = this.managerService.findByPrincipal();
		  Assert.isTrue(actor instanceof Manager);
		  final Collection<String> allServices = this.servicesService.getAllNames();

		  if (allServices.contains(services.getName()))
		   result = this.createEditModelAndView2(services, "services.name.commit.error");
		  else if (binding.hasErrors())
		   result = this.createEditModelAndView2(services);
		  else
		   try {
		    try {
		     final Manager yo = this.managerService.findByPrincipal();
		     Assert.isTrue(!yo.getBanned());

		    } catch (final Throwable oops) {
		     result = this.createEditModelAndView(services, "services.name.commit.error");
		     return result;
		    }
		    this.servicesService.sendPetition(services);
		    result = new ModelAndView("redirect:/");
		   } catch (final Exception oops) {
		    result = this.createEditModelAndView(services, "services.commit.error");
		   }
		  return result;
	}

	protected ModelAndView createEditModelAndView2(final Services services) {
		ModelAndView result;

		result = this.createEditModelAndView2(services, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Services services, final String message) {
		ModelAndView result;
		result = new ModelAndView("services/petition");
		result.addObject("services", services);
		result.addObject("requestURI", "services/petition.do");
		result.addObject("message", message);
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Integer roomId = 0;
		Collection<Services> services;
		services = this.servicesService.allServices();
		result = new ModelAndView("services/list");
		result.addObject("services", services);
		result.addObject("requestURI", "services/list.do");
		result.addObject("roomId", roomId);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Services services = this.servicesService.create();
		result = this.createEditModelAndView(services);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int servicesId) {
		ModelAndView result;
		Services service;
		service = this.servicesService.findOne(servicesId);
		Assert.notNull(service);
		result = this.createEditModelAndView(service);
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int servicesId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);

		final Services services = this.servicesService.findOne(servicesId);
		try {
			this.servicesService.delete(services);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(services, "services.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Services services, final BindingResult binding) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		final Collection<String> allServices = this.servicesService.getAllNames();
		if (allServices.contains(services.getName()))
			result = this.createEditModelAndView(services, "services.name.commit.error");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(services);
		else
			try {
				this.servicesService.save(services);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception oops) {
				result = this.createEditModelAndView(services, "services.commit.error");
			}

		return result;

	}
	protected ModelAndView createEditModelAndView(final Services services) {
		ModelAndView result;

		result = this.createEditModelAndView(services, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Services services, final String message) {
		ModelAndView result;
		result = new ModelAndView("services/edit");
		result.addObject("services", services);
		result.addObject("requestURI", "services/edit.do");
		result.addObject("message", message);
		return result;

	}

}
