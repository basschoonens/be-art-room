package nl.novi.theartroom.controller;

import jakarta.validation.Valid;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.service.OrderService;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // USER METHODS

    @GetMapping("/user")
    public List<OrderOutputDto> getOrdersForUser() {
        String username = userService.getCurrentLoggedInUsername();
        return orderService.getOrdersForUser(username);
    }

    @PostMapping("/user")
    public ResponseEntity<OrderOutputDto> createOrderForUser(@RequestBody @Valid OrderInputDto orderInputDto) {
        String username = userService.getCurrentLoggedInUsername();
        OrderOutputDto createdOrder = orderService.createOrderForUser(username, orderInputDto);
        return ResponseEntity.ok(createdOrder);
    }

    // ADMIN METHODS

    @GetMapping("/admin")
    public List<OrderOutputDto> getAllOrdersForAdmin() {
        return orderService.getAllOrdersForAdmin();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<OrderOutputDto> getOrderByIdForAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByIdForAdmin(id));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<OrderOutputDto> approveOrderForAdmin(@PathVariable Long id) {
        OrderOutputDto updatedOrder = orderService.approveOrderForAdmin(id);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteOrderFor(@PathVariable Long id) {
        orderService.deleteOrderForAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // CRUD METHODS FOR TESTING

    @GetMapping
    public List<OrderOutputDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderOutputDto> createOrder(@RequestBody @Valid OrderInputDto orderInputDto) {
        OrderOutputDto createdOrder = orderService.createOrder(orderInputDto);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderInputDto orderInputDto) {
        OrderOutputDto updatedOrder = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}