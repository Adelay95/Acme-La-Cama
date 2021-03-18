
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RoomRepository;
import domain.Hotel;
import domain.KindOfRoom;
import domain.Offert;
import domain.Reservation;
import domain.Room;
import domain.Services;
import forms.FormRoom;

@Service
@Transactional
public class RoomService {

	@Autowired
	private RoomRepository	roomRepository;
	@Autowired
	private ServicesService	servicesService;
	@Autowired
	private HotelService	hotelService;
	@Autowired
	private Validator		validator;


	public RoomService() {
		super();
	}

	public Room create(final Hotel hotel) {
		Room res;
		final Collection<Services> services = new HashSet<Services>();
		final Collection<Reservation> reservations = new HashSet<Reservation>();
		final Collection<Date> dates = new HashSet<Date>();
		res = new Room();
		res.setReservations(reservations);
		res.setOccupiedDays(dates);
		res.setOffert(null);
		res.setServices(services);
		res.setHotel(hotel);
		return res;
	}

	public Room save(final Room room) {
		Assert.notNull(room);
		Room res;
		res = this.roomRepository.save(room);
		return res;

	}

	public Collection<Room> allRooms() {
		Collection<Room> res;
		res = this.roomRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Room reconstruct(final FormRoom formRoom, final BindingResult binding) {
		Room result;
		result = this.create(formRoom.getHotel());
		result = this.fullyReconstruct(result, formRoom);
		this.validator.validate(result, binding);
		return result;
	}
	private Room fullyReconstruct(final Room result, final FormRoom formRoom) {
		result.setCapacity(formRoom.getCapacity());
		result.setDescription(formRoom.getDescription());
		result.setKids(formRoom.getKids());
		result.setKindOfRoom(formRoom.getKindOfRoom());
		result.setNumber(formRoom.getNumber());

		Double originalPriceDay = formRoom.getHotel().getRoomPrice();
		if (formRoom.getCheckAutomaticPrice())
			originalPriceDay = formRoom.getOriginalPriceDays();
		else
			originalPriceDay = formRoom.getPersonalPriceDays();
		if (formRoom.getKindOfRoom() == KindOfRoom.SINGLE)
			result.setCapacity(1);
		else if (formRoom.getKindOfRoom() == KindOfRoom.DOUBLE)
			result.setCapacity(2);
		else if (formRoom.getKindOfRoom() == KindOfRoom.TRIPLE)
			result.setCapacity(3);
		else if (formRoom.getKindOfRoom() == KindOfRoom.TEMPORARY_BED)
			result.setCapacity(1);
		else if (formRoom.getKindOfRoom() == KindOfRoom.SUITE)
			result.setCapacity(3);
		else
			result.setCapacity(2);
		result.setOriginalPriceDays(originalPriceDay);
		result.setPicture(formRoom.getPicture());
		return result;
	}

	public boolean checkCapacityHotel(final Hotel hotel, final Integer newRooms) {
		boolean res = false;
		final int roomsActually = hotel.getRooms().size();
		res = hotel.getTotalRooms() > roomsActually + newRooms;
		return res;
	}

	public void saveMultipleRooms(final Collection<Services> services, final FormRoom formRoom, final Room room) {
		Integer number = room.getNumber();
		final Collection<Room> savedRooms = new HashSet<Room>();
		final List<Services> servicesList = new LinkedList<Services>(services);
		for (int i = 0; i < formRoom.getNumberOfRooms(); i++) {
			Room theSaved = room;
			theSaved.setNumber(number);
			theSaved = this.save(theSaved);
			savedRooms.add(theSaved);
			number++;
		}
		for (int i = 0; i < services.size(); i++) {
			final Services theSavedService = servicesList.get(i);
			theSavedService.getRooms().addAll(savedRooms);
			this.servicesService.saveNormal(theSavedService);
		}
		final Hotel hoteru = room.getHotel();
		hoteru.getRooms().addAll(savedRooms);
		this.hotelService.save(hoteru);
	}

	public Double calculateAuthomaticPrice(final FormRoom formRoom) {
		Double originalPriceDay = formRoom.getHotel().getRoomPrice();
		if (formRoom.getKindOfRoom() == KindOfRoom.SINGLE) {
		} else if (formRoom.getKindOfRoom() == KindOfRoom.DOUBLE)
			originalPriceDay = originalPriceDay * 1.5;
		else if (formRoom.getKindOfRoom() == KindOfRoom.TRIPLE)
			originalPriceDay = originalPriceDay * 1.8;
		else if (formRoom.getKindOfRoom() == KindOfRoom.TEMPORARY_BED)
			originalPriceDay = originalPriceDay * 0.8;
		else if (formRoom.getKindOfRoom() == KindOfRoom.SUITE)
			originalPriceDay = originalPriceDay * 5.0;
		else
			originalPriceDay = originalPriceDay * 3.5;
		return originalPriceDay;
	}

	public Integer getMaxNumberHotel(final int id) {
		Integer number = 1;

		number = this.roomRepository.getMaxNumberHotel(id);
		if (number == null)
			number = 1;
		return number;
	}

	public Collection<Room> allRoomHotel(final int hotelId) {
		return this.roomRepository.getAllRoomHotel(hotelId);
	}

	public Room findOne(final int id) {
		Room res;
		res = this.roomRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public void flush() {
		this.roomRepository.flush();

	}
	public Collection<Room> searchRoomWorker(final int hotelId, final int search) {
		return this.roomRepository.getSearchRoomWorker(hotelId, search);
	}

	public Reservation myReservations(final int roomId) {
		return this.roomRepository.myReservations(roomId);
	}
	public Offert myOfferts(final int roomId) {
		return this.roomRepository.myOffert(roomId);
	}
	public Collection<Room> myRoomazos(final int hotelId) {
		final Collection<Room> roomz = this.roomRepository.myReservationZ(hotelId);
		roomz.addAll(this.roomRepository.myOffertZ(hotelId));

		return roomz;
	}
}
