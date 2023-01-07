package exercise.repository;

import exercise.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // BEGIN
    
    // END

    Optional<User> findByEmail(String email);
}
