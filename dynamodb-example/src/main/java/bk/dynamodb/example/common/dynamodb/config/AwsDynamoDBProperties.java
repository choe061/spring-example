package bk.dynamodb.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("aws.dynamodb")
public class AwsDynamoDBProperties {
    private final String endpoint;
    private final String region;
}
