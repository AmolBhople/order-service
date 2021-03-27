package ab.techstack.spring.orderservice.controller;

import ab.techstack.spring.orderservice.common.Payment;
import ab.techstack.spring.orderservice.common.TransactionRequest;
import ab.techstack.spring.orderservice.common.TransactionResponse;
import ab.techstack.spring.orderservice.entity.Order;
import ab.techstack.spring.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bookOrder() throws Exception {

        TransactionResponse transactionResponse=new TransactionResponse();

        TransactionRequest transactionRequest = new TransactionRequest();

        Order order = new Order();
        transactionRequest.setOrder(order);
        Payment payment = new Payment();
        transactionRequest.setPayment(payment);

  

        Mockito.when(orderService.saveOrder(transactionRequest)).thenReturn(transactionResponse);
        String json = mapper.writeValueAsString(transactionRequest);


        mockMvc.perform(post("/order/bookOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("orderName")));


    }

    @Test
    void getOrder() throws Exception {
        Order order = new Order();
        order.setId(1);
        order.setName("orderName");
        order.setPrice(12.3d);
        order.setQty(5);
        Mockito.when(orderService.getOrder(1)).thenReturn(order);

        mockMvc.perform(get("/order/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("orderName")));
    }


}