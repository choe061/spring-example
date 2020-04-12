package jpabook.jpashop.api.dto;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class SimpleOrderDTO {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;

    public SimpleOrderDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();    // Lazy 초기화
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();  // Lazy 초기화
    }
}
