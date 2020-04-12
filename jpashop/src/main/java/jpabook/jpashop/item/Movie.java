package jpabook.jpashop.item;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@DiscriminatorValue("movie")
@NoArgsConstructor(access = PROTECTED)
public class Movie extends Item {
    private String director;
    private String actor;
}
