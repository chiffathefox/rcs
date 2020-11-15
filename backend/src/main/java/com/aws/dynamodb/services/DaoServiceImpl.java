package com.aws.dynamodb.services;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.aws.dynamodb.manager.DynamoDBManager;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j;

@Log4j
public class DaoServiceImpl implements DaoService {
  private static final DynamoDB dynamoDB = DynamoDBManager.dynamoDB();
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
    return null;
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
    return List.of(Map.of("something1", "something2"));
  }
}
