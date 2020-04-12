package jpabook.jpashop.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MemberServiceTest {
    private MemberService service;
    private MemberRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = mock(MemberRepository.class);
        this.service = new MemberService(repository);
    }

    @Test
    void join() {
        // Given
        Member member = Member.builder()
                              .name("bk")
                              .build();
        given(repository.findByName("bk")).willReturn(List.of());
        // When
        service.join(member);
        // Then
        verify(repository).findByName("bk");
        verify(repository).save(member);
    }

    @Test
    void throwExJoin() {
        // Given
        Member member = Member.builder()
                              .name("bk")
                              .build();
        Member member2 = Member.builder()
                               .name("bk")
                               .build();
        given(repository.findByName("bk")).willReturn(List.of(member2));
        // When
        // Then
        assertThatThrownBy(() -> service.join(member)).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
