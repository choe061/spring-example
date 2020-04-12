package jpabook.jpashop.order.query;

import java.time.LocalDateTime;
import java.util.List;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.order.OrderStatus;
import lombok.Data;

@Data
public class OrderQueryDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDTO> orderItems;

    public OrderQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryDTO> orderItems) {
        this.orderId = orderId;
        this.name = name;    // Lazy 초기화
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;  // Lazy 초기화
        this.orderItems = orderItems;
    }

}
