package bk.jpaquerydsl.team;

import bk.jpaquerydsl.support.AbstractJpaTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TeamCustomRepositoryImplTest extends AbstractJpaTestSupport {
    @Autowired
    private TeamRepository repository;

    @DisplayName("손흥민이 속한 Team 조회 - 연관관계 X")
    @SqlGroup({
                  @Sql("classpath:integ.data/team.sql"),
                  @Sql("classpath:integ.data/member.sql")
    })
    @Test
    void findTeamOfMemberWithoutRelationship() {
        // Given
        final var sonMemberId = 1L;
        // When
        final var team = repository.findTeamOfMemberWithoutRelationship(sonMemberId);
        // Then
        assertAll(
            () -> assertThat(team).isPresent(),
            () -> assertThat(team.get().getName()).isEqualTo("토트넘"));
    }

    @DisplayName("손흥민이 속한 Team 조회 - 연관관계 O")
    @SqlGroup({
                  @Sql("classpath:integ.data/team.sql"),
                  @Sql("classpath:integ.data/team-member.sql")
              })
    @Test
    void findTeamOfMemberWithRelationship() {
        // Given
        final var sonMemberId = 1L;
        // When
        final var team = repository.findTeamOfMemberWithRelationship(sonMemberId);
        // Then
        assertAll(
            () -> assertThat(team).isPresent(),
            () -> assertThat(team.get().getName()).isEqualTo("토트넘"));
    }
}
