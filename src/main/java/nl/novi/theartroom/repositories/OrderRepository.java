package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Order;
import nl.novi.theartroom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // find all orders by user
    Collection<Order> findAllByUser(User user);
}