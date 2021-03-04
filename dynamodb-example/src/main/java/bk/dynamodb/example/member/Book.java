package bk.dynamodb.example.member;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import bk.dynamodb.example.common.dynamodb.converter.LocalDateTimeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "member")
public class Musics {
    @DynamoDBHashKey(attributeName = "")
    @DynamoDBTyped(DynamoDBAttributeType.S)
    private String partitionKey;
    @DynamoDBRangeKey(attributeName = "")
    @DynamoDBTyped(DynamoDBAttributeType.S)
    private String sortKey;

    @DynamoDBAttribute(attributeName = "nm")
    @DynamoDBTyped(DynamoDBAttributeType.S)
    private String name;

    @DynamoDBAttribute(attributeName = "crt_dt")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBTyped(DynamoDBAttributeType.N)
    private LocalDateTime createdAt;

    @DynamoDBAttribute(attributeName = "udt_dt")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBTyped(DynamoDBAttributeType.N)
    private LocalDateTime updatedAt;
}
