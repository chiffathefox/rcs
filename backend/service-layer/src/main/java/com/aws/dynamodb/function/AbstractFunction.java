package com.aws.dynamodb.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.dynamodb.services.DaoService;
import com.aws.dynamodb.services.DaoServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

public abstract class AbstractFunction {

  private static final Type ROBOT_CONFIGURATION_TYPE =
      new TypeToken<Map<String, Object>>() {
      }.getType();

  protected final DaoService daoService = DaoServiceImpl.service();
  private final Gson gson = new Gson();

  protected APIGatewayProxyResponseEvent constructResponse(Object body) {
    APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

    responseEvent.setStatusCode(200);
    responseEvent.setHeaders(Map.of(HttpHeaders.CONTENT_TYPE,
        ContentType.APPLICATION_JSON.toString()));
    responseEvent.setBody(gson.toJson(body));

    return responseEvent;
  }

  protected Map<String, Object> extractBodyFromRequest(APIGatewayProxyRequestEvent requestEvent) {
    return gson.fromJson(requestEvent.getBody(), ROBOT_CONFIGURATION_TYPE);
  }

}
