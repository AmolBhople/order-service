package ab.techstack.spring.orderservice.controller;

import ab.techstack.spring.orderservice.aop.customannotation.CustomExecutionTime;
import ab.techstack.spring.orderservice.common.Payment;
import ab.techstack.spring.orderservice.common.TransactionRequest;
import ab.techstack.spring.orderservice.common.TransactionResponse;
import ab.techstack.spring.orderservice.entity.Order;
import ab.techstack.spring.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @CustomExecutionTime
    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
        log.info("***************LOGGING USING LOMBOK SLF4J");
        log.info("******** transactions request", transactionRequest);
        return orderService.saveOrder(transactionRequest);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable int orderId) throws JsonProcessingException {

        return orderService.getOrder(orderId);
    }

}
