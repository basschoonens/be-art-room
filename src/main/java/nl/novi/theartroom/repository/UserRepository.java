package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
//    Optional<User> findByUsername(String username);

}
