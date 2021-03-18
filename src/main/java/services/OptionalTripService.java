
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OptionalTripRepository;
import domain.Bill;
import domain.BillLine;
import domain.Client;
import domain.Hotel;
import domain.Offert;
import domain.OptionalTrip;
import domain.Reservation;
import forms.FormTrip;

@Service
@Transactional
public class OptionalTripService {

	@Autowired
	private OptionalTripRepository	optionalTripRepository;
	@Autowired
	private ClientService			clientService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BillLineService			billLineService;
	@Autowired
	private ReservationService		reservationService;
	@Autowired
	private OffertService			offertService;
	@Autowired
	private BillService				billService;
	@Autowired
	private Validator				validator;


	public OptionalTripService() {
		super();
	}

	public OptionalTrip save(final OptionalTrip op) {
		Assert.notNull(op);
		OptionalTrip res;
		res = this.optionalTripRepository.save(op);
		return res;

	}
	public OptionalTrip create(final Hotel hotel) {
		OptionalTrip res;
		final Collection<Client> services = new HashSet<Client>();
		final Collection<Date> dates = new HashSet<Date>();
		res = new OptionalTrip();
		res.setClients(services);
		res.setTripDays(dates);
		res.setHotel(hotel);
		return res;
	}

	public Collection<OptionalTrip> allOptionalTrips() {
		Collection<OptionalTrip> res;
		res = this.optionalTripRepository.findAll();
		Assert.notNull(res);
		return res;
	}
	public OptionalTrip findOne(final int id) {
		OptionalTrip res;
		res = this.optionalTripRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public void delete(final int id) {
		this.optionalTripRepository.delete(id);
	}
	public Collection<OptionalTrip> excursionesDisponiblesReserva(final int idReserva) {

		Reservation rese;
		rese = this.reservationService.findOne(idReserva);
		Collection<Date> hi;

		hi = this.fechasOcupadas(rese.getCheckIn(), rese.getCheckOut());
		return this.checkDispnibilidadHabitación(rese, hi);

	}
	public Collection<OptionalTrip> excursionesDisponiblesOfertas(final int idOfertA) {
		final Collection<OptionalTrip> res = new HashSet<OptionalTrip>();
		;
		Offert rese;
		rese = this.offertService.findOne(idOfertA);
		Collection<Date> hi;
		final Collection<OptionalTrip> hotel = rese.getRooms().getHotel().getOptionalTrips();
		hi = this.fechasOcupadas(rese.getCheckIn(), rese.getCheckOut());
		for (final OptionalTrip ot : hotel) {
			final Collection<Date> puff = ot.getTripDays();
			for (final Date date : puff)
				if (hi.contains(date)) {
					res.add(ot);
					break;
				}
		}

		return res;
	}
	public void asistir(final int otId, final int actorId) {
		OptionalTrip res;
		Client client;
		res = this.findOne(otId);
		client = this.clientService.findOne(actorId);
		final Hotel hotel = res.getHotel();
		final Bill bill;
		final List<Bill> ofer = new ArrayList<Bill>(this.optionalTripRepository.billDelClient2(actorId, hotel.getId()));
		final List<Bill> reserva = new ArrayList<Bill>(this.optionalTripRepository.billDelClient(actorId, hotel.getId()));
		if (ofer.isEmpty())
			bill = reserva.get(0);
		else
			bill = ofer.get(0);
		final BillLine line = this.billLineService.create(bill);
		line.setAmount(res.getPrice());
		line.setReason(res.getTitle());
		final BillLine hehe = this.billLineService.save(line);
		final Bill biruso = hehe.getBill();
		final Double totalAmount = biruso.getTotalAmount();
		biruso.setTotalAmount(totalAmount + res.getPrice());
		biruso.getBillLines().add(hehe);
		this.billService.save(biruso);
		res.getClients().add(client);
		this.save(res);
	}

	private Collection<Date> fechasOcupadas(final Date fechaEntada, final Date fechaSalida) {
		final Set<Date> res = new HashSet<Date>();
		res.add(fechaEntada);
		res.add(fechaSalida);

		final long diferenciaEn_ms = fechaSalida.getTime() - fechaEntada.getTime();
		final long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
		final int hi = (int) dias;
		for (int i = 1; i < hi; i++) {
			Date fecha3 = null;
			final Calendar calendario = Calendar.getInstance();

			calendario.setTime(fechaEntada);
			calendario.add(Calendar.DATE, i);
			fecha3 = calendario.getTime();
			res.add(fecha3);
		}

		return res;
	}
	private Collection<OptionalTrip> checkDispnibilidadHabitación(final Reservation offert, final Collection<Date> dates) {

		final Collection<OptionalTrip> res = new HashSet<OptionalTrip>();
		final Collection<OptionalTrip> ots = offert.getRooms().getHotel().getOptionalTrips();
		for (final OptionalTrip p : ots) {
			final Collection<Date> diasS = p.getTripDays();
			for (final Date e : diasS)
				if (dates.contains(e)) {
					res.add(p);
					break;
				}
		}
		return res;

	}
	public Collection<Client> sixtoCarry(final int idTrip, final int idHotel) {
		return this.optionalTripRepository.sixtoCarry(idTrip, idHotel);
	}

	public OptionalTrip reconstruct(final FormTrip formTrip, final BindingResult binding) {
		OptionalTrip result;

		if (formTrip.getId() == 0) {

			result = this.create(formTrip.getHotel());
			result = this.fullyReconstruct(result, formTrip);
		} else {
			result = this.optionalTripRepository.findOne(formTrip.getId());
			result = this.fullyReconstruct(result, formTrip);
		}
		this.validator.validate(result, binding);
		return result;

	}
	private OptionalTrip fullyReconstruct(final OptionalTrip result, final FormTrip formTrip) {

		result.setLocation(formTrip.getLocation());
		result.setClosingTime(formTrip.getClosingTime());
		result.setOpeningTime(formTrip.getOpeningTime());
		result.setPicture(formTrip.getPicture());
		result.setPrice(formTrip.getPrice());
		result.setTitle(formTrip.getTitle());
		result.setTripDays(this.fechasOcupadas(formTrip.getCheckIn(), formTrip.getCheckOut()));
		return result;
	}

	public Collection<OptionalTrip> excursionesMia(final int id) {
		return this.optionalTripRepository.holaCasol(id);
	}

	public FormTrip createMyForm(final OptionalTrip formTrip) {
		final FormTrip result = new FormTrip();
		if (formTrip.getId() == 0)
			result.setHotel(formTrip.getHotel());
		else {
			result.setHotel(formTrip.getHotel());
			result.setLocation(formTrip.getLocation());
			result.setClosingTime(formTrip.getClosingTime());
			result.setOpeningTime(formTrip.getOpeningTime());
			result.setPicture(formTrip.getPicture());
			result.setPrice(formTrip.getPrice());
			result.setTitle(formTrip.getTitle());
			result.setId(formTrip.getId());
			result.setVersion(formTrip.getVersion());
		}
		return result;
	}

}
