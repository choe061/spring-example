package jpabook.jpashop.api.dto;

import lombok.Value;

@Value
public class UpdateMemberResponse {
    Long id;
    String name;
}
