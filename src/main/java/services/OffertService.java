
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

import repositories.OffertRepository;
import domain.Bill;
import domain.BillLine;
import domain.Client;
import domain.Hotel;
import domain.Manager;
import domain.Offert;
import domain.Reservation;
import domain.Room;
import domain.State;

@Service
@Transactional
public class OffertService {

	@Autowired
	private OffertRepository	offertRepository;
	@Autowired
	private RoomService	roomService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private BillService billService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private BillLineService billLineService;


	public OffertService() {
		super();
	}


	public Offert save(Offert offert) {
		Assert.notNull(offert);
		Offert res;
		res = this.offertRepository.save(offert);
		return res;
		
		
	}


	public Offert create(Room room) {
		Offert res;
		res = new Offert();
		res.setClient(null);
		res.setRooms(room);
		return res;
	}
	
	public Collection<Date> calcularDates(Offert offert) {
		return fechasOcupadas(offert.getCheckIn(),offert.getCheckOut());
	}
	private Collection<Date> fechasOcupadas(Date fechaEntada,Date fechaSalida){
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
	
	public boolean checkDispnibilidadHabitación(Offert offert,Collection<Date> dates) {
		boolean res=true;
		Collection<Date> datesOriginal = new HashSet<Date>();
		Room room=offert.getRooms();
		if(offert.getId()!=0){
		Offert originalOne=findOne(offert.getId());
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
	
	public Offert saveAgore(Offert offert, Collection<Date> reservoirDates) {
		Assert.notNull(offert);
		Offert res2;
		Room room=offert.getRooms();
		List<Date> dateRooms=new ArrayList<Date>(room.getOccupiedDays());
		List<Date> dateRoomsDephinitive=new ArrayList<Date>(room.getOccupiedDays());
		if(offert.getId()!=0){
			Offert theTrue=this.findOne(offert.getId());
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
		if(offert.getId()==0){
			room.getOccupiedDays().addAll(reservoirDates);
			room.setOffert(offert);
			offert.setRooms(room);
			res2 = this.offertRepository.save(offert);
			room.setOffert(res2);
			this.roomService.save(room);
		}else{
			dateRoomsDephinitive.addAll(reservoirDates);
			room.setOccupiedDays(dateRoomsDephinitive);
			this.roomService.save(room);
			res2=this.offertRepository.saveAndFlush(offert);
		}
		
		return res2;
		
		
	}


	public Offert findOne(int id) {
		Offert res;
		res= offertRepository.findOne(id);
		return res;
	}


	public boolean checkOffertFreeRoom(Room rooms) {
		boolean res=true;
		if(rooms.getOffert()!=null){
			Date now=new Date();
			Offert offert= rooms.getOffert();
			res=!(offert.getClient()!=null && offert.getCheckOut().after(now));
		}
		return res;
	}
	
	public Offert makeTheBill(Offert offert) {
		Bill bill=billService.create();
		bill.setOffert(offert);
		Bill saved=billService.save(bill);
		BillLine facturilla=billLineService.create(saved);
		facturilla.setAmount(offert.getTotalPrice());
		facturilla.setReason("Precio de la oferta de la habitación " +offert.getRooms().getNumber()+ " en el hotel " +offert.getRooms().getHotel().getName());
        BillLine savedu= billLineService.save(facturilla);
        Bill hehe = savedu.getBill();
		bill.setTotalAmount(savedu.getAmount());
		bill.getBillLines().add(savedu);
		Bill xd = billService.save(hehe);
		offert.setBill(xd);
		return offert;
	}


	public boolean checkGoodHotel(Hotel hotel, Date checkIn, Date checkOut) {
		boolean res = true;
		Manager mana = managerService.findByPrincipal();
		res = hotel.getRequest().getState().equals(State.ACCEPTED);
		if (res == true) {
			res = mana.getId()==hotel.getRequest().getManager().getId();
			if (res == true) {
				res = checkIn.after(hotel.getRequest().getTimeIn());
			if (res == true)
				res = checkOut.before(hotel.getRequest().getTimeOut());
		}}
		return res;
	}
	
	public Collection<Offert> allOfferts(){
		return offertRepository.findAll();
	}

}
