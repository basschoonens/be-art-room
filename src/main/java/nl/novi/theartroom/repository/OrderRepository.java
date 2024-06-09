package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserUsername(String username);

}