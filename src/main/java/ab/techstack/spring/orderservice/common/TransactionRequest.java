package ab.techstack.spring.orderservice.common;

import ab.techstack.spring.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private Order order;
    private Payment payment;
}
