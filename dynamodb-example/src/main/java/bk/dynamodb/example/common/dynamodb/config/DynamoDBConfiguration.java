package bk.dynamodb.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

@Configuration
public class DynamoDBConfiguration {
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider(final AwsCredentialsProperties awsCredentialsProperties) {
        final var awsCredentials = new BasicAWSCredentials(awsCredentialsProperties.getAccessKey(),
                                                           awsCredentialsProperties.getSecretKey());
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    @Bean
    public AwsClientBuilder.EndpointConfiguration endpointConfiguration(final AwsDynamoDBProperties awsDynamoDBProperties) {
        return new AwsClientBuilder.EndpointConfiguration(awsDynamoDBProperties.getEndpoint(), awsDynamoDBProperties.getRegion());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(final AWSCredentialsProvider awsCredentialsProvider,
                                         final AwsClientBuilder.EndpointConfiguration endpointConfiguration) {
        return AmazonDynamoDBClientBuilder.standard()
                                          .withCredentials(awsCredentialsProvider)
                                          .withEndpointConfiguration(endpointConfiguration)
                                          .build();
    }

    @Primary
    @Bean
    public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT);
    }
}
