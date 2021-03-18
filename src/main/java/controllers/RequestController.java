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
import services.ClientService;
import services.RequestService;
import services.RoomService;
import services.WorkerService;
import domain.Actor;
import domain.Bill;
import domain.BillLine;
import domain.Request;
import domain.State;
import domain.Worker;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	// Constructors -----------------------------------------------------------

	@Autowired
	private RequestService	requestService;
	@Autowired
	private AdministratorService	administratorService;


	public RequestController() {
		super();
	}
	
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Request> request;
		request=this.administratorService.requests();
		result = new ModelAndView("request/list");
		result.addObject("requestURI", "request/list.do");
		result.addObject("request", request);
		return result;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam int requestId) {
		ModelAndView result;
	    Request request = requestService.findOne(requestId);
	    if(request.getState()==State.PENDING){
	    	request.setState(State.ACCEPTED);
	    	requestService.save(request);
	    }
		result=list();
		return result;
	}
	
	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
	    Request request = requestService.findOne(requestId);
	    if(request.getState()==State.PENDING){
	    	request.setState(State.DENIED);
	    	requestService.save(request);
	    }
		result=list();
		return result;
	}
}
