package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.Map;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoUpdateConfigurationsFunction extends AbstractFunction
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent,
                                                    Context context) {
    log.debug("Request: " + requestEvent);

    final Map<String, Object> robotConfiguration = extractBodyFromRequest(requestEvent);

    return constructResponse(daoService.updateRobotConfiguration(robotConfiguration));
  }
}
