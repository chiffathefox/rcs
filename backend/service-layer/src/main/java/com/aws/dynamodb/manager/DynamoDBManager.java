package com.aws.dynamodb.manager;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoDBManager {

  private static AmazonDynamoDB client;

  public static synchronized AmazonDynamoDB dynamoDB() {

    if (client == null) {
      log.info("Instantiating mapper");

      client = AmazonDynamoDBClientBuilder.standard()
//          .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
//              "http://localhost:8000", "eu-west-1"))
          .withRegion(Regions.EU_NORTH_1)
          .build();
    }

    return client;
  }
}
