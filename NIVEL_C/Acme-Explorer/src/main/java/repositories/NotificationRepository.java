
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Explorer;
import domain.Notification;
import domain.Trip;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select (select count(t) from Trip t where t.notifications.size!=0) * 1.0 / count(t) from Trip t")
	Double findRatioTripsWithNotifications();

	@Query("select e from Explorer e")
	Collection<Explorer> findExplorersWithMoreNotifications();

	@Query("select t from Trip t join t.notifications n where n.id=?1")
	Trip findTripWithThisNotification(int notificationId);

	@Query("select t.notifications from Trip t where t.id=?1")
	Collection<Notification> findNotificationsWithTripId(int tripId);

	@Query("select a.trip from Explorer e join e.applicationsFor a where e.id=?1")
	Collection<Trip> findTripsWithExplorerId(int explorerId);

	@Query("select n from ApplicationFor a join a.trip.notifications n where a.explorer.id=?1")
	Collection<Notification> findByExplorerId(int explorerId);

}
