
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private FolderService		folderService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageRepository	messageRepository;


	public MessageService() {
		super();
	}

	public Message create(final Actor sender, final Actor receiver) {
		Assert.notNull(sender);
		Assert.notNull(receiver);
		Message res;
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1000);
		res = new Message();
		res.setMoment(moment);
		res.setSender(sender);
		res.setReceiver(receiver);
		final Folder folder = this.folderService.findOUTBOX(sender);
		res.setFolder(folder);
		return res;

	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final Actor sender = message.getSender();
		final Actor receiver = message.getReceiver();
		Assert.isTrue(sender.getId() != receiver.getId());
		sender.getMessageSent().add(message);
		receiver.getMessageReceived().add(message);
		this.actorService.save(sender);
		this.actorService.save(receiver);
		Message res;
		res = this.messageRepository.save(message);
		return res;
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		final Actor actor = this.actorService.findByPrincipal();
		if (actor.getMessageReceived().contains(message) || actor.getMessageSent().contains(message)) {
			final Folder borrar = message.getFolder();
			borrar.getMessages().remove(message);
			this.folderService.save(borrar);
			this.messageRepository.delete(message);
		}
	}

	public Message findOne(final int id) {
		Message res;
		res = this.messageRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Collection<Message> allMessages() {
		Collection<Message> res;
		res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public void deleteFinish(final Message message) {
		Assert.notNull(message);
		this.messageRepository.delete(message);

	}
	public void flush(){
		messageRepository.flush();
	}

}
