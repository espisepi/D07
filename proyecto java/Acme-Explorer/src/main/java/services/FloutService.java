
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloutRepository;
import domain.Flout;
import domain.Manager;
import domain.Trip;

@Service
@Transactional
public class FloutService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FloutRepository	floutRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public FloutService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Flout create() {
		Flout result;
		Manager managerPrincipal;
		String trackTrackNumber;

		result = new Flout();
		trackTrackNumber = this.generatedTrackNumber();

		managerPrincipal = this.managerService.findByPrincipal();
		Assert.notNull(managerPrincipal);
		result.setTrackNumber(trackTrackNumber);

		return result;
	}

	public String generatedTrackNumber() {

		Calendar calendar;

		calendar = Calendar.getInstance();
		String trackTrackNumber;
		String dias;
		String mes;
		trackTrackNumber = "";

		//trackTrackNumber = String.valueOf(calendar.get(Calendar.YEAR)).substring(2) + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		dias = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);

		//A�adimos las dos primeras letras
		final char[] arr = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		String cadenaAleatoria = "";
		for (Integer i = 0; i <= 1; i++) {
			final char elegido = arr[(int) (Math.random() * 26)];
			cadenaAleatoria = cadenaAleatoria + elegido;

		}
		trackTrackNumber = trackTrackNumber + cadenaAleatoria;

		if (dias.length() <= 1)
			trackTrackNumber = trackTrackNumber + "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		else
			trackTrackNumber = trackTrackNumber + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		if (mes.length() <= 1)
			trackTrackNumber = trackTrackNumber + "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		else
			trackTrackNumber = trackTrackNumber + String.valueOf(calendar.get(Calendar.MONTH) + 1);
		trackTrackNumber = trackTrackNumber + String.valueOf(calendar.get(Calendar.YEAR)).substring(2);

		cadenaAleatoria = "";
		for (Integer i = 0; i <= 1; i++) {
			final char elegido = arr[(int) (Math.random() * 26)];
			cadenaAleatoria = cadenaAleatoria + elegido;

		}

		trackTrackNumber = trackTrackNumber + cadenaAleatoria;

		return trackTrackNumber;
	}

	public Collection<Flout> findAll() {
		Collection<Flout> result;

		Assert.notNull(this.floutRepository);
		result = this.floutRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Flout findOne(final int floutId) {
		Flout result;

		result = this.floutRepository.findOne(floutId);
		Assert.notNull(result);

		return result;
	}

	public Flout save(final Flout flout) {
		Assert.notNull(flout);

		Flout result;

		Assert.isTrue((flout.getMoment() == null) || flout.getMoment().after(new Date()), "moment in past");

		result = this.floutRepository.save(flout);

		return result;
	}
	public void delete(final Flout flout) {
		Assert.notNull(flout != null);
		Assert.isTrue(flout.getId() != 0);
		Assert.isTrue(this.floutRepository.exists(flout.getId()));
		Manager managerPrincipal;

		//Comprobamos si la flout pertenece al manager logeado
		managerPrincipal = this.managerService.findByPrincipal();
		Assert.isTrue(this.findByManagerId(managerPrincipal.getId()).contains(flout));
		//Borramos la notificacion de la lista de notificaciones de Trip (No haria falta en bidireccional
		//trip.getFlouts().remove(flout);

		this.floutRepository.delete(flout);
	}

	// Other business methods------------------------------------------------------

	public Double findRatioTripsWithFlouts() {
		Double result;

		result = this.floutRepository.findRatioTripsWithFlouts();
		Assert.notNull(result);

		return result;
	}

	public Collection<Manager> findManagersWithMoreFlouts() {
		Collection<Manager> result;

		result = this.floutRepository.findManagersWithMoreFlouts();

		return result;
	}

	public Trip findTripWithThisFlout(final int floutId) {
		Trip result;

		result = this.floutRepository.findTripWithThisFlout(floutId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Flout> findFloutsWithTripId(final int tripId) {
		Collection<Flout> result;

		result = this.floutRepository.findFloutsWithTripId(tripId);

		return result;
	}

	public Collection<Trip> findTripsWithManagerId(final int managerId) {
		Collection<Trip> result;

		result = this.floutRepository.findTripsWithManagerId(managerId);

		return result;
	}

	public Collection<Flout> findByManagerId(final int managerId) {
		Collection<Flout> result;

		result = this.floutRepository.findByManagerId(managerId);

		return result;
	}
}