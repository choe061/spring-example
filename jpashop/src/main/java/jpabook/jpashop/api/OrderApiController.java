package jpabook.jpashop.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.api.dto.OrderDTO;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderRepository;
import jpabook.jpashop.order.OrderSearch;
import jpabook.jpashop.order.query.OrderFlatDTO;
import jpabook.jpashop.order.query.OrderQueryDTO;
import jpabook.jpashop.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * v1
     * 엔티티 직접 반환
     * 사용하지 말것! 예제일뿐
     * 양방향 관계가 있는 경우 JsonIgnore 하지 않는 경우 무한 루프에 빠짐. 근데 결국 엔티티는 직접 반환하지 않아야한다.
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        final var orders = orderRepository.findAllByName(new OrderSearch());
        // Lazy Loading 때문에 강제 호출해준다.
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            final var orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO> ordersV2() {
        return orderRepository.findAllByName(new OrderSearch()).stream()
                              .map(OrderDTO::new)
                              .collect(toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO> ordersV3(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                   @RequestParam(value = "limit", defaultValue = "100") int limit) {
        return orderRepository.findAllWithMemberDelivery(offset, limit).stream()
                              .map(OrderDTO::new)
                              .collect(toList());
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDTO> ordersV5() {
        return orderQueryRepository.findOrders_Optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDTO> ordersV6() {
        return orderQueryRepository.findOrders_Flat();
    }
}
