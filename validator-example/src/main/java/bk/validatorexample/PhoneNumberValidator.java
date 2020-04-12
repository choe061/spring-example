package bk.validatorexample;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import java.util.regex.Pattern;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<Phone, String> {
   private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]{3}-[0-9]{3,4}-[0-9]{4}");

   public void initialize(Phone constraint) {
   }

   public boolean isValid(@NotBlank String phoneField, ConstraintValidatorContext context) {
      log.info("invalid phone number : {}", phoneField);
      return NUMBER_PATTERN.matcher(phoneField).matches();
   }
}
