package com.aws.dynamodb.functions;

import com.aws.dynamodb.services.DaoService;
import java.util.List;
import java.util.Map;

public class DynamoDbFunctions {

  private final DaoService daoService;

  public DynamoDbFunctions(DaoService daoService) {
    this.daoService = daoService;
  }

  public List<Map<String, String>> getConfigurations() {
    return daoService.getConfigurations();
  }

  public List<Map<String, String>> getChangeLogs(String configurationId, String version) {
    return daoService.getChangeLogs(configurationId, version);
  }

  public Map<String, String> createRobotConfiguration(Map<String, String> robotConfiguration) {
    return daoService.createRobotConfiguration(robotConfiguration);
  }

  public Map<String, String> updateRobotConfiguration(Map<String, String> robotConfiguration) {
    return daoService.updateRobotConfiguration(robotConfiguration);
  }

  public void deleteRobotConfiguration(String configurationId) {
    daoService.deleteRobotConfiguration(configurationId);
  }
}
