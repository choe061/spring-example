package jpabook.jpashop.order;

import jpabook.jpashop.item.ItemRepository;
import jpabook.jpashop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        final var member = memberRepository.findOne(memberId);
        final var item = itemRepository.findOne(itemId);

        final var delivery = Delivery.create(member.getAddress());

        final var orderItem = OrderItem.create(item, item.getPrice(), count);
        // order 의 delivery 와 orderItem 필드에 Cascade.ALL 설정으로 인해 생명주기를 함께 하여 order 를 저장하여도 모두 같이 저장된다.
        // Aggregate root 와 life cycle 이 동일한 자식 관계에서 사용
        final var order = Order.create(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        final var order = orderRepository.findOne(orderId);
        order.cancel(); // JPA 의 dirty check 로 인해 entity 의 변경내용이 영속성 컨텍스트에 반영이 되고, DB 에 까지 저장이 된다.
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
