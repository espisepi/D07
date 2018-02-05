
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import domain.Manager;
import domain.Notification;
import domain.Trip;

@Service
@Transactional
public class NotificationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NotificationRepository	notificationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService			managerService;


	// Constructors -----------------------------------------------------------

	public NotificationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Notification create() {
		Notification result;
		Date moment;
		Manager managerPrincipal;
		String ticker;

		result = new Notification();
		moment = new Date();
		ticker = this.generatedTicker();

		managerPrincipal = this.managerService.findByPrincipal();
		Assert.notNull(managerPrincipal);
		result.setMoment(moment);
		result.setTicker(ticker);

		return result;
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

	public Collection<Notification> findAll() {
		Collection<Notification> result;

		Assert.notNull(this.notificationRepository);
		result = this.notificationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Notification findOne(final int notificationId) {
		Notification result;

		result = this.notificationRepository.findOne(notificationId);
		Assert.notNull(result);

		return result;
	}

	public Notification save(final Notification notification) {
		final Date moment;
		Assert.notNull(notification);

		Notification result;

		if (notification.getId() == 0) {
			//Solo se cambia el moment la primera vez que se crea, si se actualiza no se cambia su moment
			moment = new Date(System.currentTimeMillis() - 1000);
			notification.setMoment(moment);
			//trip.getNotifications().add(notification);

		}

		result = this.notificationRepository.save(notification);

		return result;
	}
	public void delete(final Notification notification) {
		Assert.notNull(notification != null);
		Assert.isTrue(notification.getId() != 0);
		Assert.isTrue(this.notificationRepository.exists(notification.getId()));
		Trip trip;
		Manager managerPrincipal;

		trip = this.findTripWithThisNotification(notification.getId());
		//Comprobamos si la notification pertenece al manager logeado
		managerPrincipal = this.managerService.findByPrincipal();
		Assert.isTrue(trip.getManager().equals(managerPrincipal));
		//Borramos la notificacion de la lista de notificaciones de Trip (No haria falta en bidireccional
		//trip.getNotifications().remove(notification);

		this.notificationRepository.delete(notification);
	}

	// Other business methods------------------------------------------------------

	public Double findRatioTripsWithNotifications() {
		Double result;

		result = this.notificationRepository.findRatioTripsWithNotifications();
		Assert.notNull(result);

		return result;
	}

	public Collection<Manager> findManagersWithMoreNotifications() {
		Collection<Manager> result;

		result = this.notificationRepository.findManagersWithMoreNotifications();

		return result;
	}

	public Trip findTripWithThisNotification(final int notificationId) {
		Trip result;

		result = this.notificationRepository.findTripWithThisNotification(notificationId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Notification> findNotificationsWithTripId(final int tripId) {
		Collection<Notification> result;

		result = this.notificationRepository.findNotificationsWithTripId(tripId);

		return result;
	}

	public Collection<Trip> findTripsWithManagerId(final int managerId) {
		Collection<Trip> result;

		result = this.notificationRepository.findTripsWithManagerId(managerId);

		return result;
	}

	public Collection<Notification> findByManagerId(final int managerId) {
		Collection<Notification> result;

		result = this.notificationRepository.findByManagerId(managerId);

		return result;
	}
}
