package net.nvsoftware.OrderService;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OrderServiceTestConfig {
    @Bean
    public ServiceinstanceListSupplierTest supplier() {
        return new ServiceinstanceListSupplierTest();
    }
}
