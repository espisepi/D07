
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.ApplicationFor;
import domain.Category;
import domain.Explorer;
import domain.Flout;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Stage;
import domain.Trip;
import domain.Value;

@Service
@Transactional
public class TripService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TripRepository				tripRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService				managerService;

	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;

	@Autowired
	private StageService				stageService;

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private LegalTextService			legalTextService;


	// Constructors------------------------------------------------------------
	public TripService() {
		super();
	}

	// Simple CRUD methods-----------------------------------------------------

	public Trip create(final Manager manager) {

		Collection<ApplicationFor> applicationsFor;
		Collection<Stage> stages;
		Collection<Value> values;
		Collection<Flout> flouts;
		Trip trip;
		Ranger ranger;

		ranger = new Ranger();
		trip = new Trip();
		applicationsFor = new ArrayList<ApplicationFor>();
		stages = new ArrayList<Stage>();
		values = new ArrayList<Value>();
		flouts = new ArrayList<Flout>();

		trip.setManager(manager);
		trip.setApplicationsFor(applicationsFor);
		trip.setStages(stages);
		trip.setValues(values);
		trip.setTicker(this.generatedTicker());
		trip.setRanger(ranger);
		trip.setReasonWhy(null);
		trip.setFlouts(flouts);

		return trip;
	}

	public Trip save(final Trip trip) {
		Assert.notNull(trip);
		Trip result;
		Date dateNow;

		dateNow = new Date();
		//Continua si la fecha de empezar es anterior a la de final
		Assert.isTrue(trip.getStartDate().before(trip.getFinishDate()));
		//Continua si la fecha de publicacion es anterior a la de empezar
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartDate()));
		//Esto quiere decir que la estamos creando
		//Si no tiene reasonWhy debe de comprobar eso
		if ((trip.getReasonWhy().trim().isEmpty()) || (trip.getReasonWhy() == null))
			Assert.isTrue(trip.getPublicationDate().after(dateNow));

		result = this.tripRepository.save(trip);
		return result;
	}

	public Trip saveA(final Trip trip) {
		Assert.notNull(trip);
		Trip result;
		//Continua si la fecha de empezar es anterior a la de final
		Assert.isTrue(trip.getStartDate().before(trip.getFinishDate()));
		//Continua si la fecha de publicacion es anterior a la de empezar
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartDate()));
		result = this.tripRepository.save(trip);
		return result;
	}
	public Collection<Trip> findAll() {
		Collection<Trip> result;
		result = new ArrayList<Trip>(this.tripRepository.findAll());
		Assert.notNull(result);
		return result;
	}

	public Trip findOne(final int tripId) {
		Trip result;
		Assert.isTrue(tripId != 0);
		result = this.tripRepository.findOne(tripId);
		return result;
	}

	public void delete(final Trip trip) {
		Collection<Category> categories;
		Collection<LegalText> legalTexts;
		int tripId;
		Date dateNow;
		Manager managerT;
		Manager conectado;

		conectado = this.managerService.findByPrincipal();
		managerT = trip.getManager();
		dateNow = new Date();
		Assert.isTrue(trip.getPublicationDate().after(dateNow));
		Assert.isTrue(managerT.equals(conectado));
		tripId = trip.getId();
		legalTexts = new ArrayList<LegalText>(this.legalTextService.findAllLegalTextByTripId(tripId));
		categories = new ArrayList<Category>(this.categoryService.findAllCategoriesByTripId(tripId));

		for (final Category c : categories)
			c.getTrips().remove(trip);

		for (final LegalText l : legalTexts)
			l.getTrips().remove(trip);
		this.tripRepository.delete(trip);
	}

	public void cancel(final Trip trip) {

		Date date;
		Manager manager;

		date = new Date();
		manager = this.managerService.findByPrincipal();

		Assert.isTrue(manager.getTrips().contains(trip));
		Assert.isTrue(trip.getStartDate().after(date));
		trip.setCancelled(true);

	}
	//	// Other business methods -------------------------------------------------

	//	//Requisito 12.3
	public Collection<Trip> findAllTripsPublishedNotStarted() {
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripRepository.findAllTripsPublishedNotStarted());
		Assert.notNull(trips);
		return trips;
	}

	public Collection<Trip> findAllTripsPublishedNotCancelled() {
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripRepository.findAllTripsPublishedNotCancelled());
		Assert.notNull(trips);
		return trips;
	}

	//	//Para sacar los trips con estado ACCEPTED para que un explorer pueda cancelarlo
	public Collection<Trip> findTripsWhitStatusAcceptedNotStarted() {
		Collection<Trip> trips;
		trips = new ArrayList<Trip>(this.tripRepository.findTripsWhitStatusAcceptedNotStarted());
		Assert.notNull(trips);
		return trips;
	}

	//	//**********************************************************************************
	//	//***********************  METODO EDITAR  ******************************************
	//	//**********************************************************************************
	public Trip findOneToEdit(final int tripId) {
		Trip trip;
		Trip tripEdit;
		Manager manager;
		Date dateNow;

		dateNow = new Date();
		//Trip a editar
		trip = this.tripRepository.findOne(tripId);
		//Para que un manager edite un trip NO puede haber sido publicada
		Assert.isTrue(trip.getPublicationDate().after(dateNow));
		//Comprobamos que sea de ese Manager
		manager = this.managerService.findByPrincipal();
		Assert.isTrue(manager.getTrips().contains(trip));
		//Lo editamos
		tripEdit = this.tripRepository.save(trip);
		return tripEdit;
	}

	//	//**********************************************************************************
	//	//***********************  METODO CANCELAR  ****************************************
	//	//**********************************************************************************
	public Trip findOneToCancelManager(final int tripId) {
		this.managerService.checkPrincipal();
		Collection<Trip> allTrips;
		Trip trip;
		Trip tripEdit;
		Manager manager;
		//Trip a editar
		trip = this.tripRepository.findOne(tripId);
		//Para que un manager cancele un trip NO puede haber empezado
		allTrips = new ArrayList<>(this.tripRepository.findAllTripsNotPublished());
		Assert.isTrue(allTrips.contains(trip));
		//Comprobamos que sea de ese Manager
		manager = this.managerService.findByPrincipal();
		Assert.isTrue(manager.getTrips().contains(trip));
		tripEdit = this.tripRepository.save(trip);
		if (trip.isCancelled())
			Assert.notNull(trip.getReasonWhy());
		return tripEdit;
	}

	public Trip findOneToCancelExplorer(final int tripId) {
		this.explorerService.checkPrincipal();
		Trip trip;
		Trip tripEdit;
		Explorer explorer;
		Collection<Trip> tripsAccepted;
		Collection<Explorer> explorers;

		//Trip a editar
		trip = this.tripRepository.findOne(tripId);
		//Para que un explorer edite un trip debe de tener el estatus ACCEPTED
		tripsAccepted = new ArrayList<Trip>(this.findTripsWhitStatusAcceptedNotStarted());
		//Comprobamos que sea de ese Explorer
		Assert.isTrue(tripsAccepted.contains(trip));
		//explorer conectado
		explorer = this.explorerService.findByPrincipal();
		//Lista de explorer con ese trip
		explorers = new ArrayList<Explorer>(this.explorerService.findExplorersByTripId(tripId));
		//Vemos si el explorer conectado tiene ese trip
		Assert.isTrue(explorers.contains(explorer));
		tripEdit = this.tripRepository.save(trip);
		if (trip.isCancelled())
			Assert.notNull(trip.getReasonWhy());
		return tripEdit;
	}

	//	//Todos los Trips que apply un explorer

	public Collection<Trip> findAllTripsApplyByExplorerId(final int explorerId) {
		Collection<Trip> trips;
		trips = new ArrayList<>(this.tripRepository.findAllTripsApplyByExplorerId(explorerId));
		Assert.notNull(trips);
		return trips;
	}

	public Collection<Trip> findAllTripsNotApplyByExplorerId(final int explorerId) {
		Collection<Trip> alltrips;
		Collection<Trip> tripsOfExplorer;
		Collection<Trip> tripsForApply;
		Collection<ApplicationFor> applicationsForOfExplorer;
		Explorer explorer;

		tripsForApply = new ArrayList<Trip>();
		tripsOfExplorer = new ArrayList<Trip>();
		applicationsForOfExplorer = new ArrayList<ApplicationFor>();
		alltrips = new ArrayList<Trip>();

		explorer = this.explorerService.findOne(explorerId);
		applicationsForOfExplorer = explorer.getApplicationsFor();
		alltrips = this.tripRepository.findAll();
		tripsForApply = this.tripRepository.findAllTripsPublishedNotCancelled();

		for (final ApplicationFor a : applicationsForOfExplorer)
			tripsOfExplorer.add(a.getTrip());

		alltrips.removeAll(tripsOfExplorer);
		alltrips.retainAll(tripsForApply);
		return alltrips;

	}

	public Collection<Trip> findAllTripsNotPublished() {
		Collection<Trip> trips;
		trips = new ArrayList<>();
		Assert.notNull(trips);
		return trips;
	}

	public Collection<Trip> findAllTripsByApplicationForId(final int applicationForId) {
		Collection<Trip> res;
		res = this.tripRepository.findAllTripsByApplicationForId(applicationForId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Trip> findAllTripsByKeyWord(final String keyWord) {
		final Collection<Trip> res;
		final Page<Trip> resPage;
		int maxNumberFinder;

		maxNumberFinder = this.configurationSystemService.findOne().getMaxNumberFinder();
		final Pageable pageable = new PageRequest(0, maxNumberFinder);

		resPage = this.tripRepository.findAllTripsByKeyWord(keyWord, pageable);
		res = resPage.getContent();
		return res;
	}

	public Double findPrice(final int tripId) {
		Double price;

		price = this.tripRepository.findPrice(tripId);
		Assert.notNull(price);

		return price;
	}
	public void setPriceOfTrip(final Trip trip) {
		Double price;
		price = this.tripRepository.findPrice(trip.getId());
		trip.setPrice(price);

	}

	public void setPriceOfAllTrips() {
		Collection<Trip> trips;
		trips = this.tripRepository.findAll();

		for (final Trip t : trips)
			this.setPriceOfTrip(t);

	}

	public String generatedTicker() {

		Calendar calendar;

		calendar = Calendar.getInstance();
		String ticker;
		String dias;
		String mes;
		ticker = "";

		//ticker = String.valueOf(calendar.get(Calendar.YEAR)).substring(2) + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		dias = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (dias.length() <= 1)
			ticker = ticker + "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		else
			ticker = ticker + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		if (mes.length() <= 1)
			ticker = ticker + "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			ticker = ticker + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		ticker = ticker + String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		final char[] arr = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		String cadenaAleatoria = "";
		for (Integer i = 0; i <= 3; i++) {
			final char elegido = arr[(int) (Math.random() * 26)];
			cadenaAleatoria = cadenaAleatoria + elegido;

		}

		ticker = ticker + "-" + cadenaAleatoria;

		return ticker;
	}

	public Collection<Trip> findByCategory(final int categoryId) {

		Collection<Trip> trips;

		trips = this.tripRepository.findByCategory(categoryId);

		return trips;
	}

	public Collection<Trip> findAllTripsPublished() {
		Collection<Trip> trips;
		trips = this.tripRepository.findAllTripsPublished();
		return trips;
	}

	public double setPrice(final Collection<Stage> stages) {

		double priceTrip;
		priceTrip = 0;
		for (final Stage s : stages) {
			this.stageService.setTotalPriceStage(s);
			s.setTotalPrice(s.getTotalPrice());
			priceTrip = priceTrip + s.getTotalPrice();
		}

		return priceTrip;
	}

	public double setPriceTrip(final Collection<Trip> trips) {

		double price;

		price = 0.0;

		for (final Trip t : trips) {
			price = this.setPrice(t.getStages());
			t.setPrice(price);
		}

		return price;
	}
}
