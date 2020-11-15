package com.aws.dynamodb.services;

import java.util.List;
import java.util.Map;

public class DaoServiceImpl implements DaoService {
  @Override
  public List<Map<String, String>> getConfigurations() {
    return null;
  }

  @Override
  public List<Map<String, String>> getChangeLogs(String configurationId, String version) {
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
}
