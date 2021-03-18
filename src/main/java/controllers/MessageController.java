
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.ActorRepository;
import services.ActorService;
import services.FolderService;
import services.ManagerService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Manager;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private FolderService	folderService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private ActorRepository	actorRepository;


	public MessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {
		ModelAndView result;
		Collection<Message> messages;
		Manager mana = null;
		final Actor act = this.actorService.findByPrincipal();
		final Folder folder = this.folderService.findOne(folderId);
		Assert.isTrue(folder.getActor().equals(act));
		messages = folder.getMessages();
		if (act instanceof Manager)
			mana = this.managerService.findByPrincipal();
		result = new ModelAndView("message/list");
		result.addObject("manager", mana);
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor1;
		Actor actor2;
		Assert.notNull(actorId);
		this.actorRepository.findAll();
		actor1 = this.actorService.findOne(actorId);
		this.actorRepository.findAll();
		actor2 = this.actorService.findByPrincipal();
		Assert.notNull(actor1);
		Assert.notNull(actor2);
		final Message messag = this.messageService.create(actor2, actor1);
		result = this.createEditModelAndView(messag);
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result = null;
		final Message message = this.messageService.findOne(messageId);
		final Actor actor = this.actorService.findByPrincipal();
		try {
			if (actor instanceof Manager)
				try {
					final Manager yo = this.managerService.findByPrincipal();
					Assert.isTrue(!yo.getBanned());

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(message, "ban.commit.error");
					return result;
				}

			this.actorService.deleteMessage(message);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("messages")@Valid final Message messages, final BindingResult binding) {
		ModelAndView result = null;
		final Actor act = this.actorService.findByPrincipal();
		Assert.isTrue(messages.getSender().equals(act));

		if (binding.hasErrors())
			result = this.createEditModelAndView(messages);
		else

			try {
				if (act instanceof Manager)
					try {

						final Manager yo = this.managerService.findByPrincipal();
						Assert.isTrue(!yo.getBanned());

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(messages, "ban.commit.error");
						return result;
					}
				this.actorService.sendMessage(messages);
				result = new ModelAndView("redirect:/");
			} catch (final Exception oops) {
				result = this.createEditModelAndView(messages, "message.commit.error");
				return result;
			}
		return result;

	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int messageId) {
		ModelAndView result;
		Collection<Folder> folders;

		final Actor actor = this.actorService.findByPrincipal();
		final Message mes = this.messageService.findOne(messageId);
		Assert.isTrue(mes.getReceiver().equals(actor) || mes.getSender().equals(actor));
		folders = actor.getFolders();
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("messageId", messageId);
		result.addObject("requestURI", "message/move.do");

		return result;
	}
	@RequestMapping(value = "/defMov", method = RequestMethod.GET)
	public ModelAndView moverDef(@RequestParam final int folderId, @RequestParam final int messageId) {
		ModelAndView result = null;
		final Message message = this.messageService.findOne(messageId);
		final Folder folder = this.folderService.findOne(folderId);
		final Actor actor = this.actorService.findByPrincipal();
		try {
			if (actor instanceof Manager)
				try {
					final Manager yo = this.managerService.findByPrincipal();
					Assert.isTrue(!yo.getBanned());

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(message, "message.commit.error");
					return result;
				}
			this.actorService.moveMessage(message, folder);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message messages) {
		ModelAndView result;

		result = this.createEditModelAndView(messages, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message messages, final String message) {
		ModelAndView result;
		result = new ModelAndView("message/edit");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/edit.do");
		result.addObject("message", message);
		return result;

	}

}
