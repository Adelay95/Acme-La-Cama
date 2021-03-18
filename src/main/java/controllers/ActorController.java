
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
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ClientService;
import services.ManagerService;
import domain.Actor;
import domain.Brand;
import domain.Client;
import domain.Manager;
import forms.FormActor;

@Controller
@RequestMapping("/actor/client")
public class ActorController extends AbstractController {

	//Services ------------------------------------------------------------

	@Autowired
	private ClientService	clientService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;


	//Constructors --------------------------------------------------------

	public ActorController() {
		super();
	}

	//Listing -------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();
		Collection<Actor> actors;
		this.actorService.findAll();
		actors = this.actorService.todosActoresMenosYo(actor.getId());
		Manager mana = null;
		if (actor instanceof Manager)
			mana = this.managerService.findByPrincipal();

		result = new ModelAndView("actor/client/list");
		result.addObject("requestURI", "actor/client/list.do");
		result.addObject("manager", mana);
		result.addObject("workers", actors);
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
		final Client me = this.clientService.findByPrincipal();
		result = this.createEditModelAndViewE(me);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(final FormActor formActor, final BindingResult binding) {
		ModelAndView result;
		Client client;
		try {

			client = this.clientService.reconstruct(formActor, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(formActor, "actor.commit.error");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(formActor);
		else
			try {
				Assert.isTrue(this.clientService.checkDNI(client.getDniNif()));
				Assert.isTrue(this.clientService.checkExceptions(client));

				final Client client2 = this.clientService.saveUserAccount(formActor, client);
				this.clientService.save(client2);
				result = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(formActor, "actor.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Client actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewE(actor);
		else
			try {
				Assert.isTrue(this.clientService.checkExceptions(actor));
				this.clientService.save(actor);
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
	protected ModelAndView createEditModelAndViewE(final Client form) {
		ModelAndView result;

		result = this.createEditModelAndViewE(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormActor form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/client/create");
		result.addObject("requestURI", "actor/client/create.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("formActor", form);
		result.addObject("message", message);
		return result;

	}
	protected ModelAndView createEditModelAndViewE(final Client form, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/client/edit");
		result.addObject("requestURI", "actor/client/edit.do");

		final Collection<String> brands = new HashSet<String>();
		for (final Brand p : Brand.values())
			brands.add(p.toString());

		result.addObject("brands", brands);
		result.addObject("actor", form);
		result.addObject("message", message);
		return result;

	}
}
