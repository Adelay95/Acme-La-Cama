/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/politic")
public class PoliticController extends AbstractController {

	// Panic handler ----------------------------------------------------------

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("politic/aboutUs");
		result.addObject("requestURI", "politic/aboutUs.do");

		return result;
	}

	@RequestMapping(value = "/termsAndConditions", method = RequestMethod.GET)
	public ModelAndView listTermsAndConditions() {
		ModelAndView result;
		result = new ModelAndView("politic/termsAndConditions");
		result.addObject("requestURI", "politic/termsAndConditions.do");

		return result;
	}

	@RequestMapping(value = "/cookies", method = RequestMethod.GET)
	public ModelAndView listCookies() {
		ModelAndView result;
		result = new ModelAndView("politic/cookies");
		result.addObject("requestURI", "politic/cookies.do");

		return result;
	}

	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView listPrivacyPolicy() {
		ModelAndView result;
		result = new ModelAndView("politic/privacyPolicy");
		result.addObject("requestURI", "politic/privacyPolicy.do");

		return result;
	}

}
