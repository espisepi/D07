
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FloutService;
import services.RangerService;
import services.TripService;
import domain.Flout;

@Controller
@RequestMapping("/flout")
public class FloutController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private FloutService	floutService;

	@Autowired
	private RangerService		rangerService;


	// Constructors -----------------------------------------------------------
	public FloutController() {

	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {

		ModelAndView result;
		final Collection<Flout> flouts;

		flouts = this.floutService.findFloutsWithTripId(tripId);

		result = new ModelAndView("flout/list");
		result.addObject("flouts", flouts);
		result.addObject("tripId", tripId);
		result.addObject("requestURI", "flout/list.do");

		return result;
	}

}
