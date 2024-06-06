package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // find all orders by user
    Collection<Order> findAllByUser(User user);
}