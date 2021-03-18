/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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
import services.AdministratorService;
import services.BannerService;
import domain.Actor;
import domain.Administrator;
import domain.Banner;

@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	@Autowired
	private BannerService			bannerService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ActorService			actorService;


	public BannerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Administrator actor = this.administratorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		final Banner banner = this.bannerService.create();
		result = this.createEditModelAndView(banner);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Banner> banners;
		banners = this.bannerService.allBanners();
		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("requestURI", "banner/list.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int bannerId) {
		ModelAndView result;

		final Actor mana = this.actorService.findByPrincipal();
		Assert.isTrue(mana instanceof Administrator);
		try {
			Banner banner;
			banner = this.bannerService.findOne(bannerId);
			Assert.notNull(banner);
			this.bannerService.delete(banner);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?message=actor.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bannerId) {
		ModelAndView result;
		Banner banner;
		final Actor mana = this.actorService.findByPrincipal();
		Assert.isTrue(mana instanceof Administrator);
		banner = this.bannerService.findOne(bannerId);
		Assert.notNull(banner);
		result = this.createEditModelAndView(banner);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Banner banner, final BindingResult binding) {
		ModelAndView result;

		if (!(this.actorService.findByPrincipal() instanceof Administrator))
			result = this.createEditModelAndView(banner, "banner.commit.error");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(banner);
		else
			try {
				this.bannerService.save(banner);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(banner, "banner.commit.error");
			}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Banner banner) {
		ModelAndView result;

		result = this.createEditModelAndView(banner, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Banner banner, final String message) {
		ModelAndView result;
		result = new ModelAndView("banner/edit");
		result.addObject("banner", banner);
		result.addObject("requestURI", "banner/edit.do");
		result.addObject("message", message);
		return result;

	}

}
