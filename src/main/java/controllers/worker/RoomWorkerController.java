
package controllers.worker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RoomService;
import services.WorkerService;
import controllers.AbstractController;
import domain.Room;
import domain.Worker;

@Controller
@RequestMapping("/room/myhotel/worker")
public class RoomWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RoomService		roomService;
	@Autowired
	private WorkerService	workerService;


	// Constructors ----------------------------------------------------------
	public RoomWorkerController() {
		super();
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final String search = "";
		final Boolean principal = false;
		final Worker worker = this.workerService.findByPrincipal();
		Collection<Room> rooms;
		rooms = this.roomService.allRoomHotel(worker.getHotel().getId());

		result = new ModelAndView("room/list");
		result.addObject("requestURI", "room/myhotel/worker/list.do");
		result.addObject("rooms", rooms);
		result.addObject("principal", principal);
		result.addObject("search", search);
		return result;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final int search, final String searchs) {
		ModelAndView result;
		final Boolean principal = false;
		final Worker worker = this.workerService.findByPrincipal();
		Collection<Room> rooms;
		rooms = this.roomService.searchRoomWorker(worker.getHotel().getId(), search);

		result = new ModelAndView("room/list");
		result.addObject("requestURI", "room/myhotel/worker/search.do");
		result.addObject("rooms", rooms);
		result.addObject("principal", principal);
		result.addObject("search", search);
		return result;
	}

}
