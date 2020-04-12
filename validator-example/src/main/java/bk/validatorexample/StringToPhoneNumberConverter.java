package bk.validatorexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToPhoneNumberConverter implements Converter<String, PhoneNumber> {
    @Override
    public PhoneNumber convert(final String source) {
        return new PhoneNumber(source);
    }
}
