package bk.validatorexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component("beforeCreateMessageDTOValidator")
public class MessageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final var eventMessage = (MessageDTO) target;
        log.info("event message : {}", eventMessage);
        if (eventMessage.getEndAt().isBefore(eventMessage.getStartAt())) {
            errors.rejectValue("endAt", "Wrong endAt");
        }
    }
}
