
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.ActorRepository;
import services.ActorService;
import services.FolderService;
import services.ManagerService;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Manager;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private ManagerService	managerService;
	@Autowired
	private ActorRepository	actorRepository;


	public FolderController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		this.actorRepository.findAll();
		final Actor actor = this.actorService.findByPrincipal();
		final Folder folder = this.folderService.create(actor);
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Folder> folders;
		Manager mana = null;
		final Actor actor = this.actorService.findByPrincipal();
		if (actor instanceof Manager)
			mana = this.managerService.findByPrincipal();
		folders = actor.getFolders();
		result = new ModelAndView("folder/list");
		result.addObject("manager", mana);
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;
		final Actor actor = this.actorService.findByPrincipal();
		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		Assert.isTrue(folder.getActor().equals(actor));
		result = this.createEditModelAndView(folder);
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		ModelAndView result;

		final Folder folder = this.folderService.findOne(folderId);
		if ((this.actorService.findByPrincipal() instanceof Administrator) && folder.getName().equals("REPORTS"))
			result = this.createEditModelAndView(folder, "folder.name.commit.error");
		else if (folder.getName().equals("INBOX") || folder.getName().equals("OUTBOX"))
			result = this.createEditModelAndView(folder, "folder.commit.error");
		else
			try {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result = null;
		final int actorillo = folder.getActor().getId();
		final Collection<String> fol = this.actorService.todasFoldersActor(actorillo);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(folder.getActor().equals(actor));
		if ((actor instanceof Administrator) && folder.getName().equals("REPORTS"))
			result = this.createEditModelAndView(folder, "folder.name.commit.error");
		else if (folder.getName().equals("INBOX") || folder.getName().equals("OUTBOX") || fol.contains(folder.getName()))
			result = this.createEditModelAndView(folder, "folder.name.commit.error");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				if (actor instanceof Manager)
					try {
						final Manager yo = this.managerService.findByPrincipal();
						Assert.isTrue(!yo.getBanned());

					} catch (final Throwable oops) {
						result = this.createEditModelAndView(folder, "ban.commit.error");
						return result;
					}

				if (folder.getId() != 0) {
					final Folder folder2 = this.folderService.findOne(folder.getId());
					Assert.isTrue(!folder2.getName().equals("INBOX") && !folder2.getName().equals("OUTBOX") && !folder2.getName().equals("REPORTS"));

				}
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}

		return result;

	}
	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folde, final String message) {
		ModelAndView result;
		result = new ModelAndView("folder/edit");
		result.addObject("folder", folde);
		result.addObject("requestURI", "folder/edit.do");
		result.addObject("message", message);
		return result;

	}

}
