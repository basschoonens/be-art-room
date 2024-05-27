package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}