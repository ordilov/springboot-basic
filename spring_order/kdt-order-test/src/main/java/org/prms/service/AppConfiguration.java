package org.prms.service;

import org.prms.controller.Order;
import org.prms.repository.MemoryRepository;
import org.prms.repository.OrderRepository;
import org.prms.domain.Voucher;
import org.prms.repository.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

// Bean을 정의할 도면이다라고 스프링에게 알려줘야 함. @Configuration
// 각 메소드에 Bean 어노테이션 사용
@Configuration
public class AppConfiguration {

    // Command 과제
//    public outPutService()


    @Bean
    public VoucherRepository voucherRepository() {
//        return new VoucherRepository() {
//            @Override
//            public Optional<Voucher> findById(UUID voucherId) {
//                return Optional.empty();
//            }
//        };

        return new MemoryRepository();
    }
    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }
    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService,orderRepository);
    }

}