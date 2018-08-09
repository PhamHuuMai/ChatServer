package mta.is.maiph.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import mta.is.maiph.entity.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MaiPH
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailAndPassword(String email, String originalPassword);
    User findByEmail(String email);
    
}
