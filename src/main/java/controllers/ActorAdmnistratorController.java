
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

import services.AdministratorService;
import domain.Administrator;
import domain.Brand;
import domain.Client;
import forms.FormActor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdmnistratorController extends AbstractController{

	//Services ------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	//Constructors --------------------------------------------------------

	public ActorAdmnistratorController() {
		super();
	}

	//Listing -------------------------------------------------------------

	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator me = administratorService.findByPrincipal();
		result = this.createEditModelAndViewE(me);
		return result;

	}

	
	@RequestMapping(value= "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save(@ModelAttribute("actor") @Valid Administrator actor, BindingResult binding){
		ModelAndView result;
		
		
		if(binding.hasErrors()){
			result= createEditModelAndViewE(actor);
		}else{
			try{
				
				this.administratorService.save(actor);
				result = new ModelAndView("redirect:/");
			} catch(Throwable oops){
				
				result=createEditModelAndViewE(actor, "actor.commit.error");}
		}
		return result;
		}
		


	//Ancillary methods --------------------------------------------------------


	protected ModelAndView createEditModelAndViewE(final Administrator form) {
		ModelAndView result;

		result = this.createEditModelAndViewE(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewE(final Administrator form, final String message) {
		ModelAndView result;
			
				result = new ModelAndView("actor/administrator/edit");
				result.addObject("requestURI", "actor/administrator/edit.do");

		result.addObject("actor", form);
		result.addObject("message", message);
		return result;

	}
}
