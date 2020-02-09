package bk.asyncexample;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailSentResultDTO {
    private int memberNumber;
    private boolean result;

    public boolean isSuccess() {
        return result;
    }
}
