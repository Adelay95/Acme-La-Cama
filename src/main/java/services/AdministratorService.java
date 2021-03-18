
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Hotel;
import domain.Manager;
import domain.Message;
import domain.Request;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;
	@Autowired
	private FolderService folderService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private HotelService hotelService;


	public AdministratorService() {
		super();
	}

	public Administrator create() {
		Administrator res;
		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority("ADMINISTRATOR");
		final Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		res = new Administrator();
		res.setUserAccount(userAccount);

		return res;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		this.administratorRepository.delete(administrator);
	}

	public Collection<Administrator> allAdministrator() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Administrator findOne(final int id) {
		Administrator res;
		res = this.administratorRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	private Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}
	
	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator res;
		res = this.administratorRepository.save(administrator);
		if (administrator.getId() == 0) {

			final Folder folder1 = this.folderService.create(res);
			final Folder folder2 = this.folderService.create(res);
			final Folder folder3 = this.folderService.create(res);
			folder1.setName("INBOX");
			folder2.setName("OUTBOX");
			folder3.setName("REPORTS");
			final Folder folder1saved = this.folderService.save(folder1);
			final Folder folder2saved = this.folderService.save(folder2);
			final Folder folder3saved = this.folderService.save(folder3);
			res.getFolders().add(folder1saved);
			res.getFolders().add(folder2saved);
			res.getFolders().add(folder3saved);
			res = this.administratorRepository.save(res);
		}
		return res;
	}

	public void banManager(final int actorId) {
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);
		final Manager mana = this.managerService.findOne(actorId);
		Assert.isTrue(!mana.getBanned());
		mana.setBanned(true);
		final Actor actor1 = this.actorService.findByPrincipal();
		final Message mes;
		mes = this.messageService.create(actor1, mana);
		mes.setSubject("Banned");
		mes.setBody("The manager " + mana.getSurname() + ", " + mana.getName() + " has been banned");
		this.actorService.sendMessage(mes);

	}
	public void unbanManager(final int actorId) {
		Assert.isTrue(this.actorService.findByPrincipal() instanceof Administrator);
		final Manager mana = this.managerService.findOne(actorId);
		Assert.isTrue(mana.getBanned());
		mana.setBanned(false);
		final Actor actor1 = this.actorService.findByPrincipal();
		final Message mes;
		mes = this.messageService.create(actor1, mana);
		mes.setSubject("Banned");
		mes.setBody("The manager " + mana.getSurname() + ", " + mana.getName() + " has been unbaned");
		this.actorService.sendMessage(mes);
	}
	
	public Double ocupacionHotelera(final int hotelId) {
		Double res = 0.0;
		final Double actuales = new Double(this.administratorRepository.numeroHabitacionesOcupadas(hotelId));
		final Double totales = new Double(this.hotelService.findOne(hotelId).getRooms().size());
		if (totales != 0)
			res = (actuales / totales) * 100.0;

		return res;
	}
	public Double mediaAsistenciaExcur(final int hotelId) {
		final Double res = this.administratorRepository.avgClientsTrips(hotelId);
		if (res == null)
			return 0.0;
		return res;
	}
	public Double mediaStarsComentarios(final int hotelId) {

		final Double res = this.administratorRepository.avgStarsComment(hotelId);
		if (res == null)
			return 0.0;
		return res;
	}
	public Long numClientesReservas(final int hotelId) {
		return this.administratorRepository.numeroClientestotales(hotelId);
	}
	public Double mediaGastado(final int hotelId) {
		final Double res = this.administratorRepository.avgBill(hotelId);
		if (res == null)
			return 0.0;
		return res;
	}
	//admin
	public Collection<Manager> ordenado() {
		return this.administratorRepository.ordenado();
	}
	public Collection<Hotel> ordenados() {
		return this.administratorRepository.ordenados();
	}
	public Integer min() {
		return this.administratorRepository.min();
	}
	public Double avg() {
		final Double res = this.administratorRepository.avg();
		if (res == null)
			return 0.0;
		return res;
	}
	public Integer max() {
		return this.administratorRepository.max();
	}
	public Double avgWorker() {
		final Double res = this.administratorRepository.avgWorker();
		if (res == null)
			return 0.0;
		return res;
	}
	public Integer minWorker() {
		return this.administratorRepository.minWorker();
	}
	public Integer maxWorker() {
		return this.administratorRepository.maxWorker();
	}

	public Collection<Request> requests() {
	    return this.administratorRepository.requests();
	}


}
