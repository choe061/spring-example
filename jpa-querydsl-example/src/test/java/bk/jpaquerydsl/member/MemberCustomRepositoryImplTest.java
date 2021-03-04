package bk.jpaquerydsl.member;

import bk.jpaquerydsl.support.AbstractJpaTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberCustomRepositoryImplTest extends AbstractJpaTestSupport {
    @Autowired
    private MemberRepository repository;

    @DisplayName("30세 미만의 선수들 페이징 조회")
    @Sql("classpath:integ.data/member.sql")
    @Test
    void findYoungerThanThirty() {
        // Given
        final var pageRequest = PageRequest.of(3, 2);
        // When
        final var youngMembers = repository.findYoungerThanThirty(pageRequest);
        // Then
        assertAll(
            () -> assertThat(youngMembers.getTotalPages()).isEqualTo(4),
            () -> assertThat(youngMembers.getTotalElements()).isEqualTo(7),
            () -> assertThat(youngMembers.isLast()).isTrue(),
            () -> assertThat(youngMembers.getContent()).hasSize(1));
    }
}
