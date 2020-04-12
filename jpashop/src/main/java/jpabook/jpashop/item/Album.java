package jpabook.jpashop.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@DiscriminatorValue("album")
@NoArgsConstructor(access = PROTECTED)
public class Album extends Item {
    private String artist;
    private String etc;
}
