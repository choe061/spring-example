package jpabook.jpashop.api.dto;

import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
public class UpdateMemberRequest {
    @NotEmpty
    String name;
}
