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
import services.BillLineService;
import services.BillService;
import services.ClientService;
import services.RoomService;
import services.WorkerService;
import domain.Actor;
import domain.Bill;
import domain.BillLine;
import domain.Hotel;
import domain.Offert;
import domain.Reservation;
import domain.Room;
import domain.Worker;

@Controller
@RequestMapping("/billLine")
public class BillLineController extends AbstractController {

	// Constructors -----------------------------------------------------------

	@Autowired
	private BillLineService	billLineService;
	@Autowired
	private BillService	billService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private RoomService roomService;


	public BillLineController() {
		super();
	}
	
	@RequestMapping(value = "/worker/list", method = RequestMethod.GET)
	 public ModelAndView list() {
	  ModelAndView result;
	  Boolean principal = false;
	  final Worker worker = this.workerService.findByPrincipal();
	     
	  final Hotel hotel = worker.getHotel();
	  
	  Collection<Room> rooms;
	  rooms = this.roomService.myRoomazos(hotel.getId());

	  
	  result = new ModelAndView("room/list");
	  result.addObject("requestURI", "room/worker/list.do");
	  result.addObject("rooms", rooms);
	  result.addObject("principal", principal);
	  return result;
	 }
	
	@RequestMapping(value = "/listWorker", method = RequestMethod.GET)
	public ModelAndView list5(@RequestParam int roomId) {
		ModelAndView result;

		Room room = roomService.findOne(roomId);
		Collection<BillLine> billLine;
		int billId=0;
		try{
			Reservation reservations=roomService.myReservations(roomId);
			Offert offert=roomService.myOfferts(roomId);
			if(reservations!=null)
				billId=reservations.getBill().getId();
			else if(offert!=null)
				billId=offert.getBill().getId();
			else
				Assert.isTrue(false);
			} catch (Exception oops){
				result = new ModelAndView("redirect:../room/myhotel/worker/list.do");
				result.addObject("message","room.commit.noone");
				return result;
			}
        result=list(billId);
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int billId) {
		ModelAndView result;

		Collection<BillLine> billLine;
		Actor actor=this.actorService.findByPrincipal();
		Bill bill = this.billService.findOne(billId);
		try{
			if(actor instanceof Worker){
				Worker worker=this.workerService.findByPrincipal();
				if(bill.getReservation()==null)
					Assert.isTrue(bill.getOffert().getRooms().getHotel().getId()==worker.getHotel().getId());
				else
					Assert.isTrue(bill.getReservation().getRooms().getHotel().getId()==worker.getHotel().getId());
			}else{
			if(bill.getReservation()==null)
				Assert.isTrue(bill.getOffert().getClient().getId()==actor.getId());
			else
				Assert.isTrue(bill.getReservation().getClient().getId()==actor.getId());
			}
			} catch (Exception oops){
				result = new ModelAndView("redirect:/");
				result.addObject("message","bill.commit.error");
				return result;
			}
        billLine=bill.getBillLines();
		result = new ModelAndView("billLine/list");
		result.addObject("requestURI", "billLine/list.do?billId="+billId);
		result.addObject("billLines", billLine);
		result.addObject("billId",billId);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView list2(@RequestParam int billId) {
		ModelAndView result;
		Bill bill = this.billService.findOne(billId);
		BillLine billLine = billLineService.create(bill);
		result = this.createEditModelAndView(billLine);
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int billLineId) {
		ModelAndView result;
		final BillLine billLine = this.billLineService.findOne(billLineId);
		Actor actor=actorService.findByPrincipal();
		Bill bill=this.billService.findOne(billLine.getBill().getId());
		result = new ModelAndView("billLine/list");
			
			
			
		try {
			if(actor instanceof Worker){
				Worker worker=this.workerService.findByPrincipal();
				if(bill.getReservation()==null)
					Assert.isTrue(bill.getOffert().getRooms().getHotel().getId()==worker.getHotel().getId());
				else
					Assert.isTrue(bill.getReservation().getRooms().getHotel().getId()==worker.getHotel().getId());
			this.billLineService.delete(billLine);
			result = new ModelAndView("redirect:list.do?billId="+bill.getId());
			result.addObject("message", "bill.commit.ok");}
			else{
				Assert.isTrue(false);
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?billId="+bill.getId());
			result.addObject("message", "bill.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BillLine billLine, final BindingResult binding) {
		ModelAndView result;
		Actor actor=this.actorService.findByPrincipal();
		Bill bill = this.billService.findOne(billLine.getBill().getId());
		try {
			if(actor instanceof Worker){
				Worker worker=this.workerService.findByPrincipal();
				if(bill.getReservation()==null)
					Assert.isTrue(bill.getOffert().getRooms().getHotel().getId()==worker.getHotel().getId());
				else
					Assert.isTrue(bill.getReservation().getRooms().getHotel().getId()==worker.getHotel().getId());
			}else{
				Assert.isTrue(false);
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(billLine, "bill.commit.notAuthorized");
			return result;
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(billLine);
		else
			try {
				this.billLineService.actualizeAmount(billLine);
				result = new ModelAndView("redirect:list.do?billId="+bill.getId());

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(billLine, "bill.commit.error");
			}

		return result;

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create2(@RequestParam int billLineId) {
		ModelAndView result;
		BillLine hey = billLineService.findOne(billLineId);
		result = this.createEditModelAndView(hey);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(final BillLine billLine) {
		ModelAndView result;

		result = this.createEditModelAndView(billLine, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BillLine billLine, final String message) {
		ModelAndView result;

		result = new ModelAndView("billLine/create");
		result.addObject("requestURI", "billLine/create.do");
		result.addObject("billLine", billLine);
		result.addObject("message", message);
		return result;
	}

}
