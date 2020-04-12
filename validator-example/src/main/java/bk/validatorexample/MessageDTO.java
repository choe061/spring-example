package bk.validatorexample;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class EventMessageDTO {
    @NotBlank
    private String name;

    @Email
    private String writerUserEmail;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
    private String contactNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate startAt;

    @NotNull
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate endAt;
}
