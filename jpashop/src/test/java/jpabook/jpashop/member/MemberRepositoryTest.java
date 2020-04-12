package jpabook.jpashop.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private EntityManager em;

    @Test
    public void save() {
        // Given
        final Member member = Member.builder()
                                    .name("member A")
                                    .build();
        // When
        repository.save(member);
        final var savedId = member.getId();
        final var findMember = repository.findOne(savedId);
        // Then
        em.flush();
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }
}
