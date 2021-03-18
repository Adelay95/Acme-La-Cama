
package usecases;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import services.HotelService;
import services.ManagerService;
import services.RoomService;
import services.ServicesService;
import services.WorkerService;
import utilities.AbstractTest;
import domain.Hotel;
import domain.KindOfRoom;
import domain.Manager;
import domain.Room;
import domain.Services;
import forms.FormRoom;
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class useCaseCreateRooms extends AbstractTest {

	@Autowired
	private RoomService	roomService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private ServicesService servicesService;

    //Crear múltiples habitaciones en uno de mis hoteles.
	//Servicios

	@Test
	public void driver() {
		final Object testingData[][] =  {
			{
				//Este es un caso de prueba normal, para comprobar la funcionalidad general
				"manager1", KindOfRoom.DOUBLE,true,null,
				true, 2, "Descripcion de prueba",
				"http://www.twitter.com",677,20, null
			},{
				//En este caso introducimos un precio personal en lugar del automatizado
				"manager1", KindOfRoom.DOUBLE,false,123.23,
				true, 2, "Descripcion de prueba",
				"http://www.twitter.com",677,20, null
			},{
				//En este caso introducimos datos incorrectos
				"manager1", KindOfRoom.DOUBLE,false,123.23,
				true, 2, "Descripcion de prueba",
				"http://www.twitter.com",677,-5, IllegalArgumentException.class
			},{
				//En este caso introducimos datos incorrectos
				"manager2", KindOfRoom.DOUBLE,false,123.23,
				true, 2, "Descripcion de prueba",
				"http://www.twitter.com",677,20, IllegalArgumentException.class
			},{
				//El numero de habitaciones actual mas las que se van a crear exceden la capacidad del hotel
				"manager1", KindOfRoom.DOUBLE,false,123.23,
				true, 2, "Descripcion de prueba",
				"http://www.twitter.com",677,250, IllegalArgumentException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (KindOfRoom) testingData[i][1], (Boolean) testingData[i][2], (Double) testingData[i][3], (Boolean) testingData[i][4], 
				(Integer)testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],(Integer) testingData[i][8],(Integer) testingData[i][9],
				(Class<?>) testingData[i][10]);
	}
	
	protected void template(final String username, final KindOfRoom kindOfRoom,final Boolean checkAutomaticPrice, final Double personalPriceDays,final Boolean kids,final Integer capacidad
		, final String description, final String picture, final Integer hotelId, final Integer numberOfRooms,final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
		    this.authenticate(username);
			final Hotel hotel = this.hotelService.findOne(hotelId);
			Collection<Room> roomPrincipio=hotel.getRooms();
			int roomsPrincipio=roomPrincipio.size();
			final FormRoom formRoom = new FormRoom();
			formRoom.setHotel(hotel);
			formRoom.setKindOfRoom(kindOfRoom);
			Assert.isTrue(formRoom.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formRoom.getHotel()));
			final Manager yo = this.managerService.findByPrincipal();
			Assert.isTrue(!yo.getBanned());
			final Double orginalPrice = this.roomService.calculateAuthomaticPrice(formRoom);
			formRoom.setOriginalPriceDays(orginalPrice);
			final Integer roomNumber = this.roomService.getMaxNumberHotel(formRoom.getHotel().getId());
			formRoom.setNumber(roomNumber + 1);
			formRoom.setCapacity(capacidad);
			formRoom.setCheckAutomaticPrice(checkAutomaticPrice);
			formRoom.setDescription(description);
			formRoom.setKids(kids);
            formRoom.setNumberOfRooms(numberOfRooms);
            formRoom.setPersonalPriceDays(personalPriceDays);
            formRoom.setPicture(picture);
            Collection<String> servicesPrueba=new HashSet<String>();
            formRoom.setServices(servicesPrueba);
			if (!formRoom.getCheckAutomaticPrice())
				Assert.isTrue(formRoom.getPersonalPriceDays() != null);
			Assert.isTrue(formRoom.getHotel().getRequest().getManager().getId() == this.managerService.findByPrincipal().getId());
			Assert.isTrue(this.workerService.checkGoodHotel(formRoom.getHotel()));
			Assert.isTrue(this.roomService.checkCapacityHotel(formRoom.getHotel(), formRoom.getNumberOfRooms()));
			Room room = this.roomService.reconstruct(formRoom, null);
			Assert.isTrue(formRoom.getNumberOfRooms() > 0);
			final Collection<Services> services = this.servicesService.getServicesByNames(formRoom.getServices());
			room.setServices(services);
			this.roomService.saveMultipleRooms(services, formRoom, room);
			final Hotel hotel2 = this.hotelService.findOne(hotelId);
			Collection<Room> roomFin=hotel2.getRooms();
			Assert.isTrue(roomsPrincipio+formRoom.getNumberOfRooms()==roomFin.size());
			this.unauthenticate();
			
		}catch(Exception oops){
			caught=oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	     //Añadir un servicio a una habitacion de uno de mis hoteles.
		//Servicios

		@Test
		public void driver2() {
			final Object testingData[][] =  {
				{
					//Este es un caso de prueba normal, para comprobar la funcionalidad general.
					"manager2", 556,null
				},{
					//En este caso el manager no es el propietario del hotel al que pertenece.
					"manager1",556,IllegalArgumentException.class
				}
				
			};
			for (int i = 0; i < testingData.length; i++)
				this.template2((String) testingData[i][0], (Integer) testingData[i][1],(Class<?>) testingData[i][2]);
		}
		
		protected void template2(final String username,final Integer roomId,final Class<?> expected) {
			Class<?> caught;
			caught = null;
			try{
			    this.authenticate(username);
			    final Manager manager = this.managerService.findByPrincipal();
				final Room room = this.roomService.findOne(roomId);
				Assert.isTrue(room.getHotel().getRequest().getManager().equals(manager));
				final Manager yo = this.managerService.findByPrincipal();
				Assert.isTrue(!yo.getBanned());
				this.managerService.addService(roomId, 580);
				final Room room2 = this.roomService.findOne(roomId);
				final Services services=this.servicesService.findOne(580);
				Assert.isTrue(room2.getServices().contains(services));
				this.unauthenticate();
				
			}catch(Exception oops){
				caught=oops.getClass();
			}
			this.checkExceptions(expected, caught);
		}
		
		  //Eliminar un servicio a una habitacion de uno de mis hoteles.
				//Servicios

				@Test
				public void driver3() {
					final Object testingData[][] =  {
						{
							//Este es un caso de prueba normal, para comprobar la funcionalidad general.
							"manager2", 556,null
						},{
							//En este caso el manager no es el propietario del hotel al que pertenece.
							"manager1",556,IllegalArgumentException.class
						}
						
					};
					for (int i = 0; i < testingData.length; i++)
						this.template3((String) testingData[i][0], (Integer) testingData[i][1],(Class<?>) testingData[i][2]);
				}
				
				protected void template3(final String username,final Integer roomId,final Class<?> expected) {
					Class<?> caught;
					caught = null;
					try{
					    this.authenticate(username);
					    final Manager manager = this.managerService.findByPrincipal();
						final Room room = this.roomService.findOne(roomId);
						Assert.isTrue(room.getHotel().getRequest().getManager().equals(manager));
						final Manager yo = this.managerService.findByPrincipal();
						Assert.isTrue(!yo.getBanned());
						this.managerService.removeService(roomId, 571);
						final Room room2 = this.roomService.findOne(roomId);
						final Services services=this.servicesService.findOne(580);
						Assert.isTrue(!room2.getServices().contains(services));
						this.unauthenticate();
						
					}catch(Exception oops){
						caught=oops.getClass();
					}
					this.checkExceptions(expected, caught);
				}
	
}
