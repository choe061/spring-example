package study.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import study.datajpa.domain.Member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {
    private final EntityManager em;
    // or MyBatis Mapper injection

    @Override
    public List<Member> findMembers() {
        return em.createQuery("select m from Member m")
                 .getResultList();
    }
}
