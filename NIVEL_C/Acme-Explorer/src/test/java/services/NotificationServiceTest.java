
package services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Notification;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NotificationServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private NotificationService	notificationService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;

	@PersistenceContext
	private EntityManager		entityManager;


	// Test -----------------------------------------------
	@Test
	public void testSave() {
		super.authenticate("explorer1");

		Notification result;
		Explorer explorerPrincipal;

		explorerPrincipal = this.explorerService.findByPrincipal();
		result = this.notificationService.create();
		result.setGauge(3);
		result.setTrip(explorerPrincipal.getApplicationsFor().iterator().next().getTrip());
		result = this.notificationService.save(result);
		this.entityManager.flush();
		//Compruebo si se cumple la navegabilidad en ambas partes
		Assert.isTrue(explorerPrincipal.getApplicationsFor().iterator().next().getTrip().getNotifications().contains(result));
	}

	@Test
	public void testDelete() {
		super.authenticate("explorer1");

		this.notificationService.delete(this.notificationService.findOne(super.getEntityId("notification1")));
		this.entityManager.flush();
	}

	@Test
	public void testDeleteTripWithNotifications() {
		super.authenticate("explorer1");
		Trip tripDelete;
		Date publicationDateAfterNow;

		publicationDateAfterNow = new Date();
		publicationDateAfterNow.setMonth(5);
		tripDelete = this.tripService.findOne(super.getEntityId("trip1"));
		tripDelete.setPublicationDate(publicationDateAfterNow);
		this.tripService.delete(tripDelete);
		this.entityManager.flush();
		//El siguiente codigo falla porque salta el Assert.notNull del findOne porque ha desaparecido la notificacion al borrar la trip que la tiene, todo perfecto
		//this.notificationService.findOne(super.getEntityId("notification1"));

	}
}
