
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Flout;
import domain.Manager;
import domain.Trip;

@Repository
public interface FloutRepository extends JpaRepository<Flout, Integer> {

	@Query("select (select count(t) from Trip t where t.flouts.size!=0) * 1.0 / count(t) from Trip t")
	Double findRatioTripsWithFlouts();

	@Query("select t.manager from Trip t where t.flouts.size=(select max(t.flouts.size) from Trip t)")
	Collection<Manager> findManagersWithMoreFlouts();

	@Query("select t from Trip t join t.flouts n where n.id=?1")
	Trip findTripWithThisFlout(int floutId);

	@Query("select f from Trip t join t.flouts f where t.id=?1 and (f.moment<=CURRENT_TIMESTAMP or f.moment=null)")
	Collection<Flout> findFloutsWithTripId(int tripId);

	@Query("select t from Manager m join m.trips t where m.id=?1")
	Collection<Trip> findTripsWithManagerId(int managerId);

	@Query("select n from Flout n where n.trip.manager.id=?1")
	Collection<Flout> findByManagerId(int managerId);

}
