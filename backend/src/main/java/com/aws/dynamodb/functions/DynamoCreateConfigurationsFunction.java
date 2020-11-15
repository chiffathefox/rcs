package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.Map;

public class DynamoCreateConfigurationsFunction
    implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> robotConfiguration,
                                           Context context) {
    return daoService.createRobotConfiguration(robotConfiguration);
  }
}
