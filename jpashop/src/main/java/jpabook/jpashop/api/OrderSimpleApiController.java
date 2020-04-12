package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.SimpleOrderDTO;
import jpabook.jpashop.order.OrderRepository;
import jpabook.jpashop.order.OrderSearch;
import jpabook.jpashop.order.OrderService;
import jpabook.jpashop.order.simplequery.OrderSimpleQueryDTO;
import jpabook.jpashop.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * v2
     * Entity -> DTO
     * Lazy 초기화로 인해 최악의 경우 N + 1 문제 발생
     * 처음 Orders 조회 쿼리 -> 1
     * Order 내부에 있는 Member 와 Delivery 를 가져오기 위한 쿼리 -> N 개의 Member 조회 + N 개의 Delivery 조회
     * 최악의 경우(?) N + 1 문제
     *  - Lazy Loading 의 경우 DB 조회 전 영속성 컨텍스트에 조회를 요청한다. 닌
     *  - 만약 Orders 가 전부 동일한 Member 의 주문이라면 N 번의 Member 조회 중 DB 조회는 1번만 되고, 나머지는 영속성 컨텍스트에서 조회되고 쿼리는 생략된다.
     *  - 1번만 DB 조회, 나머지는 영속성 컨텍스트에서 조회된다.
     *  쿼리 결과 : 5번
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2() {
        return orderService.findOrders(new OrderSearch()).stream()
                           .map(SimpleOrderDTO::new)
                           .collect(toList());
    }

    /**
     * v3
     * Fetch Join 최적화
     * fetch join 을 사용하여 Entity 조회시 한번의 쿼리로 가져오도록 한다.
     * Lazy Loading 은 이루어지지 않는다.
     * 쿼리 결과 : 1번
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
                              .map(SimpleOrderDTO::new)
                              .collect(toList());
    }

    /**
     * v4
     * JPA 에서 바로 DTO 로 매핑하여 리턴
     * 쿼리 결과 : 1번
     *
     * 원하는 컬럼(필드) 값만 선택하여 조회할 수 있음. 성능은 최적화되지만, DTO 로 조회되어 fetch join 에 비해 유연성과 재사용성이 낮아진다.
     * 코드도 지저분해지는 단점.
     * 컬럼(필드) 가 몇개 추가된다고 성능 차이는 없지만, 트래픽이 많다면 join 하는 부분에서 성능 차이가 많이날 수 있다.
     * (대부분의 성능은 v3. fetch join 으로 해결된다)
     *
     * 사실상 API 스펙이기 때문에 재사용성이 없다고 생각하면 된다. 별도의 패키지로 만드는게 유지보수성을 올리는데 좋다.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDTO> ordersV4() {
        return orderSimpleQueryRepository.findOrdersDTO();
    }

    /**
     * 성능 최적화 순서
     * 1. (v2) Entity 를 DTO 로 변환하는 방법 (Lazy Loading 이슈가 존재)
     * 2. (v3) fetch join -> 여기서 대부분의 성능 이슈가 해결된다
     * 3. (v4) DTO 로 직접 조회하는 방법
     * 4. 최후의 방법으로 JPA 가 제공하는 네이티브 SQL 또는 스프링 JDBC Template 으로 직접 SQL 사용
     */
}
