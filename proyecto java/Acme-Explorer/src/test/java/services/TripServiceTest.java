
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ApplicationFor;
import domain.Category;
import domain.Explorer;
import domain.Manager;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------
	@Autowired
	private TripService				tripService;

	//	// Supporting services ----------------------------------------------------

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;
	@Autowired
	private ApplicationForService	applicationForService;
	@Autowired
	private CategoryService			categoryService;


	@Autowired
	@Test
	public void testCreate() {
		this.authenticate("manager1");
		Trip result;
		Manager manager;
		manager = this.managerService.findByPrincipal();
		result = this.tripService.create(manager);
		Assert.notNull(result);
	}

	@Test
	public void testFindAll() {
		final Collection<Trip> result = this.tripService.findAll();
		Assert.notEmpty(result);
	}

	@Test
	public void testSave() {

		Trip tripBD;
		final Trip trip5 = this.tripService.findOne(this.getEntityId("trip1"));
		trip5.setDescription("Descripcion editada");
		trip5.setReasonWhy("hola");
		trip5.setCancelled(true);
		tripBD = this.tripService.save(trip5);
		Assert.notNull(tripBD);
	}

	@Test
	public void testFindOne() {
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip1"));
		Assert.notNull(trip);
	}

	@Test
	public void testDelete() {
		this.authenticate("manager5");
		final Trip trip5 = this.tripService.findOne(this.getEntityId("trip5"));
		this.tripService.delete(trip5);

		this.authenticate(null);
	}
	// Other business test methods -------------------------------------------------

	@Test
	public void testFindAllTripsPublishedNotStarted() {
		this.authenticate("manager1");
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripService.findAllTripsPublishedNotStarted());
		Assert.notNull(trips);
		this.authenticate(null);
	}

	@Test
	public void testFindTripsWhitStatusAccepted() {
		this.authenticate("explorer1");
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripService.findTripsWhitStatusAcceptedNotStarted());
		Assert.notNull(trips);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		this.authenticate("manager5");
		Trip trip;
		Trip tripEdit;
		trip = this.tripService.findOne(super.getEntityId("trip5"));
		trip.setDescription("Nueva descripción");
		tripEdit = this.tripService.findOneToEdit(trip.getId());
		Assert.notNull(tripEdit);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToCancelManager() {
		this.authenticate("manager1");
		Trip trip;

		trip = this.tripService.findOne(super.getEntityId("trip2"));
		trip.setCancelled(true);
		trip.setReasonWhy("Aforo sobrepasa el limite");
		this.tripService.findOneToCancelManager(trip.getId());
		Assert.notNull(trip);
		this.authenticate(null);
	}

	@Test
	public void testFindOneToCancelExplorer() {
		this.authenticate("explorer3");
		Trip trip;
		Trip tripEdit;
		trip = this.tripService.findOne(super.getEntityId("trip3"));
		trip.setCancelled(true);
		trip.setReasonWhy("No hay fondos");
		tripEdit = this.tripService.findOneToCancelExplorer(trip.getId());
		Assert.notNull(tripEdit);
		this.authenticate(null);
	}

	@Test
	public void testFindAllTripsApplyByExplorerId() {
		Collection<Trip> trips;
		Explorer explorer;
		explorer = this.explorerService.findOne(super.getEntityId("explorer1"));
		trips = new ArrayList<Trip>(this.tripService.findAllTripsApplyByExplorerId(explorer.getId()));
		Assert.notNull(trips);
	}

	@Test
	public void testFindAllTripsNotPublished() {
		Collection<Trip> trips;
		trips = new ArrayList<>(this.tripService.findAllTripsNotPublished());
		Assert.notNull(trips);
	}

	@Test
	public void testFindAllTripsByKeyWord() {
		Collection<Trip> trips;
		String search;
		search = "description";
		trips = new ArrayList<>(this.tripService.findAllTripsByKeyWord(search));
		Assert.notNull(trips);
	}

	@Test
	public void testFindPrice() {
		Trip trip;
		trip = this.tripService.findOne(super.getEntityId("trip1"));
		this.tripService.findPrice(trip.getId());
	}
	@Test
	public void testSetPriceOfTrip() {
		Trip trip;
		trip = this.tripService.findOne(super.getEntityId("trip1"));

		this.tripService.setPriceOfTrip(trip);
		Assert.isTrue(trip.getPrice() >= 0.0);

	}

	@Test
	public void testGeneratedTicker() {
		String ticker;
		ticker = this.tripService.generatedTicker();
		Assert.notNull(ticker);
	}

	@Test
	public void testCategory() {
		Collection<Trip> trips;

		trips = this.tripService.findByCategory(5364);
		Assert.notNull(trips);

	}

	@Test
	public void testPrice() {

		Trip trip;
		double price;

		trip = this.tripService.findOne(super.getEntityId("trip1"));

		price = this.tripService.setPrice(trip.getStages());

		Assert.isTrue(price > 0.0);
	}

	@Test
	public void testCancel() {
		Trip trip;
		this.authenticate("manager1");

		trip = this.tripService.findOne(super.getEntityId("trip2"));
		trip.setCancelled(true);
		trip.setReasonWhy("porque si");
		this.tripService.cancel(trip);
		Assert.notNull(trip);
	}

	@Test
	public void testFindAllTripsNotApplyByExplorerId() {
		Collection<Trip> trips;
		Explorer explorer;

		explorer = this.explorerService.findOne(super.getEntityId("explorer1"));
		trips = this.tripService.findAllTripsNotApplyByExplorerId(explorer.getId());

		Assert.notNull(trips);

	}

	@Test
	public void testFindAllTripsByApplicationForId() {
		Collection<Trip> trips;
		ApplicationFor apply;
		apply = this.applicationForService.findOne(super.getEntityId("applicationFor1"));
		trips = new ArrayList<Trip>(this.tripService.findAllTripsByApplicationForId(apply.getId()));
		Assert.notNull(trips);
	}

	@Test
	public void testSetPriceOfAllTrips() {
		Collection<Trip> result;
		result = new ArrayList<Trip>(this.tripService.findAll());
		this.tripService.setPriceOfAllTrips();
		Assert.notNull(result);
	}

	@Test
	public void testFindByCategory() {
		Collection<Trip> result;
		Category category;

		category = this.categoryService.findOne(super.getEntityId("lake"));
		result = new ArrayList<Trip>(this.tripService.findByCategory(category.getId()));
		Assert.notNull(result);
	}

}
