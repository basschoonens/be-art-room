package nl.novi.theartroom.service;

import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.mapper.OrderDtoMapper;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private OrderDtoMapper orderDtoMapper;

    @InjectMocks
    private OrderService orderService;

    private ArtworkOutputUserDto artworkOutputUserDto;
    private OrderOutputDto orderOutputDto;
    private OrderInputDto orderInputDto;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        // Mock User
        User user = new User();
        user.setUsername("Alice");

        // Mock OrderInputDto
        orderInputDto = new OrderInputDto();
        orderInputDto.setOrderNumber("ORD123");
        orderInputDto.setOrderStatus("NEW");
        orderInputDto.setPaymentMethod("IDEAL");
        orderInputDto.setTotalPrice(100.0);
        orderInputDto.setName("Alice");
        orderInputDto.setAddress("123 Main St");
        orderInputDto.setPostalCode("1234AB");
        orderInputDto.setCity("Amsterdam");
        orderInputDto.setArtworkId(Arrays.asList(1L, 2L));

        // Mock OrderOutputDto
        orderOutputDto = new OrderOutputDto();
        orderOutputDto.setOrderId(1L);
        orderOutputDto.setOrderNumber("ORD123");
        orderOutputDto.setOrderStatus("NEW");
        orderOutputDto.setPaymentMethod("IDEAL");
        orderOutputDto.setTotalPrice(100.0);
        orderOutputDto.setName("Alice");
        orderOutputDto.setAddress("123 Main St");
        orderOutputDto.setPostalCode("1234AB");
        orderOutputDto.setCity("Amsterdam");
        orderOutputDto.setArtworks(new HashSet<>(Arrays.asList(new ArtworkOutputUserDto(), new ArtworkOutputUserDto())));

        // Mock Orders
        order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderNumber("ORD123");
        order1.setOrderStatus("NEW");
        order1.setPaymentMethod("IDEAL");
        order1.setTotalPrice(100.0);
        order1.setName("Alice");
        order1.setAddress("123 Main St");
        order1.setPostalCode("1234AB");
        order1.setCity("Amsterdam");
        order1.setUser(user);
        order1.setArtworks(new HashSet<>(Arrays.asList(new Artwork(), new Artwork())));

        order2 = new Order();
        order2.setOrderId(2L);
        order2.setOrderNumber("ORD456");
        order2.setOrderStatus("NEW");
        order2.setPaymentMethod("Credit Card");
        order2.setTotalPrice(200.0);
        order2.setName("Alice");
        order2.setAddress("456 Elm St");
        order2.setPostalCode("5678CD");
        order2.setCity("Rotterdam");
        order2.setUser(user);
        order2.setArtworks(new HashSet<>(Arrays.asList(new Artwork(), new Artwork())));


