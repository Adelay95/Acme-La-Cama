
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ClientRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Client;
import domain.Comment;
import domain.CreditCard;
import domain.Finder;
import domain.Folder;
import domain.Message;
import domain.Offert;
import domain.Reservation;
import domain.Room;
import forms.FormActor;

@Service
@Transactional
public class ClientService {

	@Autowired
	private ClientRepository		clientRepository;
	@Autowired
	private Validator				validator;
	@Autowired
	private UserAccountRepository	uar;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private FolderService			folderService;


	public ClientService() {
		super();
	}

	public Client create() {
		Client res;
		final Collection<Message> messages = new HashSet<Message>();
		final Collection<Folder> folders = new HashSet<Folder>();
		final Collection<Comment> comments = new HashSet<Comment>();
		final Collection<Offert> offerts = new HashSet<Offert>();
		final Collection<Reservation> reservations = new HashSet<Reservation>();
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority("CLIENT");
		final Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		res = new Client();
		res.setComments(comments);
		res.setFolders(folders);
		res.setMessageReceived(messages);
		res.setMessageSent(messages);
		res.setOfferts(offerts);
		res.setReservations(reservations);
		res.setUserAccount(userAccount);
		return res;
	}
	public Boolean checkExceptions(final Client client) {
		Boolean res = true;

		try {
			Assert.isTrue(this.checkTime(client.getCreditCard()));
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
	public Client save(final Client client) {
		Assert.notNull(client);
		Client res;
		res = this.clientRepository.save(client);
		if (client.getId() == 0) {

			final Folder folder1 = this.folderService.create(res);
			final Folder folder2 = this.folderService.create(res);
			folder1.setName("INBOX");
			folder2.setName("OUTBOX");
			final Folder folder1saved = this.folderService.save(folder1);
			final Folder folder2saved = this.folderService.save(folder2);
			res.getFolders().add(folder1saved);
			res.getFolders().add(folder2saved);
			res = this.clientRepository.save(res);
		}
		return res;
	}

	public void delete(final Client client) {
		Assert.notNull(client);
		Assert.isTrue(client.getId() != 0);
		this.clientRepository.delete(client);
	}

	public Collection<Client> allClients() {
		Collection<Client> res;
		res = this.clientRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Client findOne(final int id) {
		Client res;
		res = this.clientRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Client findByPrincipal() {
		Client res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	private Client findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Client res;
		res = this.clientRepository.findByUserAccountId(userAccount.getId());
		return res;

	}

	public Client reconstruct(final FormActor formActor, final BindingResult binding) {
		Client result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create();
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.clientRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		this.validator.validate(result, binding);
		return result;
	}
	private Client fullyReconstruct(final Client result, final FormActor formActor) {
		result.setCreditCard(formActor.getCreditCard());
		result.setName(formActor.getName());
		result.setSurname(formActor.getSurname());
		result.setEmail(formActor.getEmail());
		result.setPostalAdress(formActor.getPostalAdress());
		result.setPhoneNumber(formActor.getPhoneNumber());
		result.setDniNif(formActor.getDniNif());
		return result;
	}
	
	public Client reconstruct(final FormActor formActor) {
		Client result;

		if (formActor.getId() == 0) {
			Assert.isTrue(formActor.getPassword().equals(formActor.getPassword2()));
			result = this.create();
			result = this.fullyReconstruct(result, formActor);

		} else {
			result = this.clientRepository.findOne(formActor.getId());
			result = this.fullyReconstruct(result, formActor);
		}
		return result;
	}
	
	
	
	public FormActor createMyForm() {
		final FormActor res = new FormActor();
		Assert.notNull(res);
		final Client me = this.findByPrincipal();
		res.setCreditCard(me.getCreditCard());
		res.setEmail(me.getEmail());
		res.setId(me.getId());
		res.setConfirmed(true);
		res.setVersion(me.getVersion());
		res.setPhoneNumber(me.getPhoneNumber());
		res.setName(me.getName());
		res.setSurname(me.getSurname());
		res.setPostalAdress(me.getPostalAdress());
		res.setDniNif(me.getDniNif());
		return res;
	}
	public Client saveUserAccount(final FormActor formActor, final Client client) {
		Assert.isTrue(formActor.getConfirmed());
		Assert.isTrue(formActor.getUsername() != "" && formActor.getUsername().length() >= 5 && formActor.getUsername().length() <= 32);
		Assert.isTrue(formActor.getPassword() != "" && formActor.getPassword().length() >= 5 && formActor.getPassword().length() <= 32);
		final UserAccount uacc = client.getUserAccount();
		uacc.setUsername(formActor.getUsername());
		uacc.setPassword(formActor.getPassword());
		final UserAccount def = this.uar.save(uacc);
		client.setUserAccount(def);
		final String oldPs = client.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = client.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.uar.save(old);
		client.setUserAccount(newOne);
		return client;

	}

	public Collection<Room> finderRoomsResults() {
		Collection<Room> res;
		final Client principal = this.findByPrincipal();
		res = this.finderService.searchFinderRoom(principal.getFinder());
		return res;
	}

	public Collection<Offert> finderOffertsResults() {
		Collection<Offert> res;
		final Client principal = this.findByPrincipal();
		res = this.finderService.searchFinderOffert(principal.getFinder());
		return res;
	}

	public Finder saveFinder(final Finder finder) {
		Assert.notNull(finder);
		final Client client = this.findByPrincipal();
		Assert.isTrue(finder.getClient().getId() == client.getId());
		if (finder.getCheckIn() != null && finder.getCheckOut() != null)
			Assert.isTrue(finder.getCheckIn().before(finder.getCheckOut()));
		if (finder.getMinimumPrice() != null && finder.getMaximumPrice() != null)
			Assert.isTrue(finder.getMaximumPrice() >= finder.getMinimumPrice());
		Finder res;
		res = this.finderService.save(finder);
		return res;

	}

	public Finder createFinder(final Client client) {
		Assert.notNull(client);
		final Finder notsaved = this.finderService.create(client);
		final Finder res = this.finderService.save(notsaved);
		final Finder res2 = this.finderService.save(res);
		client.setFinder(res2);
		this.save(client);
		return res2;
	}

	public List<Reservation> reservationThisHotel(final int id) {
		return new ArrayList<Reservation>(this.clientRepository.reservationThisHotel(id, this.findByPrincipal().getId()));
	}

	public boolean checkDNI(final String dniNif) {
		final Collection<Client> res = this.clientRepository.checkDNI(dniNif);
		return res.isEmpty();
	}

}
