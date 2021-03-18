
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

import repositories.WorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.CreditCard;
import domain.Folder;
import domain.Hotel;
import domain.Message;
import domain.State;
import domain.Worker;
import forms.FormActor;

@Service
@Transactional
public class WorkerService {

	@Autowired
	private WorkerRepository		workerRepository;

	@Autowired
	private Validator				validator;
	@Autowired
	private UserAccountRepository	uar;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private HotelService			hotelService;


	public WorkerService() {
		super();
	}

	public Worker create(final Hotel hotel) {
		Worker res;
		final Collection<Message> messages = new HashSet<Message>();
		final Collection<Folder> folders = new HashSet<Folder>();
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority("WORKER");
		final Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		res = new Worker();
		res.setFolders(folders);
		res.setMessageReceived(messages);
		res.setMessageSent(messages);
		res.setHotel(hotel);
		res.setUserAccount(userAccount);
		return res;
	}

	public Boolean checkExceptions(final Worker worker) {
		Boolean res = true;

		try {
			Assert.isTrue(this.checkTime(worker.getCreditCard()));
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
	public Worker save(final Worker worker) {
		Assert.notNull(worker);
		Worker res;
		res = this.workerRepository.save(worker);
		if (worker.getId() == 0) {

			final Folder folder1 = this.folderService.create(res);
			final Folder folder2 = this.folderService.create(res);
			folder1.setName("INBOX");
			folder2.setName("OUTBOX");
			final Folder folder1saved = this.folderService.save(folder1);
			final Folder folder2saved = this.folderService.save(folder2);
			res.getFolders().add(folder1saved);
			res.getFolders().add(folder2saved);
			res = this.workerRepository.save(res);
		}
		return res;
	}

	public void delete(final Worker worker) {
		Assert.notNull(worker);
		Assert.isTrue(worker.getId() != 0);
		final Collection<Message> messageSent = worker.getMessageSent();
		final Collection<Message> messageReceived = worker.getMessageReceived();
		final Collection<Folder> folders = worker.getFolders();
		final Hotel hotel = worker.getHotel();
		for (final Message e : messageSent)
			this.messageService.delete(e);
		for (final Message e : messageReceived)
			this.messageService.delete(e);
		for (final Folder f : folders)
			this.folderService.delete(f);
		hotel.getWorkers().remove(worker);
		this.hotelService.save(hotel);
		this.workerRepository.delete(worker);
	}

	public Collection<Worker> allWorkers() {
		Collection<Worker> res;
		res = this.workerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Worker findOne(final int id) {
		Worker res;
		res = this.workerRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Worker findByPrincipal() {
		Worker res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	private Worker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Worker res;
		res = this.workerRepository.findByUserAccountId(userAccount.getId());
		return res;

	}

	public Worker reconstruct(final FormActor formActor, final BindingResult binding) {
		Worker result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create(formActor.getHotel());
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.workerRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		this.validator.validate(result, binding);
		return result;
	}
	
	public Worker reconstruct(final FormActor formActor) {
		Worker result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create(formActor.getHotel());
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.workerRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		
		return result;
	}

	private Worker fullyReconstruct(final Worker result, final FormActor formActor) {
		result.setCreditCard(formActor.getCreditCard());
		result.setName(formActor.getName());
		result.setSurname(formActor.getSurname());
		result.setEmail(formActor.getEmail());
		result.setPostalAdress(formActor.getPostalAdress());
		result.setPhoneNumber(formActor.getPhoneNumber());
		result.setSalary(formActor.getSalary());
		result.setHotel(formActor.getHotel());
		return result;
	}

	public boolean checkGoodHotel(final Hotel hotel) {
		boolean res = true;
		res = hotel.getRequest().getState().equals(State.ACCEPTED);
		if (res == true) {
			final Date dateHotel = new Date();
			res = dateHotel.after(hotel.getRequest().getTimeIn());
			if (res == true)
				res = dateHotel.before(hotel.getRequest().getTimeOut());
		}
		return res;
	}

	public Worker saveUserAccount(final FormActor formActor, final Worker worker) {
		Assert.isTrue(formActor.getConfirmed());
		Assert.isTrue(formActor.getUsername() != "" && formActor.getUsername().length() >= 5 && formActor.getUsername().length() <= 32);
		Assert.isTrue(formActor.getPassword() != "" && formActor.getPassword().length() >= 5 && formActor.getPassword().length() <= 32);
		final UserAccount uacc = worker.getUserAccount();
		uacc.setUsername(formActor.getUsername());
		uacc.setPassword(formActor.getPassword());
		final UserAccount def = this.uar.save(uacc);
		worker.setUserAccount(def);
		final String oldPs = worker.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = worker.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.uar.save(old);
		worker.setUserAccount(newOne);
		return worker;

	}

}
