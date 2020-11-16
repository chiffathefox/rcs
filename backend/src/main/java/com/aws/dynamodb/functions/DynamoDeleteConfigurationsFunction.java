package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;

public class DynamoDeleteConfigurationsFunction
    implements RequestHandler<APIGatewayProxyRequestEvent,
    APIGatewayProxyResponseEvent> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
    final String configurationId = request.getPathParameters().get("configuration_id");
    daoService.deleteRobotConfiguration(configurationId);

    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
    responseEvent.setStatusCode(204);
    return responseEvent;
  }
}
