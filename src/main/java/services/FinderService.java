
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Client;
import domain.Finder;
import domain.KindOfOffert;
import domain.KindOfRoom;
import domain.Offert;
import domain.Room;
import domain.Terrain;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;


	public FinderService() {
		super();
	}
	public Finder create(final Client client) {
		Finder res;
		Assert.notNull(client);
		res = new Finder();
		res.setClient(client);
		return res;
	}
	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Finder res;
		res = this.finderRepository.save(finder);
		return res;
	}

	public Collection<Room> searchFinderRoom(final Finder finder) {
		Collection<Room> res = new HashSet<Room>();
		final Date checkIn;
		Date checkOut;
		final KindOfRoom kindOfRoom;
		final Terrain terrain;
		final String hotelName;
		final Double minimumPrice, maximumPrice;
		final String province;
		final String population;
		final Integer minCapacity;
		final Integer maxCapacity;

		if (finder.getPopulation() == null || finder.getPopulation() == "")
			population = "%%";
		else
			population = "%" + finder.getPopulation() + "%";

		if (finder.getProvince() == null || finder.getProvince() == "")
			province = "%%";
		else
			province = "%" + finder.getProvince() + "%";

		if (finder.getCapacity() == null) {
			minCapacity = 0;
			maxCapacity = Integer.MAX_VALUE;
		} else {
			minCapacity = finder.getCapacity();
			maxCapacity = finder.getCapacity();
		}

		final Date hoy2 = new Date();
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(hoy2);
		calendario.add(Calendar.DATE, 1);
		final Date hoy = calendario.getTime();
		if (finder.getCheckIn() == null || finder.getCheckIn().before(hoy))
			checkIn = hoy;
		else
			checkIn = finder.getCheckIn();

		if (finder.getCheckOut() == null) {
			checkOut = checkIn;
			if (finder.getCheckIn() != null) {
				final Date out = checkOut;
				final Calendar calendario2 = Calendar.getInstance();
				calendario2.setTime(out);
				calendario2.add(Calendar.DATE, 1);
				checkOut = calendario.getTime();
			}
		} else
			checkOut = finder.getCheckOut();

		if (finder.getHotelName() == null || finder.getHotelName() == "")
			hotelName = "%%";
		else
			hotelName = "%" + finder.getHotelName() + "%";
		if (finder.getMinimumPrice() == null)
			minimumPrice = 0.0;
		else
			minimumPrice = finder.getMinimumPrice();
		if (finder.getMaximumPrice() == null)
			maximumPrice = Double.MAX_VALUE;
		else
			maximumPrice = finder.getMaximumPrice();

		//ENUMERADOS
		kindOfRoom = finder.getKindOfRoom();
		terrain = finder.getTerrain();

		if (kindOfRoom == null) {
			if (terrain == null)
				res = this.finderRepository.searchRoomSinKyT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, province, population, minCapacity, maxCapacity);
			else
				res = this.finderRepository.searchRoomSinK(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, terrain, province, population, minCapacity, maxCapacity);
		} else if (terrain == null)
			res = this.finderRepository.searchRoomSinT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, province, population, minCapacity, maxCapacity);
		else
			res = this.finderRepository.searchRoomTodo(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, terrain, province, population, minCapacity, maxCapacity);
		return res;
	}
	@SuppressWarnings("deprecation")
	public Collection<Offert> searchFinderOffert(final Finder finder) {
		Collection<Offert> res = new HashSet<Offert>();

		final Date checkIn;
		Date checkOut;
		final KindOfRoom kindOfRoom;
		final Terrain terrain;
		final KindOfOffert kindOfOffert;
		final String hotelName;
		final Double minimumPrice, maximumPrice;
		final String province;
		final String population;
		final Integer minCapacity;
		final Integer maxCapacity;

		if (finder.getPopulation() == null || finder.getPopulation() == "")
			population = "%%";
		else
			population = "%" + finder.getPopulation() + "%";

		if (finder.getProvince() == null || finder.getProvince() == "")
			province = "%%";
		else
			province = "%" + finder.getProvince() + "%";

		if (finder.getCapacity() == null) {
			minCapacity = 0;
			maxCapacity = Integer.MAX_VALUE;
		} else {
			minCapacity = finder.getCapacity();
			maxCapacity = finder.getCapacity();
		}
		final Date hoy2 = new Date();
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(hoy2);
		calendario.add(Calendar.DATE, 1);
		final Date hoy = calendario.getTime();
		if (finder.getCheckIn() == null || finder.getCheckIn().before(hoy))
			checkIn = hoy;
		else
			checkIn = finder.getCheckIn();

		if (finder.getCheckOut() == null) {
			checkOut = new Date(checkIn.getYear()+2,0,1);
			
		} else
			checkOut = finder.getCheckOut();

		if (finder.getHotelName() == null || finder.getHotelName() == "")
			hotelName = "%%";
		else
			hotelName = "%" + finder.getHotelName() + "%";
		if (finder.getMinimumPrice() == null)
			minimumPrice = 0.0;
		else
			minimumPrice = finder.getMinimumPrice();
		if (finder.getMaximumPrice() == null)
			maximumPrice = Double.MAX_VALUE;
		else
			maximumPrice = finder.getMaximumPrice();

		//ENUMERADOS
		kindOfRoom = finder.getKindOfRoom();
		terrain = finder.getTerrain();
		kindOfOffert = finder.getKindOfOffert();

		if (kindOfOffert == null) {
			if (kindOfRoom == null) {
				if (terrain == null)
					res = this.finderRepository.searchOffertSinKOyKRyT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, province, population, minCapacity, maxCapacity);
				else
					res = this.finderRepository.searchOffertSinKOyKR(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, terrain, province, population, minCapacity, maxCapacity);
			} else if (terrain == null)
				res = this.finderRepository.searchOffertSinKOyT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, province, population, minCapacity, maxCapacity);
			else
				res = this.finderRepository.searchOffertSinKO(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, terrain, province, population, minCapacity, maxCapacity);
		} else if (kindOfRoom == null) {
			if (terrain == null)
				res = this.finderRepository.searchOffertSinKRyT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfOffert, province, population, minCapacity, maxCapacity);
			else
				res = this.finderRepository.searchOffertSinKR(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, terrain, kindOfOffert, province, population, minCapacity, maxCapacity);
		} else if (terrain == null)
			res = this.finderRepository.searchOffertSinT(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, kindOfOffert, province, population, minCapacity, maxCapacity);
		else
			res = this.finderRepository.searchOffertTodo(hotelName, minimumPrice, maximumPrice, checkIn, checkOut, kindOfRoom, terrain, kindOfOffert, province, population, minCapacity, maxCapacity);

		return res;
	}
	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}

	public Collection<Finder> allFinder() {
		Collection<Finder> res;
		res = this.finderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Finder findOne(final int id) {
		Finder res;
		res = this.finderRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
}
