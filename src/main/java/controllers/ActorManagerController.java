
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

import services.AdministratorService;
import services.ManagerService;
import domain.Brand;
import domain.Manager;
import forms.FormActor;

@Controller
@RequestMapping("/actor/manajer")
public class ActorManagerController extends AbstractController {

	//Services ------------------------------------------------------------

	@Autowired
	private ManagerService			managerService;
	@Autowired
	private AdministratorService	administratorService;


	//Constructors --------------------------------------------------------

	public ActorManagerController() {
		super();
	}

	//Listing -------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Manager> managers;
		managers = this.managerService.allManagers();

		result = new ModelAndView("actor/manajer/list");
		result.addObject("requestURI", "actor/manajer/list.do");
		result.addObject("managers", managers);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final FormActor form = new FormActor();
		result = this.createEditModelAndView(form);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Manager me = this.managerService.findByPrincipal();
		result = this.createEditModelAndViewE(me);
		return result;

	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;
		try {
			this.administratorService.banManager(actorId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?message=actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView result;
		try {
			this.administratorService.unbanManager(actorId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?message=actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final FormActor formActor, final BindingResult binding) {
		ModelAndView result;
		Manager manager;
		try {
			manager = this.managerService.reconstruct(formActor, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formActor, "actor.commit.error");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(formActor);
		else
			try {

				Assert.isTrue(this.managerService.checkExceptions(manager));

				final Manager manager2 = this.managerService.saveUserAccount(formActor, manager);
				this.managerService.save(manager2);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(formActor, "actor.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Manager actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewE(actor);
		else
			try {
				Assert.isTrue(this.managerService.checkExceptions(actor));
				this.managerService.save(actor);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {

				result = this.createEditModelAndViewE(actor, "actor.commit.error");
			}
		return result;
	}

	//Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FormActor form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}
	protected ModelAndView createEditModelAndViewE(final Manager form) {
		ModelAndView result;

		result = this.createEditModelAndViewE(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormActor form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/manajer/create");
		result.addObject("requestURI", "actor/manajer/create.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("formActor", form);
		result.addObject("message", message);
		return result;

	}
	protected ModelAndView createEditModelAndViewE(final Manager form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/manajer/edit");
		result.addObject("requestURI", "actor/manajer/edit.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("actor", form);
		result.addObject("message", message);
		return result;

	}
}
