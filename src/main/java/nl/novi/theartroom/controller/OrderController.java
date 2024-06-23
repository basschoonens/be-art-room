package nl.novi.theartroom.controller;

import jakarta.validation.Valid;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.service.OrderService;
import nl.novi.theartroom.service.userservice.UserService;
import nl.novi.theartroom.util.UriBuilderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<OrderOutputDto>> getOrdersForUser() {
        String username = userService.getCurrentLoggedInUsername();
        List<OrderOutputDto> orders = orderService.getOrdersForUser(username);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/user")
    public ResponseEntity<OrderOutputDto> createOrderForUser(@RequestBody @Valid OrderInputDto orderInputDto) {
        String username = userService.getCurrentLoggedInUsername();
        OrderOutputDto createdOrder = orderService.createOrderForUser(username, orderInputDto);
        URI location = UriBuilderUtil.buildUriBasedOnLongId(createdOrder.getOrderId(), "/{orderId}");
        return ResponseEntity.created(location).body(createdOrder);
    }

    // ADMIN METHODS

    @GetMapping("/admin")
    public ResponseEntity<List<OrderOutputDto>> getAllOrdersForAdmin() {
        List<OrderOutputDto> orders = orderService.getAllOrdersForAdmin();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/{orderId}")
    public ResponseEntity<OrderOutputDto> getOrderByIdForAdmin(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderByIdForAdmin(orderId));
    }

    @PutMapping("/admin/{orderId}")
    public ResponseEntity<OrderOutputDto> approveOrderForAdmin(@PathVariable Long orderId) {
        OrderOutputDto updatedOrder = orderService.approveOrderForAdmin(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/admin/{orderId}")
    public ResponseEntity<Void> deleteOrderFor(@PathVariable Long orderId) {
        orderService.deleteOrderForAdmin(orderId);
        return ResponseEntity.noContent().build();
    }

    // CRUD METHODS FOR TESTING

    @GetMapping
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<OrderOutputDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderOutputDto> createOrder(@RequestBody @Valid OrderInputDto orderInputDto) {
        OrderOutputDto createdOrder = orderService.createOrder(orderInputDto);
        URI location = UriBuilderUtil.buildUriBasedOnLongId(createdOrder.getOrderId(), "/{orderId}");
        return ResponseEntity.created(location).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderInputDto orderInputDto) {
        OrderOutputDto updatedOrder = orderService.updateOrder(orderId, orderInputDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}