
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

import repositories.ReservationRepository;
import domain.Bill;
import domain.BillLine;
import domain.Client;
import domain.Global;
import domain.Hotel;
import domain.KindOfOffert;
import domain.Reservation;
import domain.Room;
import domain.Season;
import domain.State;
import domain.Terrain;

@Service
@Transactional
public class ReservationService {

	@Autowired
	private ReservationRepository	reservationRepository;
	@Autowired
	private GlobalService           globalService;
	@Autowired
	private BillService             billService;
	@Autowired
	private BillLineService         billLineService;
	@Autowired
	private RoomService         roomService;
	@Autowired
	private ClientService       clientService;


	public ReservationService() {
		super();
	}
	public Reservation create(Client me, Room room) {
		Reservation res;
		res = new Reservation();
		res.setClient(me);
		res.setNumDays(1);
		res.setRooms(room);
		return res;
	}


	public Reservation save(Reservation reservation) {
		Assert.notNull(reservation);
		Reservation res;
		res = this.reservationRepository.save(reservation);
		return res;
		
		
	}

	public Reservation saveAgore(Reservation reservation, Collection<Date> reservoirDates) {
		Assert.notNull(reservation);
		Reservation res,res2;
		Room room=reservation.getRooms();
		List<Date> dateRooms=new ArrayList<Date>(room.getOccupiedDays());
		List<Date> dateRoomsDephinitive=new ArrayList<Date>(room.getOccupiedDays());
		Client client=reservation.getClient();
		if(reservation.getId()!=0){
			Reservation theTrue=this.findOne(reservation.getId());
			Collection<Date> reservoirDates2=this.fechasOcupadas(theTrue.getCheckIn(),theTrue.getCheckOut());
			for(int i=dateRooms.size()-1;i>-1;i--){
				for(Date e:reservoirDates2){
					if(e.equals(dateRooms.get(i))){
						dateRoomsDephinitive.remove(i);
						break;
					}
				}
			}
		}
		if(reservation.getId()==0){
			room.getOccupiedDays().addAll(reservoirDates);
			room.getReservations().add(reservation);
			reservation.setRooms(room);
			res = this.reservationRepository.save(reservation);
			this.roomService.flush();
			client.getReservations().add(res);
			Client clientSaved=this.clientService.save(client);
			Room roomSaved=this.roomService.save(room);
			res.setClient(clientSaved);
			res.setRooms(roomSaved);
			res2= this.reservationRepository.save(res);
			this.reservationRepository.flush();
		}else{
			dateRoomsDephinitive.addAll(reservoirDates);
			room.setOccupiedDays(dateRoomsDephinitive);
			this.roomService.save(room);
			res2=this.reservationRepository.saveAndFlush(reservation);
		}
		
		return res2;
		
		
	}
	public Double calculateAuthomaticPrice(Reservation reservation) {
		Double originalPriceDay = reservation.getRooms().getOriginalPriceDays();
		if (reservation.getKindOfOffert() == KindOfOffert.ALL_INCLUDED) {
			originalPriceDay = originalPriceDay * 1.9;
		} else if (reservation.getKindOfOffert() == KindOfOffert.BED)
			originalPriceDay = originalPriceDay * 0.8;
		else if (reservation.getKindOfOffert() == KindOfOffert.BED_AND_BREAKFAST)
			originalPriceDay = originalPriceDay * 0.9;
		else if (reservation.getKindOfOffert() == KindOfOffert.FULL_PENSION)
			originalPriceDay = originalPriceDay * 1.3;
		else
			originalPriceDay = originalPriceDay * 1;
		Global global = globalService.findTheGlobal();
		Hotel hotel = reservation.getRooms().getHotel();
		if (hotel.getTerrain()==Terrain.BEACH && global.getSeason()==Season.HIGH) {
			originalPriceDay = originalPriceDay * 2.2;
		} else if (hotel.getTerrain()==Terrain.CULTURAL && global.getSeason()==Season.HIGH)
			originalPriceDay = originalPriceDay * 1.1;
		else if (hotel.getTerrain()==Terrain.MIXED && global.getSeason()==Season.HIGH)
			originalPriceDay = originalPriceDay * 1.6;
		else if (hotel.getTerrain()==Terrain.MOUNTAIN && global.getSeason()==Season.HIGH)
			originalPriceDay = originalPriceDay * 1.7;
		else if (hotel.getTerrain()==Terrain.BEACH && global.getSeason()==Season.HALF) 
			originalPriceDay = originalPriceDay * 0.7;
		else if (hotel.getTerrain()==Terrain.CULTURAL && global.getSeason()==Season.HALF)
			originalPriceDay = originalPriceDay * 1.6;
		else if (hotel.getTerrain()==Terrain.MIXED && global.getSeason()==Season.HALF)
			originalPriceDay = originalPriceDay * 1.1;
		else if (hotel.getTerrain()==Terrain.MOUNTAIN && global.getSeason()==Season.HALF)
			originalPriceDay = originalPriceDay * 1.6;
		else if (hotel.getTerrain()==Terrain.BEACH && global.getSeason()==Season.LOW) 
			originalPriceDay = originalPriceDay * 1.4;
		else if (hotel.getTerrain()==Terrain.CULTURAL && global.getSeason()==Season.LOW)
			originalPriceDay = originalPriceDay * 0.8;
		else if (hotel.getTerrain()==Terrain.MIXED && global.getSeason()==Season.LOW)
			originalPriceDay = originalPriceDay * 1;
		else if (hotel.getTerrain()==Terrain.MOUNTAIN && global.getSeason()==Season.LOW)
			originalPriceDay = originalPriceDay * 0.9;
		else
			originalPriceDay = originalPriceDay * 1;
		return originalPriceDay;
	}
	public boolean checkDispnibilidadHabitación(Reservation reservation,Collection<Date> dates) {
		boolean res=true;
		Collection<Date> datesOriginal = new HashSet<Date>();
		Room room=reservation.getRooms();
		if(reservation.getId()!=0){
		Reservation originalOne=findOne(reservation.getId());
		datesOriginal = fechasOcupadas(originalOne.getCheckIn(),originalOne.getCheckOut());
		dates.removeAll(datesOriginal);
		}
		for(Date e:dates){
			res=!room.getOccupiedDays().contains(e);
			if(res==false){
				break;
			}
		}
		return res;
		
	}
	public Collection<Date> calcularDates(Reservation reservation) {
		return fechasOcupadas(reservation.getCheckIn(),reservation.getCheckOut());
	}
	public Collection<Date> fechasOcupadas(Date fechaEntada,Date fechaSalida){
		  Set<Date> res=new HashSet<Date>();
		  res.add(fechaEntada);
		  
		  long diferenciaEn_ms = fechaSalida.getTime() - fechaEntada.getTime();
		  long dias = diferenciaEn_ms / (1000*60*60*24);
		  int hi= (int) dias;
		  for(int i=1;i<hi;i++){
		   Date fecha3=null;
		   Calendar calendario=Calendar.getInstance();
		   
		     calendario.setTime(fechaEntada);
		     calendario.add(Calendar.DATE, i);
		    fecha3=calendario.getTime();
		    res.add(fecha3);
		  }
		  
		  
		  return res;
		 }
	public Reservation makeTheBill(Reservation reservation) {
		if(reservation.getBill()==null){
		Bill bill=billService.create();
		bill.setReservation(reservation);
		Bill saved=billService.save(bill);
		BillLine facturilla=billLineService.create(saved);
		facturilla.setAmount(reservation.getPriceDay()*reservation.getNumDays());
		facturilla.setReason("Precio de la reserva " +reservation.getRooms().getNumber()+ " en el hotel " +reservation.getRooms().getHotel().getName());
		BillLine hehe= billLineService.save(facturilla);
		Bill biruso = hehe.getBill();
		biruso.setTotalAmount(hehe.getAmount());
		biruso.getBillLines().add(hehe);
		Bill xd = billService.save(biruso);
		reservation.setBill(xd);
		}else{
			Bill billaso=reservation.getBill();
			for(BillLine e:billaso.getBillLines()){
				e.setAmount(reservation.getPriceDay()*reservation.getNumDays());
				BillLine hehe= billLineService.save(e);
				billaso.setTotalAmount(hehe.getAmount());
				Bill xd = billService.save(billaso);
				reservation.setBill(xd);
				break;
			}
		}
		return reservation;
	}
	public Reservation findOne(int reservationId) {
		Reservation res;
		res=reservationRepository.findOne(reservationId);
		return res;
	}
	public boolean checkGoodHotel(Hotel hotel, Date checkIn,Date checkOut) {
		boolean res = true;
		res = hotel.getRequest().getState().equals(State.ACCEPTED);
		if (res == true) {
			res = checkIn.after(hotel.getRequest().getTimeIn());
			if (res == true)
				res = checkOut.before(hotel.getRequest().getTimeOut());
		}
		return res;
	}


	
}
