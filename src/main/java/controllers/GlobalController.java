
package controllers;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.GlobalService;
import domain.Global;
import domain.Season;

@Controller
@RequestMapping("/global")
public class GlobalController extends AbstractController {

	@Autowired
	private GlobalService	globalService;


	public GlobalController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Global global;
		global = this.globalService.findTheGlobal();
		Assert.notNull(global);
		result = this.createEditModelAndView(global);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Global global, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(global);
		else
			try {
				this.globalService.save(global);
				result = new ModelAndView("redirect:/");
			} catch (final Exception oops) {
				result = this.createEditModelAndView(global, "global.commit.error");
			}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Global global) {
		ModelAndView result;

		result = this.createEditModelAndView(global, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Global global, final String message) {
		ModelAndView result;

		final Collection<String> season = new HashSet<String>();
		for (final Season p : Season.values())
			season.add(p.toString());

		result = new ModelAndView("global/edit");
		result.addObject("global", global);
		result.addObject("season", season);
		result.addObject("requestURI", "global/edit.do");
		result.addObject("message", message);
		return result;

	}

}
