
package controllers.explorer;

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

import services.ExplorerService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Notification;
import domain.Trip;

@Controller
@RequestMapping("/notification/explorer")
public class NotificationExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ExplorerService		explorerService;


	// Constructors -----------------------------------------------------------
	public NotificationExplorerController() {

	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Notification> notifications;
		Explorer explorerPrincipal;

		//Se muestran las notificaciones de ese explorer
		explorerPrincipal = this.explorerService.findByPrincipal();
		notifications = this.notificationService.findByExplorerId(explorerPrincipal.getId());

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		result.addObject("showEditCreateLink", true);
		result.addObject("requestURI", "notification/explorer/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Notification notification;

		notification = this.notificationService.create();
		result = this.createEditModelAndView(notification);

		return result;
	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int notificationId) {

		ModelAndView result;
		Notification notification;

		notification = this.notificationService.findOne(notificationId);
		Assert.notNull(notification);

		//Comprobamos que esa notificacion que se quiere editar pertenece al explorer logueado
		Explorer explorerPrincipal;
		explorerPrincipal = this.explorerService.findByPrincipal();
		Assert.isTrue(this.notificationService.findByExplorerId(explorerPrincipal.getId()).contains(notification));

		result = this.createEditModelAndView(notification);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Notification notification, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(notification);
		else
			try {
				this.notificationService.save(notification);
				result = new ModelAndView("redirect:/notification/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(notification, "notification.commit.error");
			}

		return result;

	}

	//Delete ---------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Notification notification, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(notification);
		else
			try {
				this.notificationService.delete(notification);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(notification, "notification.commit.error");
			}
		return result;
	}

	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final Notification notification) {

		Assert.notNull(notification);
		ModelAndView result;
		result = this.createEditModelAndView(notification, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Notification notification, final String messageCode) {
		Assert.notNull(notification);

		ModelAndView result;
		Collection<Trip> trips;
		Explorer explorerPrincipal;

		//Para hacer un select con todas las Trips de ese explorer
		explorerPrincipal = this.explorerService.findByPrincipal();
		trips = this.notificationService.findTripsWithExplorerId(explorerPrincipal.getId());

		result = new ModelAndView("notification/edit");
		result.addObject("notification", notification);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "notification/explorer/edit.do");

		return result;

	}
}
