package jpabook.jpashop.api.dto;

import lombok.Value;

@Value
public class Result<T> {
    T data;
}
