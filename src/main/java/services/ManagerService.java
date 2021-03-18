
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.CreditCard;
import domain.Folder;
import domain.Global;
import domain.Hotel;
import domain.Manager;
import domain.Message;
import domain.Offert;
import domain.Request;
import domain.Room;
import domain.Services;
import domain.State;
import domain.Worker;
import forms.FormActor;
import forms.FormHotel;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository		managerRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private FolderService			folderService;
	@Autowired
	private UserAccountRepository	uar;
	@Autowired
	private HotelService			hotelService;

	@Autowired
	private RoomService				roomService;
	@Autowired
	private ServicesService			servicesService;
	@Autowired
	private RequestService			requestService;
	@Autowired
	private GlobalService			globalService;


	public ManagerService() {
		super();
	}

	public Manager create() {
		Manager res;
		final Collection<Message> messages = new HashSet<Message>();
		final Collection<Folder> folders = new HashSet<Folder>();
		final Collection<Request> requests = new HashSet<Request>();
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority("MANAGER");
		final Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		res = new Manager();
		res.setFolders(folders);
		res.setRequests(requests);
		res.setBanned(false);
		res.setMessageReceived(messages);
		res.setMessageSent(messages);
		res.setUserAccount(userAccount);
		return res;
	}
	public Boolean checkExceptions(final Manager manager) {
		Boolean res = true;

		try {
			Assert.isTrue(this.checkTime(manager.getCreditCard()));
		} catch (final Exception oops) {
			res = false;
		}
		return res;
	}

	public Boolean checkTime(final CreditCard cc) {
		Boolean res = true;
		try {
			final Date now = new Date();
			if (cc != null) {
				@SuppressWarnings("deprecation")
				final Date fechaCaducidad = new Date(cc.getExpirationYear() - 1900, cc.getExpirationMonth() - 1, 1);
				Assert.isTrue(now.before(fechaCaducidad));

			}
		} catch (final Exception oops) {
			res = false;
		}
		return res;
	}
	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		Manager res;
		res = this.managerRepository.save(manager);
		if (manager.getId() == 0) {

			final Folder folder1 = this.folderService.create(res);
			final Folder folder2 = this.folderService.create(res);
			folder1.setName("INBOX");
			folder2.setName("OUTBOX");
			final Folder folder1saved = this.folderService.save(folder1);
			final Folder folder2saved = this.folderService.save(folder2);
			res.getFolders().add(folder1saved);
			res.getFolders().add(folder2saved);
			res = this.managerRepository.save(res);
		}
		return res;
	}

	public void delete(final Manager manager) {
		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);
		this.managerRepository.delete(manager);
	}

	public Collection<Manager> allManagers() {
		Collection<Manager> res;
		res = this.managerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Manager findOne(final int id) {
		Manager res;
		res = this.managerRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Manager findByPrincipal() {
		Manager res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	private Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager res;
		res = this.managerRepository.findByUserAccountId(userAccount.getId());
		return res;

	}

	public Manager reconstruct(final FormActor formActor, final BindingResult binding) {
		Manager result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create();
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.managerRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		this.validator.validate(result, binding);
		return result;
	}
	private Manager fullyReconstruct(final Manager result, final FormActor formActor) {
		result.setCreditCard(formActor.getCreditCard());
		result.setName(formActor.getName());
		result.setSurname(formActor.getSurname());
		result.setEmail(formActor.getEmail());
		result.setPostalAdress(formActor.getPostalAdress());
		result.setPhoneNumber(formActor.getPhoneNumber());
		return result;
	}

	public Manager reconstruct(final FormActor formActor) {
		Manager result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create();
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.managerRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		return result;
	}
	
	public Manager saveUserAccount(final FormActor formActor, final Manager manager) {
		Assert.isTrue(formActor.getConfirmed());
		Assert.isTrue(formActor.getUsername() != "" && formActor.getUsername().length() >= 5 && formActor.getUsername().length() <= 32);
		Assert.isTrue(formActor.getPassword() != "" && formActor.getPassword().length() >= 5 && formActor.getPassword().length() <= 32);
		final UserAccount uacc = manager.getUserAccount();
		uacc.setUsername(formActor.getUsername());
		uacc.setPassword(formActor.getPassword());
		final UserAccount def = this.uar.save(uacc);
		manager.setUserAccount(def);
		final String oldPs = manager.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = manager.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.uar.save(old);
		manager.setUserAccount(newOne);
		return manager;

	}

	public Collection<Worker> todosMisTrabajadores(final int id) {
		return this.managerRepository.todosMisTrabajadores(id);
	}

	public Collection<Offert> getAllMyOfferts(final int id) {
		return this.managerRepository.getAllMyOfferts(id);
	}

	public void deleteHotel(final Hotel hotel) {
		Assert.notNull(hotel);
		final Manager manager = this.findByPrincipal();
		final Request request = hotel.getRequest();
		Assert.isTrue(request.getManager().equals(manager));
		//final Date hoy = new Date();
		//final Date checkOut = hotel.getRequest().getTimeOut();
		//	Assert.isTrue(!request.getState().equals(State.ACCEPTED) || hoy.after(checkOut));
		this.hotelService.delete(hotel);
	}
	public Double calculaPrecio(final Date una, final Date dos) {
		Assert.isTrue(una.before(dos));
		final Global g = this.globalService.findTheGlobal();
		final long diferenciaEn_ms = dos.getTime() - una.getTime();
		final long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
		final int hi = (int) dias;
		return g.getRequestPriceDay() * hi;
	}

	public Request saveRequest(final FormHotel formHotel) {
		Assert.notNull(formHotel);
		final Manager manager = this.findByPrincipal();
		Request request;
		final Date hoy = new Date();
		final Date checkIn = formHotel.getTimeIn();
		final Date checkOut = formHotel.getTimeOut();

		Assert.isTrue(checkIn.before(checkOut));
		Assert.isTrue(checkIn.after(hoy));
		Assert.isTrue(checkOut.after(hoy));
		if (formHotel.getId() != 0) {
			final Hotel hotel = this.hotelService.findOne(formHotel.getId());
			request = this.requestService.findOne(hotel.getRequest().getId());
			Assert.isTrue(request.getManager().equals(manager));
			//			final Date checkOut = request.getTimeOut();
			//			Assert.isTrue((hoy.after(checkOut)) || (request.getState().equals(State.ACCEPTED) == false));
		} else
			request = this.requestService.create(manager);
		request.setPrice(this.calculaPrecio(formHotel.getTimeIn(), formHotel.getTimeOut()));
		request.setTimeIn(formHotel.getTimeIn());
		request.setTimeOut(formHotel.getTimeOut());
		request.setState(State.PENDING);
		final Request saved = this.requestService.save(request);
		return saved;
	}

	public Hotel saveHotel(final Hotel hotel) {
		Assert.notNull(hotel);
		Hotel res;
		final Date hoy = new Date();
		final Manager manager = this.findByPrincipal();
		final Request request = this.requestService.findOne(hotel.getRequest().getId());
		Assert.isTrue(request.getManager().equals(manager));
		//		if (hotel.getId() != 0) {
		//
		//			final Date checkOut = hotel.getRequest().getTimeOut();
		//
		//			Assert.isTrue(!request.getState().equals(State.ACCEPTED) || hoy.after(checkOut));
		//		}
		final Hotel saved = this.hotelService.save(hotel);
		res = saved;
		if (hotel.getId() == 0) {
			request.setHotel(saved);
			final Request rsaved = this.requestService.save(request);
			saved.setRequest(rsaved);
			res = this.hotelService.save(saved);
		}
		return saved;
	}

	public void saveFormHotel(final FormHotel formHotel, final Hotel hotel) {
		final Request save = this.saveRequest(formHotel);
		hotel.setRequest(save);
		this.saveHotel(hotel);
	}

	public void addService(final int roomId, final int serviceId) {
		final Manager manager = this.findByPrincipal();
		final Room room = this.roomService.findOne(roomId);
		final Services service = this.servicesService.findOne(serviceId);
		Assert.isTrue(room.getHotel().getRequest().getManager().equals(manager));
		room.getServices().add(service);
		final Room saveR1 = this.roomService.save(room);
		service.getRooms().add(saveR1);
		this.servicesService.saveNormal(service);
	}

	public void removeService(final int roomId, final int serviceId) {
		final Manager manager = this.findByPrincipal();
		final Room room = this.roomService.findOne(roomId);
		final Services service = this.servicesService.findOne(serviceId);
		Assert.isTrue(room.getHotel().getRequest().getManager().equals(manager));
		room.getServices().remove(service);
		final Room saveR1 = this.roomService.save(room);
		service.getRooms().remove(saveR1);
		this.servicesService.saveNormal(service);
	}

	public void flush() {
		managerRepository.flush();
		
	}

}
