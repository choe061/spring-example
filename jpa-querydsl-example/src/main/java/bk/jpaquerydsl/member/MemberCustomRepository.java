package bk.jpaquerydsl.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {
    Page<Member> findYoungerThanThirty(Pageable pageable);
}
