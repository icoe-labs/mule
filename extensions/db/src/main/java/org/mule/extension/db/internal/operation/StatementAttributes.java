/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.db.internal.operation;

import org.mule.runtime.extension.api.annotation.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;

public class StatementAttributes {

  @Parameter
  @Optional
  private Integer fetchSize;

  @Parameter
  @Optional
  private Integer maxRows;

  public Integer getFetchSize() {
    return fetchSize;
  }

  public Integer getMaxRows() {
    return maxRows;
  }
}
