package com.aws.dynamodb.services;

import java.util.List;
import java.util.Map;

public interface DaoService {
  List<Map<String, Object>> getConfigurations();

  List<Map<String, Object>> getChangeLogs(String configurationId, String version);

  Map<String, String> createRobotConfiguration(Map<String, String> robotConfiguration);

  Map<String, String> updateRobotConfiguration(Map<String, String> robotConfiguration);

  void deleteRobotConfiguration(String configurationId);
}
