
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

import services.FloutService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Flout;
import domain.Manager;
import domain.Trip;

@Controller
@RequestMapping("/flout/manager_")
public class FloutManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FloutService	floutService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------
	public FloutManagerController() {

	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Flout> flouts;
		Manager managerPrincipal;

		//Se muestran las notificaciones de ese manager
		managerPrincipal = this.managerService.findByPrincipal();
		flouts = this.floutService.findByManagerId(managerPrincipal.getId());

		result = new ModelAndView("flout/list");
		result.addObject("flouts", flouts);
		result.addObject("showEditCreateLink", true);
		result.addObject("requestURI", "flout/manager_/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Flout flout;

		flout = this.floutService.create();
		result = this.createEditModelAndView(flout);

		return result;
	}

	//Editing-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int floutId) {

		ModelAndView result;
		Flout flout;

		flout = this.floutService.findOne(floutId);
		Assert.notNull(flout);

		//Comprobamos que esa notificacion que se quiere editar pertenece al manager logueado
		Manager managerPrincipal;
		managerPrincipal = this.managerService.findByPrincipal();
		Assert.isTrue(this.floutService.findByManagerId(managerPrincipal.getId()).contains(flout));

		result = this.createEditModelAndView(flout);

		return result;

	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Flout flout, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(flout);
		else
			try {
				this.floutService.save(flout);
				result = new ModelAndView("redirect:/flout/manager_/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("moment in past"))
					result = this.createEditModelAndView(flout, "flout.commit.past.error");
				else
					result = this.createEditModelAndView(flout, "flout.commit.error");
			}

		return result;

	}

	//Delete ---------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Flout flout, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(flout);
		else
			try {
				this.floutService.delete(flout);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(flout, "flout.commit.error");
			}
		return result;
	}

	//auxiliary------------------

	protected ModelAndView createEditModelAndView(final Flout flout) {

		Assert.notNull(flout);
		ModelAndView result;
		result = this.createEditModelAndView(flout, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Flout flout, final String messageCode) {
		Assert.notNull(flout);

		ModelAndView result;
		Collection<Trip> trips;
		Manager managerPrincipal;

		//Para hacer un select con todas las Trips de ese manager
		managerPrincipal = this.managerService.findByPrincipal();
		trips = this.floutService.findTripsWithManagerId(managerPrincipal.getId());

		result = new ModelAndView("flout/edit");
		result.addObject("flout", flout);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);
		result.addObject("RequestURI", "flout/manager_/edit.do");

		return result;

	}
}
