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
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.ClientService;
import domain.Bill;
import domain.Client;

@Controller
@RequestMapping("/bill")
public class BillController extends AbstractController {

	// Constructors -----------------------------------------------------------

	@Autowired
	private BillService	billService;
	@Autowired
	private ClientService clientService;


	public BillController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Bill> bills;
		Client client=this.clientService.findByPrincipal();
		bills = this.billService.myBillz(client.getId());

		Date now=new Date();
		result = new ModelAndView("bill/list");
		result.addObject("requestURI", "bill/list.do");
		result.addObject("bills", bills);
		result.addObject("now",now);
		return result;
	}
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam int billId) {
		ModelAndView result;
		Collection<Bill> bills;
		Bill bill=this.billService.findOne(billId);
		Client client=this.clientService.findByPrincipal();
		result = new ModelAndView("bill/list");
		try{
			if(bill.getReservation()==null)
				Assert.isTrue(bill.getOffert().getClient().getId()==client.getId());
			else
				Assert.isTrue(bill.getReservation().getClient().getId()==client.getId());
			bill.setPaid(true);
			billService.save(bill);
			result.addObject("message","bill.commit.ok");
		} catch (Exception oops){
			result.addObject("message","bill.commit.error");
		}
		Date now=new Date();
		bills = this.billService.myBillz(client.getId());
		result.addObject("requestURI", "bill/list.do");
		result.addObject("bills", bills);
		result.addObject("now",now);
		return result;
	}

}
