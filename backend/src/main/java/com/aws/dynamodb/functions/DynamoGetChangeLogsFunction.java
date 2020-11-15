package com.aws.dynamodb.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.dynamodb.model.ChangeLogRequestDTO;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import java.util.List;
import java.util.Map;

public class DynamoGetChangeLogsFunction
    implements RequestHandler<ChangeLogRequestDTO, List<Map<String, String>>> {

  private final DaoService daoService = DaoServiceImpl.service();

  @Override
  public List<Map<String, String>> handleRequest(ChangeLogRequestDTO changeLogRequestDTO,
                                                 Context context) {
    return daoService.getChangeLogs(changeLogRequestDTO.getId(), changeLogRequestDTO.getVersion());
  }
}
