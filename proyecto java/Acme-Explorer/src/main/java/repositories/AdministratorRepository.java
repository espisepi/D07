
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Trip;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	@Query("select avg(t.applicationsFor.size) ,min(t.applicationsFor.size),max(t.applicationsFor.size), stddev(t.applicationsFor.size) from Trip t")
	Double[] findAvgMinMaxStddevOfTheNumOfApplicationsPerTrip();

	@Query("select avg(m.trips.size), min(m.trips.size), max(m.trips.size), stddev(m.trips.size) from Manager m")
	Double[] findAvgMinMaxStddevOfTheNumOfTripsPerManager();

	@Query("select avg(t.price), min(t.price), max(t.price), stddev(t.price) from Trip t")
	Double[] findAvgMinMaxStddevOfThePriceOfTheTrips();

	@Query("select avg(r.trips.size),min(r.trips.size),max(r.trips.size), stddev(r.trips.size) from Ranger r")
	Double[] findAvgMinMaxStddevOfTheNumTripsPerRanger();

	@Query("select (select count(a) from ApplicationFor a where a.status='PENDING') *1.0 / count(a) from ApplicationFor a")
	Double findRatOfApplicationsPending();

	@Query("select (select count(a) from ApplicationFor a where a.status='DUE') *1.0 / count(a) from ApplicationFor a")
	Double findRatioOfApplicationsDue();

	@Query("select (select count(c) from ApplicationFor c where c.status='ACCEPTED') *1.0 / count(r) from ApplicationFor r")
	Double findRatOfApplicationsAccepted();

	@Query("select (select count(c) from ApplicationFor c where c.status='CANCELLED') *1.0 / count(r) from ApplicationFor r")
	Double findRatOfApplicationsCancelled();

	@Query("select (select count(c) from Trip c where c.cancelled=true) * 1.0 / count(r) from Trip r where r.publicationDate is not null")
	Double findRatOfTheTripsCancelledvsTripsOrganised();

	@Query("select t from Trip t where t.applicationsFor.size > 1.1*(select avg(t.applicationsFor.size) from Trip t) order by t.applicationsFor.size")
	Collection<Trip> findTrips10porcentMoreApplicationsThanAvg();

	@Query("select t.title from LegalText t")
	Collection<String> titlesOfLegalText();

	@Query("select t.trips.size from LegalText t")
	Collection<Integer> findNumOfReferencesOfLegalText();

	//La query que se pide sobre el numero de referencias de cada legal text en las trips
	// se ha dividido en dos querys, estas son las dos de arriba

}
