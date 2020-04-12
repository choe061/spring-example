package bk.validatorexample;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@NotBlank
public @interface Phone {
    String message() default "wrong phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
