package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class DynamoDeleteConfigurationsFunction extends AbstractFunction
    implements RequestHandler<APIGatewayProxyRequestEvent,
    APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
                                                    Context context) {
    final String configurationId = request.getPathParameters().get("configuration_id");
    daoService.deleteRobotConfiguration(configurationId);

    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
    responseEvent.setStatusCode(204);
    return responseEvent;
  }
}
