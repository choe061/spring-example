package bk.jpaquerydsl.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static bk.jpaquerydsl.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> findYoungerThanThirty(final Pageable pageable) {
        final var members = queryFactory.select(member)
                                        .from(member)
                                        .where(member.age.lt(30))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .fetch();
        final var countQuery = queryFactory.select(member)
                                           .from(member)
                                           .where(member.age.lt(30));
        /**
         * 세번째 인자의 () -> countQuery.fetchCount() 는 최적화되어 호출되지 않는 경우도 있다.
         *  - 마지막 페이지의 경우
         *  - 첫 페이지면서 fetch 하는 content 가 page request 의 size 보다 작은 경우
         * 하지만 이런 구조는 카운트만 캐싱하기는 어렵기 때문에 카운트를 구하는 쿼리만 아예 분리하도록.
         */
        return PageableExecutionUtils.getPage(members, pageable, countQuery::fetchCount);
    }
}
