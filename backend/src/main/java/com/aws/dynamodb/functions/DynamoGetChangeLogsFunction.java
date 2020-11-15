package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j;

@Log4j
public class DynamoGetChangeLogsFunction
    implements RequestHandler<APIGatewayProxyRequestEvent, List<Map<String, Object>>> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public List<Map<String, Object>> handleRequest(APIGatewayProxyRequestEvent changeLogRequestDTO,
                                                 Context context) {
    log.debug("Proxy request:" + changeLogRequestDTO);
    final String changelogId = changeLogRequestDTO.getPathParameters().get("changelog_id");
    final String[] split = changelogId.split(":");
    return daoService.getChangeLogs(split[0], split[1]);
  }
}
