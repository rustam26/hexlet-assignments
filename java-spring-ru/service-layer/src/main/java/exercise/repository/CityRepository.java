package exercise.repository;

import exercise.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    // BEGIN
    List<City> findByNameIsContainingIgnoreCase(String name);

    @Query("select c from City c order by c.name")
    List<City> getAll();
    // END
}
