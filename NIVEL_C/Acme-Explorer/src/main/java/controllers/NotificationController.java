
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NotificationService;
import services.RangerService;
import services.TripService;
import domain.Notification;

@Controller
@RequestMapping("/notification")
public class NotificationController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private RangerService		rangerService;


	// Constructors -----------------------------------------------------------
	public NotificationController() {

	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {

		ModelAndView result;
		final Collection<Notification> notifications;

		notifications = this.notificationService.findNotificationsWithTripId(tripId);

		result = new ModelAndView("notification/list");
		result.addObject("notifications", notifications);
		result.addObject("tripId", tripId);
		result.addObject("requestURI", "notification/list.do");

		return result;
	}

}
