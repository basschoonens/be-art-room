package nl.novi.theartroom.repositories;

import nl.novi.techiteasy1121.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
