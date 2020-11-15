package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.List;
import java.util.Map;

public class DynamoGetAllConfigurationsFunction
    implements RequestHandler<Void, List<Map<String, String>>> {

  private final DaoService daoService = DaoServiceImpl.service();

  public List<Map<String, String>> getConfigurations() {
    return daoService.getConfigurations();
  }

  @Override
  public List<Map<String, String>> handleRequest(Void unused, Context context) {
    return daoService.getConfigurations();
  }
}
