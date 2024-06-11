package nl.novi.theartroom.service;

import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderService orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
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


            order2 = new Order();
            order2.setOrderId(2L);
            order2.setOrderNumber("ORD456");
            order2.setOrderStatus("NEW");
            order2.setPaymentMethod("Credit Card");
            order2.setTotalPrice(200.0);
            order2.setName("Bob");
            order2.setAddress("456 Elm St");
            order2.setPostalCode("5678CD");
            order2.setCity("Rotterdam");

            when(orderRepository.findAllByUserUsername(anyString())).thenReturn(Arrays.asList(order1, order2));
            when(orderRepository.findById(eq(1L))).thenReturn(Optional.of(order1));
            when(orderRepository.findById(eq(2L))).thenReturn(Optional.of(order2));
        }

    @Test
    void getOrdersForUser_shouldReturnListOfOrders() {
        // Arrange


        // Act


        // Assert
    }

    @Test
    void createOrderForUser_shouldReturnOrderId() {
        // Your test logic here
    }

    @Test
    void getAllOrdersForAdmin_shouldReturnListOfOrders() {
        // Your test logic here
    }

    @Test
    void getOrderByIdForAdmin_shouldReturnOrder() {
        // Your test logic here
    }

    @Test
    void approveOrderForAdmin_shouldApproveOrder() {
        // Your test logic here
    }

    @Test
    void deleteOrderForAdmin_shouldDeleteOrder() {
        // Your test logic here
    }

    @Test
    void getAllOrders_shouldReturnListOfOrders() {
        // Your test logic here
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        // Your test logic here
    }

    @Test
    void createOrder_shouldReturnOrderId() {
        // Your test logic here
    }

    @Test
    void updateOrder_shouldUpdateOrder() {
        // Your test logic here
    }

    @Test
    void deleteOrder_shouldDeleteOrder() {
        // Your test logic here
    }
}