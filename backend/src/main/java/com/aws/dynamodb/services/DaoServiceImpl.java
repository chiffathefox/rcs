package com.aws.dynamodb.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.aws.dynamodb.manager.DynamoDBManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j;

@Log4j
public class DaoServiceImpl implements DaoService {
  private static final AmazonDynamoDB dynamoDB = DynamoDBManager.dynamoDB();
  private static DaoService service;

  public static synchronized DaoService service() {

    if (service == null) {
      log.info("Instantiating service");
      service = new DaoServiceImpl();
    }

    return service;
  }

  @Override
  public List<Map<String, String>> getChangeLogs(String configurationId, String version) {
    log.debug("getChangeLogs [configurationId={" + configurationId +
        "}, version={" + version + "}]");
    final AttributeValue idValue = new AttributeValue();
    idValue.setN(configurationId);
    final AttributeValue versionValue = new AttributeValue();
    versionValue.setN(version);

    QueryRequest queryRequest = new QueryRequest()
        .withTableName("rcs-configuration-changelogs")
        .withKeyConditionExpression("id = :id AND version = :version")
        .withExpressionAttributeValues(Map.of(":id", idValue, ":version", versionValue));

    QueryResult result = dynamoDB.query(queryRequest);

    return convertItems(result.getItems());
  }

  private List<Map<String, String>> convertItems(List<Map<String, AttributeValue>> items) {
    return items.stream()
        .map(map -> map.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, this::convertAttributeValue))
        ).collect(Collectors.toList());
  }

  @Override
  public Map<String, String> createRobotConfiguration(Map<String, String> robotConfiguration) {
    return null;
  }

  @Override
  public Map<String, String> updateRobotConfiguration(Map<String, String> robotConfiguration) {
    return null;
  }

  @Override
  public void deleteRobotConfiguration(String configurationId) {

  }

  @Override
  public List<Map<String, String>> getConfigurations() {
    ScanRequest scanRequest = new ScanRequest()
        .withTableName("rcs-robot-configurations");

    ScanResult result = dynamoDB.scan(scanRequest);
    return convertItems(result.getItems());
  }

  private String convertAttributeValue(Map.Entry<String, AttributeValue> entry) {
    AttributeValue source = entry.getValue();
    if (Objects.isNull(source)) {
      return "";
    }

    if (source.getS() != null) {
      return source.getS();
    } else if (source.getBOOL() != null) {
      return source.getBOOL().toString();
    } else if (source.getN() != null) {
      return source.getN();
    }

    return "";
  }
}
