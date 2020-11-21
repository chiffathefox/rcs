package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.Map;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoUpdateConfigurationsFunction
    implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> robotConfiguration,
                                           Context context) {
    log.debug("Request: " + robotConfiguration);
    return daoService.updateRobotConfiguration(robotConfiguration);
  }
}
