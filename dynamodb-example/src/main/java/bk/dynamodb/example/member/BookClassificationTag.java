package bk.dynamodb.example.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClassificationTag {
    FICTION(100, "소설"),
    SCIENCE(200, "과학"),
    HUMANITIES(300, "인문학"),
    COMPUTER(400, "컴퓨터");

    private final int code;
    private final String title;
}