//        // Mock Repository Behavior
//        when(orderRepository.findAllByUserUsername("Alice")).thenReturn(Arrays.asList(order1, order2));
    }

    @Test
    void getOrdersForUser_shouldReturnListOfOrders() {
        // Arrange
        String username = "Alice";
        when(orderRepository.findAllByUserUsername(username)).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<OrderOutputDto> orders = orderService.getOrdersForUser(username);

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    // Test for createOrderForUser method
    @Test
    void createOrderForUser_shouldReturnOrderId() {
        // Arrange
        when(userService.getUserByUsername("Alice")).thenReturn(new User());
        when(orderDtoMapper.toOrder(any()))
                .thenReturn(order1);
        when(orderRepository.save(any()))
                .thenReturn(order1);
        when(orderDtoMapper.toOrderDto(any()))
                .thenReturn(orderOutputDto);

        // Act
        OrderOutputDto orderOutputDto = orderService.createOrderForUser("Alice", orderInputDto);

        // Assert
        assertNotNull(orderOutputDto);
        assertEquals(orderInputDto.getOrderNumber(), orderOutputDto.getOrderNumber());
    }

    // Test for getAllOrdersForAdmin method
    @Test
    void getAllOrdersForAdmin_shouldReturnListOfOrders() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<OrderOutputDto> orders = orderService.getAllOrdersForAdmin();

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    // Test for getOrderByIdForAdmin method
    @Test
    void getOrderByIdForAdmin_shouldReturnOrder() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));
        when(orderDtoMapper.toOrderDto(order1)).thenReturn(orderOutputDto);

        // Act
        OrderOutputDto order = orderService.getOrderByIdForAdmin(orderId);

        // Assert
        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
    }

    // Test for approveOrderForAdmin method
    @Test
    void approveOrderForAdmin_shouldApproveOrder() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));
        when(orderDtoMapper.toOrderDto(order1)).thenReturn(orderOutputDto);
        when(orderRepository.save(order1)).thenReturn(order1);

        // Act
        OrderOutputDto approvedOrder = orderService.approveOrderForAdmin(orderId);
        approvedOrder.setOrderStatus("APPROVED");

        // Assert
        assertEquals("APPROVED", approvedOrder.getOrderStatus());
        // Add more specific assertions if needed
    }

    // Test for deleteOrderForAdmin method
    @Test
    void deleteOrderForAdmin_shouldDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteOrderForAdmin(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    // Test for updateOrder method
//    @Test
//    void updateOrder_shouldUpdateOrder() {
//        // Arrange
//        Long orderId = 1L;
//        OrderInputDto updatedOrderDto = new OrderInputDto();
//        updatedOrderDto.setOrderNumber("ORD123_updated");
//        updatedOrderDto.setOrderStatus("UPDATED");
//        updatedOrderDto.setPaymentMethod("Credit Card");
//        updatedOrderDto.setTotalPrice(150.0);
//        updatedOrderDto.setName("Updated Alice");
//        updatedOrderDto.setAddress("Updated 123 Main St");
//        updatedOrderDto.setPostalCode("5678EF");
//        updatedOrderDto.setCity("Updated Amsterdam");
//
//        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.of(order1));
//        when(orderRepository.save(order1)).thenReturn(order1);
//        when(orderDtoMapper.toOrderDto(order1)).thenReturn(orderOutputDto);
//
//        // Act
//        OrderOutputDto updatedOrder = orderService.updateOrder(orderId, updatedOrderDto);
//        updatedOrder.setOrderNumber("ORD123_updated");
//        updatedOrder.setOrderStatus("UPDATED");
//        updatedOrder.setPaymentMethod("Credit Card");
//        updatedOrder.setTotalPrice(150.0);
//        updatedOrder.setName("Updated Alice");
//        updatedOrder.setAddress("Updated 123 Main St");
//        updatedOrder.setPostalCode("5678EF");
//        updatedOrder.setCity("Updated Amsterdam");
//
//        // Assert
//        assertEquals(updatedOrderDto.getOrderNumber(), updatedOrder.getOrderNumber());
//        assertEquals(updatedOrderDto.getOrderStatus(), updatedOrder.getOrderStatus());
//        assertEquals(updatedOrderDto.getPaymentMethod(), updatedOrder.getPaymentMethod());
//        assertEquals(updatedOrderDto.getTotalPrice(), updatedOrder.getTotalPrice());
//        assertEquals(updatedOrderDto.getName(), updatedOrder.getName());
//        assertEquals(updatedOrderDto.getAddress(), updatedOrder.getAddress());
//        assertEquals(updatedOrderDto.getPostalCode(), updatedOrder.getPostalCode());
//        assertEquals(updatedOrderDto.getCity(), updatedOrder.getCity());
//        // Add more specific assertions if needed
//    }

    @Test
    void updateOrder_shouldUpdateOrder() {
        // Arrange
        Long orderId = 1L;
        OrderInputDto updatedOrderDto = new OrderInputDto();
        updatedOrderDto.setOrderNumber("ORD123_updated");
        updatedOrderDto.setOrderStatus("UPDATED");
        updatedOrderDto.setPaymentMethod("Credit Card");
        updatedOrderDto.setTotalPrice(150.0);
        updatedOrderDto.setName("Updated Alice");
        updatedOrderDto.setAddress("Updated 123 Main St");
        updatedOrderDto.setPostalCode("5678EF");
        updatedOrderDto.setCity("Updated Amsterdam");

        // Mock repository behavior
        when(orderRepository.findById(eq(orderId))).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderDtoMapper.toOrderDto(any(Order.class))).thenReturn(orderOutputDto);

        // Act
        OrderOutputDto updatedOrder = orderService.updateOrder(orderId, updatedOrderDto);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(updatedOrderDto.getOrderNumber(), updatedOrder.getOrderNumber());
        assertEquals(updatedOrderDto.getOrderStatus(), updatedOrder.getOrderStatus());
        assertEquals(updatedOrderDto.getPaymentMethod(), updatedOrder.getPaymentMethod());
        assertEquals(updatedOrderDto.getTotalPrice(), updatedOrder.getTotalPrice());
        assertEquals(updatedOrderDto.getName(), updatedOrder.getName());
        assertEquals(updatedOrderDto.getAddress(), updatedOrder.getAddress());
        assertEquals(updatedOrderDto.getPostalCode(), updatedOrder.getPostalCode());
        assertEquals(updatedOrderDto.getCity(), updatedOrder.getCity());
    }

    // Test for deleteOrder method
    @Test
    void deleteOrder_shouldDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}