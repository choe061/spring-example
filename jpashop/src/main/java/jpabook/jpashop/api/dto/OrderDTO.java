package jpabook.jpashop.api.dto;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderItem;
import jpabook.jpashop.order.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class OrderDTO {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;
    List<OrderItemDTO> orderItems;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream()
                               .map(OrderItemDTO::new)
                               .collect(toList());
    }

    @Value
    static class OrderItemDTO {
        String itemName;
        int orderPrice;
        int count;

        public OrderItemDTO(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
