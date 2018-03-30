
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import services.TagService;
import services.TripService;
import domain.ApplicationFor;
import domain.Ranger;
import domain.Stage;
import domain.Tag;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TripService		tripService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private RangerService	rangerService;


	// Constructors -----------------------------------------------------------
	public TripController() {

	}

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView listByKeyword(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Trip> trips;
		double price;

		trips = this.tripService.findAllTripsPublishedNotCancelled();
		price = this.tripService.setPriceTrip(trips);

		trips = this.tripService.findAllTripsByKeyWord(keyword);
		result = new ModelAndView("trip/list");

		result.addObject("trips", trips);
		result.addObject("price", price);
		result.addObject("requestURISearch", "trip/search.do");
		result.addObject("requestURI", "trip/search.do");
		result.addObject("showSearch", true);

		return result;
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Trip> trips;
		double price;

		trips = this.tripService.findAllTripsPublishedNotCancelled();
		price = this.tripService.setPriceTrip(trips);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("price", price);
		result.addObject("requestURISearch", "trip/search.do");
		result.addObject("requestURI", "trip/list.do");
		result.addObject("showSearch", true);
		result.addObject("showSearch", true);
		result.addObject("dontShowApply", true);

		return result;
	}

	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int categoryId) {

		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findByCategory(categoryId);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURISearch", "trip/search.do");
		result.addObject("requestURI", "trip/list.do");

		return result;
	}

	// Display ---------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		Collection<Tag> tags;
		Collection<Stage> stages;
		Collection<ApplicationFor> apply;
		Ranger ranger;

		ranger = this.rangerService.findRangerByTripId(tripId);
		tags = new ArrayList<Tag>(this.tagService.findAllTagByTripId(tripId));
		trip = this.tripService.findOne(tripId);
		stages = new ArrayList<Stage>(trip.getStages());
		apply = new ArrayList<ApplicationFor>(trip.getApplicationsFor());

		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);
		result.addObject("tags", tags);
		result.addObject("stages", stages);
		result.addObject("aplicationFor", apply);
		result.addObject("ranger", ranger);

		return result;
	}

}
