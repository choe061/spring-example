package bk.validatorexample;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    @NotBlank
    private String name;

    @Email
    private String email;

//    @Phone
//    private String contactNumber;
    private PhoneNumber contactNumber;

    @NotNull
    @JsonFormat(shape = STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate startAt;

    @NotNull
    @JsonFormat(shape = STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate endAt;
}
