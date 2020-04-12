package jpabook.jpashop.order.simplequery;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.order.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OrderSimpleQueryDTO {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;

    public OrderSimpleQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;    // Lazy 초기화
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;  // Lazy 초기화
    }
}
