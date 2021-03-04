package bk.dynamodb.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("aws.credentials")
public class AwsCredentialsProperties {
    private final String accessKey;
    private final String secretKey;
}
