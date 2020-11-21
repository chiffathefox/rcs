package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.List;
import java.util.Map;

public class DynamoGetAllConfigurationsFunction
    implements RequestHandler<Void, List<Map<String, Object>>> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public List<Map<String, Object>> handleRequest(Void unused, Context context) {
    return daoService.getConfigurations();
  }
}
