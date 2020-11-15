package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.List;
import java.util.Map;

public class DynamoUpdateConfigurationsFunction
    implements RequestHandler<Map<String, String>, Map<String, String>> {

  private final DaoService daoService = DaoServiceImpl.service();

  public List<Map<String, String>> getConfigurations() {
    return daoService.getConfigurations();
  }

  @Override
  public Map<String, String> handleRequest(Map<String, String> robotConfiguration,
                                           Context context) {
    return daoService.updateRobotConfiguration(robotConfiguration);
  }
}