package com.aws.dynamodb.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.aws.dynamodb.manager.DynamoDBManager;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j;

@Log4j
public class DaoServiceImpl implements DaoService {
  private static final AmazonDynamoDB dynamoDB = DynamoDBManager.dynamoDB();
  private static final String doubleRegExp =
      "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
          "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}" +
          "+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
  private static final Pattern pattern = Pattern.compile(doubleRegExp);
  public static final String ROBOT_CONFIGURATION_CHANGELOGS_TABLE = "rcs-configuration-changelogs";
  public static final String ROBOT_CONFIGURATION_TABLE = "rcs-robot-configurations";
  public static final String VERSION_ATTRIBUTE = "version";

  private static DaoService service;

  public static synchronized DaoService service() {

    if (service == null) {
      log.info("Instantiating service");
      service = new DaoServiceImpl();
    }

    return service;
  }

  @Override
  public Map<String, Object> getChangeLog(String configurationId, String version) {
    log.debug("getChangeLogs [configurationId={" + configurationId +
        "}, version={" + version + "}]");
    final AttributeValue idValue = new AttributeValue();
    idValue.setN(configurationId);
    final AttributeValue versionValue = new AttributeValue();
    versionValue.setN(version);

    QueryRequest queryRequest = new QueryRequest()
        .withTableName(ROBOT_CONFIGURATION_CHANGELOGS_TABLE)
        .withKeyConditionExpression("id = :id AND version = :version")
        .withExpressionAttributeValues(Map.of(":id", idValue, ":version", versionValue));

    QueryResult result = dynamoDB.query(queryRequest);

    return convertItem(result.getItems().get(0));
  }

  @Override
  public Map<String, Object> createRobotConfiguration(Map<String, Object> robotConfiguration) {
    final Map<String, AttributeValue> stringAttributeValueMap = convertToItem(robotConfiguration);

    long id = new Date().getTime();
    AttributeValue idN = new AttributeValue();
    idN.setN(String.valueOf(id));
    stringAttributeValueMap.put("id", idN);

    AttributeValue version = new AttributeValue();
    version.setN(String.valueOf(1));
    stringAttributeValueMap.put(VERSION_ATTRIBUTE, version);

    dynamoDB.putItem(ROBOT_CONFIGURATION_TABLE, stringAttributeValueMap);

    dynamoDB.putItem(ROBOT_CONFIGURATION_CHANGELOGS_TABLE, stringAttributeValueMap);

    return convertItem(stringAttributeValueMap);
  }

  private Map<String, AttributeValue> convertToItem(Map<String, Object> items) {
    return items.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, this::convertToAttributeValue));
  }

  private AttributeValue convertToAttributeValue(Map.Entry<String, Object> entry) {
    Object source = entry.getValue();
    AttributeValue attributeValue = new AttributeValue();
    if (Objects.isNull(source)) {
      return attributeValue;
    }

    Matcher m = pattern.matcher(source.toString());

    if (m.matches()) {
      attributeValue.setN(source.toString());
    } else if (source.getClass() == Boolean.class) {
      attributeValue.setBOOL((Boolean) source);
    } else {
      attributeValue.setS(source.toString());
    }

    return attributeValue;
  }

  @Override
  public Map<String, Object> updateRobotConfiguration(Map<String, Object> robotConfiguration) {
    double version1 =
        Double.parseDouble(robotConfiguration.getOrDefault(VERSION_ATTRIBUTE, 1).toString());
    robotConfiguration.put(VERSION_ATTRIBUTE, ++version1);
    final Map<String, AttributeValue> newObject = convertToItem(robotConfiguration);

    dynamoDB.putItem(ROBOT_CONFIGURATION_TABLE, newObject);

    dynamoDB.putItem(ROBOT_CONFIGURATION_CHANGELOGS_TABLE, newObject);

    return robotConfiguration;
  }

  @Override
  public void deleteRobotConfiguration(String configurationId) {
      final AttributeValue idValue = new AttributeValue();
      idValue.setN(configurationId);

      dynamoDB.deleteItem(ROBOT_CONFIGURATION_TABLE, Map.of("id", idValue));

      List<AttributeValue> listVersions = findAllVersionsByConfigId(idValue);

      listVersions.forEach(version ->
          dynamoDB.deleteItem(ROBOT_CONFIGURATION_CHANGELOGS_TABLE, Map.of("id", idValue, "version", version))
      );

  }

  private List<AttributeValue> findAllVersionsByConfigId(AttributeValue idValue){

      QueryRequest queryRequest = new QueryRequest()
              .withTableName(ROBOT_CONFIGURATION_CHANGELOGS_TABLE)
              .withKeyConditionExpression("id = :id")
              .withExpressionAttributeValues(Map.of(":id", idValue))
              .withProjectionExpression(VERSION_ATTRIBUTE);

      QueryResult result = dynamoDB.query(queryRequest);

      return result.getItems().stream()
              .map(Map::values)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());
  }

  @Override
  public List<Map<String, Object>> getConfigurations() {
    ScanRequest scanRequest = new ScanRequest()
        .withTableName(ROBOT_CONFIGURATION_TABLE);

    ScanResult result = dynamoDB.scan(scanRequest);
    return convertItems(result.getItems());
  }

  private List<Map<String, Object>> convertItems(List<Map<String, AttributeValue>> items) {
    return items.stream()
        .map(this::convertItem)
        .collect(Collectors.toList());
  }

  private Map<String, Object> convertItem(Map<String, AttributeValue> item) {
    return item.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, this::convertAttributeValue));
  }

  private Object convertAttributeValue(Map.Entry<String, AttributeValue> entry) {
    AttributeValue source = entry.getValue();
    if (Objects.isNull(source)) {
      return "";
    }

    if (source.getS() != null) {
      return source.getS();
    } else if (source.getBOOL() != null) {
      return source.getBOOL();
    } else if (source.getN() != null) {
      return Double.valueOf(source.getN());
    }

    return "";
  }
}
