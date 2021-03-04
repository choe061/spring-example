package bk.jpaquerydsl.team;

import java.util.Optional;

public interface TeamCustomRepository {
    Optional<Team> findTeamOfMemberWithoutRelationship(Long memberId);

    Optional<Team> findTeamOfMemberWithRelationship(Long memberId);
}
