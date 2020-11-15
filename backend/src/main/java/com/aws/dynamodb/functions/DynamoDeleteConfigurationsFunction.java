package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;

public class DynamoDeleteConfigurationsFunction implements RequestHandler<String, String> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public String handleRequest(String configurationId, Context context) {
    daoService.deleteRobotConfiguration(configurationId);
    return configurationId;
  }
}
