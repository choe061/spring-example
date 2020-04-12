package bk.validatorexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
//Spring Data REST 와 함께 사용하면 beforeCreate, afterCreate 등 prefix 를 event 로 validate 메서드가 호출된다.
//@Component("beforeCreateMessageDTOValidator")
@Component
public class MessageDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var eventMessage = (MessageDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        log.info("event message : {}", eventMessage);
        if (eventMessage.getEndAt().isBefore(eventMessage.getStartAt())) {
            errors.rejectValue("endAt", "Wrong endAt");
        }
    }
}
