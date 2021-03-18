
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;
	@Autowired
	private FolderService folderService;
	@Autowired
	private MessageService messageService;


	public ActorService() {
		super();
	}
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor res;
		res = this.actorRepository.save(actor);
		return res;
	}

	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	private Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor res;
		res = this.actorRepository.findByUserAccountId(userAccount.getId());
		return res;

	}

	public Collection<Actor> allActors() {
		final Collection<Actor> actors = this.actorRepository.findAll();
		Assert.notNull(actors);
		return actors;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		this.actorRepository.delete(actor);
	}

	public Actor findOne(final int actorId) {
		Actor res;
		res = this.actorRepository.findOne(actorId);
		Assert.notNull(res);
		return res;
	}
	public Collection<Actor> todosActoresMenosYo(final int id) {
		return this.actorRepository.todosActoresMenosYo(id);
	}
	
	public void sendMessage(final Message message) {
		Assert.notNull(message);
		final Actor sender = message.getSender();
		final Folder folder1 = message.getFolder();
		final Actor receiver = message.getReceiver();
		Folder folder2 = null;
		final Message message2 = this.messageService.create(sender, receiver);
		message2.setBody(message.getBody());
		message2.setMoment(message.getMoment());
		message2.setSubject(message.getSubject());
		folder2 = this.folderService.findINBOX(receiver);
		message2.setFolder(folder2);
		final Message saved1 = this.messageService.save(message);
		final Message saved2 = this.messageService.save(message2);
		folder1.getMessages().add(saved1);
		folder2.getMessages().add(saved2);
		sender.getMessageSent().add(saved1);
		receiver.getMessageReceived().add(saved1);
		sender.getMessageSent().add(saved2);
		receiver.getMessageReceived().add(saved2);
		this.folderService.save(folder1);
		this.folderService.save(folder2);
		this.save(sender);
		this.save(receiver);
	}
	public void sendMessage2(final Message message) {
		Assert.notNull(message);
		final Actor sender = message.getSender();
		final Folder folder1 = message.getFolder();
		final Actor receiver = message.getReceiver();
		Folder folder2 = null;
		final Message message2 = this.messageService.create(sender, receiver);
		message2.setBody(message.getBody());
		message2.setMoment(message.getMoment());
		message2.setSubject(message.getSubject());
		folder2 = this.folderService.findREPORT(receiver);
		message2.setFolder(folder2);
		final Message saved1 = this.messageService.save(message);
		final Message saved2 = this.messageService.save(message2);
		folder1.getMessages().add(saved1);
		folder2.getMessages().add(saved2);
		sender.getMessageSent().add(saved1);
		receiver.getMessageReceived().add(saved1);
		sender.getMessageSent().add(saved2);
		receiver.getMessageReceived().add(saved2);
		this.folderService.save(folder1);
		this.folderService.save(folder2);
		this.save(sender);
		this.save(receiver);
	}

	public void deleteMessage(final Message message) {
		Assert.notNull(message);
		final Folder f = message.getFolder();
		f.getMessages().remove(message);
		this.folderService.save(f);
		this.messageService.deleteFinish(message);
	}

	public void moveMessage(final Message message, final Folder newOne) {
		Assert.notNull(message);
		Assert.notNull(newOne);
		final Folder f1 = message.getFolder();
		f1.getMessages().remove(message);
		this.folderService.save(f1);
		newOne.getMessages().add(message);
		message.setFolder(newOne);
		this.messageService.save(message);
		this.folderService.save(newOne);
	}

	public Collection<String> todasFoldersActor(final int id) {
		return this.actorRepository.todasFoldersActor(id);
	}
	public Collection<Actor> findAll() {
		Collection<Actor> res;
		res=actorRepository.findAll();
		return res;
		
	}
	
	public void flush(){
		actorRepository.flush();
	}

}
