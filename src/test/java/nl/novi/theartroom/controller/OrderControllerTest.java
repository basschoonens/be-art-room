package nl.novi.theartroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @BeforeEach
    void setUp() {
        
        Order order = new Order();
        order.setOrderNumber("ORD987654");
        order.setOrderDate("2024-06-12");
        order.setOrderStatus("Pending");
        order.setPaymentMethod("Credit Card");
        order.setTotalPrice(100.0);
        order.setName("Alice");
        order.setAddress("789 Oak St");
        order.setPostalCode("9012EF");
        order.setCity("Utrecht");

        orderRepository.save(order);
    }

    @Test
    void createOrderForUser() throws Exception {
        OrderInputDto orderInputDto = new OrderInputDto();
        orderInputDto.setOrderNumber("ORD987654");
        orderInputDto.setOrderDate("2024-06-12");
        orderInputDto.setOrderStatus("Pending");
        orderInputDto.setPaymentMethod("Credit Card");
        orderInputDto.setTotalPrice(100.0);
        orderInputDto.setName("Alice");
        orderInputDto.setAddress("789 Oak St");
        orderInputDto.setPostalCode("9012EF");
        orderInputDto.setCity("Utrecht");
        orderInputDto.setArtworkId(List.of(1L, 2L));

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInputDto)))
                .andExpect(status().isCreated());
    }
}