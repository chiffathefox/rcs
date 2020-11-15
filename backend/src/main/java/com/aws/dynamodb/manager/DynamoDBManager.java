package com.aws.dynamodb.manager;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoDBManager {

  private static DynamoDB dynamoDB;

  public static synchronized DynamoDB dynamoDB() {

    if (dynamoDB == null) {
      log.info("Instantiating mapper");

      AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//          .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
//              "http://localhost:8000", "eu-west-1"))
          .withRegion(Regions.EU_WEST_2)
          .build();

      dynamoDB = new DynamoDB(client);
    }

    return dynamoDB;
  }
}
