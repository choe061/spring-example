package jpabook.jpashop.order;

import jpabook.jpashop.common.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "delivery")
@NoArgsConstructor(access = PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 1 : 1 인 경우 비즈니스 로직에 따라 다르다. 이 경우 Order 로 부터 Delivery 를 접근하는 경우가 많기 때문에 연관 관계의 주인을 Order 로 두는 것도 좋다.
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Delivery(Address address) {
        this.address = address;
    }

    public static Delivery create(Address address) {
        return new Delivery(address);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
