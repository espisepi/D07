
package controllers.manager;

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

import services.ManagerService;
import services.NotificationService;
import controllers.AbstractController;
import domain.Manager;
import domain.Notification;
import domain.Trip;

@Controller
@RequestMapping("/notification/manager_")
public class NotificationManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ManagerService		managerService;


	// Constructors -----------------------------------------------------------
	public NotificationManagerController() {

	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Notification> notifications;
		Manager managerPrincipal;

		//Se muestran las notificaciones de ese manager
		managerPrincipal = this.managerService.findByPrincipal();
		notifications = this.notificationService.findByManagerId(managerPrincipal.getId());

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		result.addObject("showEditCreateLink", true);
		result.addObject("requestURI", "notification/manager_/list.do");

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

		//Comprobamos que esa notificacion que se quiere editar pertenece al manager logueado
		Manager managerPrincipal;
		managerPrincipal = this.managerService.findByPrincipal();
		Assert.isTrue(this.notificationService.findByManagerId(managerPrincipal.getId()).contains(notification));

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
				result = new ModelAndView("redirect:/notification/manager_/list.do");
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
		Manager managerPrincipal;

		//Para hacer un select con todas las Trips de ese manager
		managerPrincipal = this.managerService.findByPrincipal();
		trips = this.notificationService.findTripsWithManagerId(managerPrincipal.getId());

		result = new ModelAndView("notification/edit");
		result.addObject("notification", notification);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "notification/manager_/edit.do");

		return result;

	}
}
