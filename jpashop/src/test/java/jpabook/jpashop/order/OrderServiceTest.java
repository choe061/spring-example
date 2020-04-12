package jpabook.jpashop.order;

import jpabook.jpashop.common.Address;
import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.ItemRepository;
import jpabook.jpashop.item.NotEnoughStockException;
import jpabook.jpashop.member.Member;
import jpabook.jpashop.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderServiceTest {
    private OrderService service;
    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        this.orderRepository = mock(OrderRepository.class);
        this.memberRepository = mock(MemberRepository.class);
        this.itemRepository = mock(ItemRepository.class);
        this.service = new OrderService(orderRepository, memberRepository, itemRepository);
    }

    @Test
    void order() {
        // Given
        final var memberId = 1L;
        final var address = Address.builder()
                                   .city("city")
                                   .street("street")
                                   .zipcode("zipcode")
                                   .build();
        final var member = Member.builder()
                                 .id(memberId)
                                 .name("bk")
                                 .address(address)
                                 .build();
        final var itemId = 2L;
        final var item = Book.builder()
                             .id(itemId)
                             .name("itemName")
                             .price(1000)
                             .stockQuantity(4)
                             .build();
        final var count = 3;
        given(memberRepository.findOne(memberId)).willReturn(member);
        given(itemRepository.findOne(itemId)).willReturn(item);
        // When
        service.order(memberId, itemId, count);
        // Then
        verify(memberRepository).findOne(memberId);
        verify(itemRepository).findOne(itemId);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 시 재고가 부족한 경우, NotEnoughStockException 발생")
    void not_enough_stock() {
        // Given
        final var memberId = 1L;
        final var address = Address.builder()
                                   .city("city")
                                   .street("street")
                                   .zipcode("zipcode")
                                   .build();
        final var member = Member.builder()
                                 .id(memberId)
                                 .name("bk")
                                 .address(address)
                                 .build();
        final var itemId = 2L;
        final var item = Book.builder()
                             .id(itemId)
                             .name("itemName")
                             .price(1000)
                             .stockQuantity(4)
                             .build();
        final var count = 5;
        given(memberRepository.findOne(memberId)).willReturn(member);
        given(itemRepository.findOne(itemId)).willReturn(item);
        // When Then
        assertThatThrownBy(() -> service.order(memberId, itemId, count))
                .isExactlyInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void cancelOrder() {
    }
}
