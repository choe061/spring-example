package jpabook.jpashop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicationMember(member);
        repository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return repository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return repository.findOne(memberId);
    }

    private void validateDuplicationMember(Member member) {
        //
        final var members = repository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("Already exist member name");
        }
    }

    @Transactional
    public void update(Long memberId, String name) {
        final var member = repository.findOne(memberId);
        member.rename(name);
    }
}
