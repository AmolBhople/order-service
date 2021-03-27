package ab.techstack.spring.orderservice.service;

import ab.techstack.spring.orderservice.aop.customannotation.CustomExecutionTime;
import ab.techstack.spring.orderservice.common.Payment;
import ab.techstack.spring.orderservice.common.TransactionRequest;
import ab.techstack.spring.orderservice.common.TransactionResponse;
import ab.techstack.spring.orderservice.entity.Order;
import ab.techstack.spring.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_DO_PAYMENT_ENDPOINT;

    @CustomExecutionTime
    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {

        logger.info("OrderService request{} ", new ObjectMapper().writeValueAsString(transactionRequest));
        String response = "";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = restTemplate.postForObject(PAYMENT_DO_PAYMENT_ENDPOINT, payment, Payment.class);

        logger.info("OrderService paymentResponse from Payment-Service {} ", new ObjectMapper().writeValueAsString(paymentResponse));

        response = "Success".equalsIgnoreCase(paymentResponse.getPaymentStatus())? "Payment and Order successful":"Payment Failed and Order pending";

        orderRepository.save(order);


        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response );
    }

    public Order getOrder(int id){
        return orderRepository.findById(id).get();
    }

}
