package bk.validatorexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public MessageDTOValidator beforeCreateEventMessageValidator() {
        return new MessageDTOValidator();
    }
}
