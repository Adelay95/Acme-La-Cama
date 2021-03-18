
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository	folderRepository;
	@Autowired
	private MessageService		messageService;
	@Autowired
	private ActorService		actorService;


	public FolderService() {
		super();
	}

	public Folder create(final Actor actor) {
		Assert.notNull(actor);
		Folder res;
		final Collection<Message> messages = new ArrayList<Message>();
		res = new Folder();
		res.setMessages(messages);
		res.setActor(actor);
		return res;

	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		Folder res;
		if (folder.getId() == 0) {
			final Actor actor = folder.getActor();
			actor.getFolders().add(folder);
			this.actorService.save(actor);
		}
		res = this.folderRepository.save(folder);
		return res;
	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);
		for (final Message m : folder.getMessages())
			this.messageService.delete(m);
		this.folderRepository.delete(folder);

	}

	public Folder findOne(final int id) {
		Folder res;
		res = this.folderRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Folder> allFolders() {
		Collection<Folder> res;
		res = this.folderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Folder findINBOX(final Actor actor) {
		Assert.notNull(actor);
		final Folder folder = this.folderRepository.getINBOXByActorId("INBOX", actor.getId());
		return folder;

	}
	public Folder findOUTBOX(final Actor actor) {
		Assert.notNull(actor);
		final Folder folder = this.folderRepository.getOUTBOXByActorId("OUTBOX", actor.getId());
		return folder;

	}

	public Folder findREPORT(final Actor actor) {
		Assert.notNull(actor);
		final Folder folder = this.folderRepository.getOUTBOXByActorId("REPORTS", actor.getId());
		return folder;
	}
	public void flush(){
		folderRepository.flush();
	}
	

}
