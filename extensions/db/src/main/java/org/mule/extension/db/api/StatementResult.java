/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.db.api;

import com.google.common.collect.ImmutableMap;

import java.math.BigInteger;
import java.util.Map;

public class StatementResult {

  private final int updateCount;
  private final Map<String, BigInteger> generatedKeys;

  public StatementResult(int updateCount, Map<String, BigInteger> generatedKeys) {
    this.updateCount = updateCount;
    this.generatedKeys = generatedKeys != null ? ImmutableMap.copyOf(generatedKeys) : ImmutableMap.of();
  }

  public int getUpdateCount() {
    return updateCount;
  }

  public Map<String, BigInteger> getGeneratedKeys() {
    return generatedKeys;
  }
}
