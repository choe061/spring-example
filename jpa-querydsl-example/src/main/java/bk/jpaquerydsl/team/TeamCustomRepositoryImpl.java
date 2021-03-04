package bk.jpaquerydsl.team;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static bk.jpaquerydsl.member.QMember.member;
import static bk.jpaquerydsl.team.QTeam.team;
import static bk.jpaquerydsl.team.QTeamMember.teamMember;

@Repository
@RequiredArgsConstructor
public class TeamCustomRepositoryImpl implements TeamCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Team> findTeamOfMemberWithoutRelationship(final Long memberId) {
        return Optional.ofNullable(queryFactory.select(team)
                                               .from(team)
                                               .join(member)
                                               .on(team.id.eq(member.team_id))
                                               .where(member.id.eq(memberId))
                                               .fetchOne());
    }

    @Override
    public Optional<Team> findTeamOfMemberWithRelationship(final Long memberId) {
        return Optional.ofNullable(queryFactory.select(teamMember)
                                               .from(teamMember)
                                               .join(teamMember.team, team).fetchJoin()
                                               .where(teamMember.id.eq(memberId))
                                               .fetchOne())
                       .map(TeamMember::getTeam);
    }
}
