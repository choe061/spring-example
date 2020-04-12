package jpabook.jpashop.order.query;

import lombok.Value;

@Value
public class OrderItemQueryDTO {
    Long orderId;
    String itemName;
    int orderPrice;
    int count;
}
