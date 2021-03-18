
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ServicesRepository;
import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.Message;
import domain.Room;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	@Autowired
	private ServicesRepository	serviceRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private RoomService			roomService;
	@Autowired
	private MessageService		messageService;
	@Autowired
	private ManagerService		managerService;


	public ServicesService() {
		super();
	}
	public Services create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		final Services res;
		final Collection<Room> rooms = new ArrayList<Room>();
		res = new Services();
		res.setRooms(rooms);
		return res;

	}

	public Services createManager() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Manager);
		final Services res;
		final Collection<Room> rooms = new ArrayList<Room>();
		res = new Services();
		res.setRooms(rooms);
		return res;

	}

	public Services save(final Services service) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		Assert.notNull(service);
		Services res;
		res = this.serviceRepository.save(service);
		return res;

	}
	public Services saveNormal(final Services service) {
		Assert.notNull(service);
		Services res;
		res = this.serviceRepository.save(service);
		return res;

	}

	public Collection<Services> allServices() {
		Collection<Services> res;
		res = this.serviceRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Services findOne(final int servicesId) {
		Services res;
		res = this.serviceRepository.findOne(servicesId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Services services) {
		Assert.notNull(services);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		for (final Room r : services.getRooms()) {
			r.getServices().remove(services);
			this.roomService.save(r);
		}
		this.serviceRepository.delete(services);

	}

	public void sendPetition(final Services services) {
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Manager);
		final Manager sender = this.managerService.findByPrincipal();
		Assert.isTrue(!sender.getBanned());
		final Actor recipient = this.serviceRepository.selectAdministrator();
		final Message res;
		res = this.messageService.create(sender, recipient);
		res.setSubject("Services Petition");
		res.setBody("The Actor: " + sender.getName() + sender.getSurname() + " wants to include this service: " + services.getName() + " with URL: " + services.getImageURL());
		this.actorService.sendMessage(res);
	}

	public Collection<String> getAllNames() {
		return this.serviceRepository.getAllNames();
	}

	public Collection<Services> getServicesByNames(final Collection<String> services) {
		final Collection<Services> servicesS = new HashSet<Services>();
		for (final String e : services) {
			final Services hehe = this.serviceRepository.getServiceByName(e);
			servicesS.add(hehe);
		}
		return servicesS;
	}

	public Collection<Services> AllServicesRoom(final int roomId) {
		return this.serviceRepository.getAllServicesRoom(roomId);
	}
	public Administrator getAdmin() {
		return this.serviceRepository.selectAdministrator();
	}

	public Collection<Services> noServicesRoom(final int roomId) {
		return this.serviceRepository.getNoServicesRoom(roomId);
	}
	public void flush(){
		serviceRepository.flush();
	}

}
