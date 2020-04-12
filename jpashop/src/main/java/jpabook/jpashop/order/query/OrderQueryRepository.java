package jpabook.jpashop.order.query;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDTO> findOrders() {
        var orders = findOrdersDTO();
        orders.forEach(o -> {
            var orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return orders;
    }

    /**
     * v5
     * 쿼리 2번
     * 일대다 관계인 컬렉션
     * List orderItems 를 IN 절을 사용해서 미리 조회하여 메모리에 올린다
     */
    public List<OrderQueryDTO> findOrders_Optimization() {
        List<OrderQueryDTO> orders = findOrders();
        List<Long> orderIds = toOrderIds(orders);
        List<OrderItemQueryDTO> orderItems = findOrderItemMap(orderIds);
        Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream()
                                                                    .collect(groupingBy(OrderItemQueryDTO::getOrderId));

        orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));
        return orders;
    }

    /**
     * v6
     * 쿼리 1번이지만, 1:N 을 JOIN 하여 중복 데이터 발생
     * 오히려 쿼리 1번이여도 v5 보다 느릴 수도 있다.
     * 애플리케이션 추가 작업이 너무 크다.
     * 페이징 불가능
     */
    public List<OrderFlatDTO> findOrders_Flat() {
        return em.createQuery(
            "select new jpabook.jpashop.order.query.OrderFlatDTO(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"
                + " from Order o"
                + " join o.member m"
                + " join o.delivery d"
                + " join o.orderItems oi"
                + " join oi.item i", OrderFlatDTO.class)
                 .getResultList();
    }

    private List<OrderItemQueryDTO> findOrderItemMap(List<Long> orderIds) {
        return em.createQuery(
                "select new jpabook.jpashop.order.query.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)"
                    + " from OrderItem oi"
                    + " join oi.item i"
                    + " where oi.order.id in :orderIds", OrderItemQueryDTO.class)
                               .setParameter("orderIds", orderIds)
                               .getResultList();
    }

    private List<Long> toOrderIds(List<OrderQueryDTO> orders) {
        return orders.stream()
                                 .map(OrderQueryDTO::getOrderId)
                                 .collect(toList());
    }

    private List<OrderItemQueryDTO> findOrderItems(Long orderId) {
        return em.createQuery(
            "select new jpabook.jpashop.order.query.OrderItemQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count) "
                + " from OrderItem oi"
                + " join oi.item i"
                + " where oi.order.id = :orderId", OrderItemQueryDTO.class)
                 .setParameter("orderId", orderId)
                 .getResultList();
    }

    private List<OrderQueryDTO> findOrdersDTO() {
        return em.createQuery(
            "select new jpabook.jpashop.order.query.OrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address, )"
                + " from Order o"
                + " join o.member m"
                + " join o.delivery d", OrderQueryDTO.class)
                 .getResultList();
    }
}
