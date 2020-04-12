package jpabook.jpashop.api;

import jpabook.jpashop.api.dto.CreateMemberRequest;
import jpabook.jpashop.api.dto.CreateMemberResponse;
import jpabook.jpashop.api.dto.MemberDTO;
import jpabook.jpashop.api.dto.Result;
import jpabook.jpashop.api.dto.UpdateMemberRequest;
import jpabook.jpashop.api.dto.UpdateMemberResponse;
import jpabook.jpashop.member.Member;
import jpabook.jpashop.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse create(@RequestBody @Valid CreateMemberRequest request) {
        final var member = Member.builder()
                                 .name(request.getName())
                                 .build();
        return new CreateMemberResponse(memberService.join(member));
    }

    @PutMapping("/api/v1/members/{id}")
    public UpdateMemberResponse update(@PathVariable("id") Long memberId, @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(memberId, request.getName());
        final var member = memberService.findOne(memberId);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @GetMapping("/api/v1/members")
    public Result<List<MemberDTO>> getMembers() {
        final var members = memberService.findMembers().stream()
                                         .map(member -> new MemberDTO(member.getName()))
                                         .collect(toList());
        return new Result<>(members);
    }
}
