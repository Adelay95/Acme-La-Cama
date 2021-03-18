
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HotelRepository;
import domain.Actor;
import domain.Client;
import domain.Comment;
import domain.Global;
import domain.Hotel;
import domain.Manager;
import domain.Message;
import domain.Offert;
import domain.OptionalTrip;
import domain.Request;
import domain.Reservation;
import domain.Room;
import domain.State;
import domain.Worker;
import forms.FormHotel;

@Service
@Transactional
public class HotelService {

	@Autowired
	private Validator		validator;
	@Autowired
	private HotelRepository	hotelRepository;
	@Autowired
	private RequestService	requestService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ServicesService	servicesService;
	@Autowired
	private MessageService	messageService;
	@Autowired
	private GlobalService	globalService;


	public HotelService() {
		super();
	}

	public Hotel create(final Request request) {
		Hotel res;
		res = new Hotel();
		final Collection<Comment> comments = new HashSet<Comment>();
		final Collection<OptionalTrip> optionalTrips = new HashSet<OptionalTrip>();
		final Collection<Room> rooms = new HashSet<Room>();
		final Collection<Worker> workers = new HashSet<Worker>();
		res.setComments(comments);
		res.setOptionalTrips(optionalTrips);
		res.setRooms(rooms);
		res.setWorkers(workers);
		res.setRequest(request);
		return res;
	}
	public Hotel save(final Hotel hotel) {
		Assert.notNull(hotel);
		Hotel res;
		res = this.hotelRepository.save(hotel);
		return res;
	}

	public void delete(final Hotel hotel) {
		Assert.notNull(hotel);
		Assert.isTrue(hotel.getId() != 0);
		final Request request = hotel.getRequest();
		request.setHotel(null);
		this.requestService.save(request);
		this.hotelRepository.delete(hotel);
		this.requestService.delete(request);
	}

	public Collection<Hotel> allHotel() {
		Collection<Hotel> res;
		res = this.hotelRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Hotel findOne(final int id) {
		Hotel res;
		res = this.hotelRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Integer idManagerHotel(final int hotelId) {
		return this.hotelRepository.getIdManagerHotel(hotelId);
	}

	public Collection<Hotel> allHotelAccepted() {
		return this.hotelRepository.getAllHotelAccepted();
	}

	public Collection<Hotel> allHotelManager(final int managerId) {
		return this.hotelRepository.getAllHotelManager(managerId);
	}

	public FormHotel createMyForm(final Hotel hotel, final Request request) {
		final FormHotel res = new FormHotel();
		res.setDescription(hotel.getDescription());
		res.setHotelChain(hotel.getHotelChain());
		res.setId(hotel.getId());
		res.setLocation(hotel.getLocation());
		res.setName(hotel.getName());
		res.setPicture(hotel.getPicture());
		//res.setPrice(request.getPrice());
		res.setRoomPrice(hotel.getRoomPrice());
		res.setStars(hotel.getStars());
		res.setTerrain(hotel.getTerrain());
		res.setTimeIn(request.getTimeIn());
		res.setTimeOut(request.getTimeOut());
		res.setTotalRooms(hotel.getTotalRooms());
		res.setVersion(hotel.getVersion());
		return res;
	}

	public Double calculaPrecio(final Date una, final Date dos) {
		Assert.isTrue(una.before(dos));
		final Global g = this.globalService.findTheGlobal();
		final long diferenciaEn_ms = dos.getTime() - una.getTime();
		final long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
		final int hi = (int) dias;
		return g.getRequestPriceDay() * hi;
	}

	public Hotel reconstruct(final FormHotel formHotel, final BindingResult binding) {
		  Hotel res;
		  Request request;
		  final Manager manager = this.managerService.findByPrincipal();

		  if (formHotel.getId() == 0) {
		   request = this.requestService.create(manager);
		   request.setPrice(this.calculaPrecio(formHotel.getTimeIn(), formHotel.getTimeOut()));
		   request.setTimeIn(formHotel.getTimeIn());
		   request.setTimeOut(formHotel.getTimeOut());
		   res = this.create(request);
		  } else {
		   res = this.findOne(formHotel.getId());
		   final Date hoy = new Date();
		   final Date checkOut = res.getRequest().getTimeOut();
		   Assert.isTrue((hoy.after(checkOut)) || (res.getRequest().getState().equals(State.ACCEPTED) == false));
		   request = res.getRequest();
		   request.setPrice(this.calculaPrecio(formHotel.getTimeIn(), formHotel.getTimeOut()));
		   request.setTimeIn(formHotel.getTimeIn());
		   request.setTimeOut(formHotel.getTimeOut());
		   res.setRequest(request);
		  }
		  Assert.isTrue(request.getManager().equals(manager));
		  final Date hoy = new Date();
		  final Date checkIn = formHotel.getTimeIn();
		  final Date checkOut = formHotel.getTimeOut();
		  Assert.isTrue(checkIn.before(checkOut));
		  Assert.isTrue(checkIn.after(hoy));
		  Assert.isTrue(checkOut.after(hoy));

		  res.setDescription(formHotel.getDescription());
		  res.setHotelChain(formHotel.getHotelChain());
		  res.setLocation(formHotel.getLocation());
		  res.setName(formHotel.getName());
		  res.setPicture(formHotel.getPicture());
		  res.setRoomPrice(formHotel.getRoomPrice());
		  res.setStars(formHotel.getStars());
		  res.setTerrain(formHotel.getTerrain());
		  res.setTotalRooms(formHotel.getTotalRooms());
		  res.setVersion(formHotel.getVersion());
		  this.validator.validate(res, binding);
		  this.validator.validate(request, binding);
		  return res;
		 }

	public Collection<Reservation> getAllReservations(final int hotelId) {
		return this.hotelRepository.getAllReservations(hotelId);
	}

	public Collection<Offert> getAllOfferts(final int hotelId) {
		return this.hotelRepository.getAllOfferts(hotelId);
	}

	public Collection<Offert> getAllAvailableOfferts(final int hotelId) {
		return this.hotelRepository.getAllAvailableOfferts(hotelId);
	}
	public void reportHotel(final int hotelId) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Worker || actor instanceof Client);
		final Actor admin = this.servicesService.getAdmin();
		final Hotel hotel = this.findOne(hotelId);
		final Message mes;
		mes = this.messageService.create(actor, admin);
		mes.setSubject("Reported");
		mes.setBody("The manager " + actor.getSurname() + ", " + actor.getName() + " has reported the hotel: " + hotel.getName());
		this.actorService.sendMessage2(mes);

	}

}
