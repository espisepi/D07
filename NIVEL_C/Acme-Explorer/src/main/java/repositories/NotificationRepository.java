
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Notification;
import domain.Trip;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query("select (select count(t) from Trip t where t.notifications.size!=0) * 1.0 / count(t) from Trip t")
	Double findRatioTripsWithNotifications();

	@Query("select m from Manager m")
	Collection<Manager> findManagersWithMoreNotifications();

	@Query("select t from Trip t join t.notifications n where n.id=?1")
	Trip findTripWithThisNotification(int notificationId);

}
