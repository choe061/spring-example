package jpabook.jpashop;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.item.Book;
import jpabook.jpashop.member.Member;
import jpabook.jpashop.order.Delivery;
import jpabook.jpashop.order.Order;
import jpabook.jpashop.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 테스트 데이터 셋
 */
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService service;

    @PostConstruct
    public void init() {
//        service.init();
//        service.init2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void init() {
            final var address = Address.builder()
                                       .city("서울")
                                       .street("1")
                                       .zipcode("111")
                                       .build();
            final var member = Member.builder()
                                     .name("userA")
                                     .address(address)
                                     .build();
            em.persist(member);

            final var book1 = Book.builder()
                                 .name("JPA1 BOOK")
                                 .price(10000)
                                 .stockQuantity(100)
                                 .build();
            em.persist(book1);
            final var book2 = Book.builder()
                                  .name("JPA2 BOOK")
                                  .price(20000)
                                  .stockQuantity(100)
                                  .build();
            em.persist(book2);

            final var orderItem1 = OrderItem.create(book1, 10000, 1);
            final var orderItem2 = OrderItem.create(book2, 20000, 2);

            final var delivery = Delivery.create(member.getAddress());
            final var order = Order.create(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void init2() {
            final var address = Address.builder()
                                       .city("경기")
                                       .street("2")
                                       .zipcode("222")
                                       .build();
            final var member = Member.builder()
                                     .name("userB")
                                     .address(address)
                                     .build();
            em.persist(member);

            final var book1 = Book.builder()
                                  .name("SPRING1 BOOK")
                                  .price(10000)
                                  .stockQuantity(100)
                                  .build();
            em.persist(book1);
            final var book2 = Book.builder()
                                  .name("SPRING2 BOOK")
                                  .price(20000)
                                  .stockQuantity(100)
                                  .build();
            em.persist(book2);

            final var orderItem1 = OrderItem.create(book1, 10000, 1);
            final var orderItem2 = OrderItem.create(book2, 20000, 2);

            final var delivery = Delivery.create(member.getAddress());
            final var order = Order.create(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
