package nl.novi.theartroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.service.OrderService;
import nl.novi.theartroom.service.userservice.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "Alice", roles = "USER")
    public void createOrderForUserIntegrationTest() throws Exception {
        // Mock user service to return a fixed username
        when(userService.getCurrentLoggedInUsername()).thenReturn("Alice");

        // Mock order service to return a fixed OrderOutputDto
        OrderOutputDto mockOrderOutputDto = new OrderOutputDto();
        mockOrderOutputDto.setOrderId(1L);
        mockOrderOutputDto.setOrderNumber("ORD987654");
        mockOrderOutputDto.setOrderDate("2024-06-12");
        mockOrderOutputDto.setOrderStatus("Pending");
        mockOrderOutputDto.setPaymentMethod("Credit Card");
        mockOrderOutputDto.setTotalPrice(100.0);
        mockOrderOutputDto.setName("Alice");
        mockOrderOutputDto.setAddress("789 Oak St");
        mockOrderOutputDto.setPostalCode("9012EF");
        mockOrderOutputDto.setCity("Utrecht");

        when(orderService.createOrderForUser(any(String.class), any(OrderInputDto.class))).thenReturn(mockOrderOutputDto);

        // Create an OrderInputDto for the request body
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

        // Perform POST request to create an order
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInputDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNumber").value("ORD987654"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value("Pending"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethod").value("Credit Card"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alice"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("789 Oak St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("9012EF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Utrecht"));
    }
}