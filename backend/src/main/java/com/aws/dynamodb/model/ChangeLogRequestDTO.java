package com.aws.dynamodb.model;

import lombok.Data;

@Data
public class ChangeLogRequestDTO {

  private String id;
  private String version;

}
