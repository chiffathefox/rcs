package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DynamoGetAllConfigurationsFunction extends AbstractFunction
    implements RequestHandler<Void, APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(Void unused, Context context) {
    return constructResponse(daoService.getConfigurations());
  }

}
