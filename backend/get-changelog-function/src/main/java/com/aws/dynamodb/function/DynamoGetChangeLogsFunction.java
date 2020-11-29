package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoGetChangeLogsFunction extends AbstractFunction
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent changeLogRequestDTO,
                                                    Context context) {
    log.debug("Proxy request:" + changeLogRequestDTO);
    final String changelogId = changeLogRequestDTO.getPathParameters().get("changelog_id");
    final String[] split = changelogId.split(":");

    return constructResponse(daoService.getChangeLog(split[0], split[1]));
  }
}
