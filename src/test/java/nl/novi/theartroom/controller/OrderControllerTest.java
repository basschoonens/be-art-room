//package nl.novi.theartroom.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//class OrderControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    void createOrderForUser() {
//
//        String requestJson = """
//                {
//                    "orderDate" : "Gibson gitaar",
//                    "orderStatus" : 2399.00,
//                    "paymentMethod" :  "IDEAL",
//                    "totalPrice" :  5000,
//
//                }
//                """;
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.post("/orders")
//                .contentType(APPLICATION_JSON)
//                .content(requestJson))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//    }
//}

// security kun je wel mocken.
// andere classes niet mocken.