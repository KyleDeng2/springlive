package net.nvsoftware.OrderService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.OrderService.client.PaymentServiceFeignClient;
import net.nvsoftware.OrderService.client.ProductServiceFeignClient;
import net.nvsoftware.OrderService.entity.OrderEntity;
import net.nvsoftware.OrderService.model.OrderRequest;
import net.nvsoftware.OrderService.model.OrderResponse;
import net.nvsoftware.OrderService.model.PaymentRequest;
import net.nvsoftware.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;

    @Override
    public long placeOrder(OrderRequest orderRequest) { //TODO: transactions
        log.info("Start: OrderService placeOrder");
        // use OrderService to create OrderEntity with status CREATED, ORM JPA save to database
        OrderEntity orderEntity = OrderEntity.builder()
                .productId(orderRequest.getProductId())
                .quantity((orderRequest.getQuantity()))
                .totalAmount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .build();
        orderRepository.save(orderEntity);
        log.info("Process: OrderService placeOrder save orderEntity with orderId: " + orderEntity.getId());

        // call ProductService to check product quantity, if ok, reduce it, else throw not enough
        productServiceFeignClient.reduceQuantity(orderEntity.getProductId(), orderEntity.getQuantity());
        log.info("Process: OrderService placeOrder FeignCall ProductService reduceQuantity");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderEntity.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .totalAmount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentServiceFeignClient.doPayment(paymentRequest);
            orderStatus = "PAID";
            log.info("Process: OrderService placeOrder FeignCall PaymentService doPayment PAID");
        } catch (Exception e) {
            orderStatus = "PAYMENT_FALIDED";
            log.info("Process: OrderService placeOrder FeignCall PaymentService doPayment PAID");
        }

        orderEntity.setOrderStatus(orderStatus);
        orderRepository.save(orderEntity);

        // call PaymentService to charge, if success, mark order PAID, else CANCELLED
        log.info("End: OrderService placeOrder Done with orderId: " + orderEntity.getId());
        return orderEntity.getId();
    }

    @Override
    public OrderResponse getOrderDetailById(long orderId) {
        log.info("Start: OrderService getOrderDeatilById");
        OrderEntity orderEntity = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("OrderService getOrderDetailById: Order Not Found with id: " + orderId));
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(orderEntity.getId())
                .orderStatus(orderEntity.getOrderStatus())
                .orderDate(orderEntity.getOrderDate())
                .totalAmount(orderEntity.getTotalAmount())
                .build();
        log.info("End: OrderService getOrderDeatilById");
        return orderResponse;
    }
}
