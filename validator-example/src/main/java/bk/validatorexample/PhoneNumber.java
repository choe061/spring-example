package bk.validatorexample;

import lombok.Getter;

import java.io.Serializable;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public final class PhoneNumber implements Serializable {
    private static final long serialVersionUID = -5477059868172998751L;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]{3}-[0-9]{3,4}-[0-9]{4}");
    private final String value;

    public PhoneNumber(final String value) {
        checkArgument(NUMBER_PATTERN.matcher(value).matches(), "invalid phone number : {}", value);
        this.value = value;
    }
}
