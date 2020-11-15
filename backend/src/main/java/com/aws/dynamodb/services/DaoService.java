package com.aws.dynamodb.services;

import java.util.List;
import java.util.Map;

public interface DaoService {
  List<Map<String, Object>> getConfigurations();

  List<Map<String, Object>> getChangeLogs(String configurationId, String version);

  Map<String, Object> createRobotConfiguration(Map<String, Object> robotConfiguration);

  Map<String, Object> updateRobotConfiguration(Map<String, Object> robotConfiguration);

  void deleteRobotConfiguration(String configurationId);
}
