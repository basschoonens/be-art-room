package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
