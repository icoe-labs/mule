/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.db.api.param;

import org.mule.extension.db.internal.domain.type.JdbcType;
import org.mule.runtime.extension.api.annotation.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;

public abstract class QueryParameter {

  /**
   * The name of the input parameter.
   */
  @Parameter
  private String paramName;

  /**
   * Parameter type name.
   */
  @Parameter
  @Optional
  private JdbcType type;

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public JdbcType getType() {
    return type;
  }
}