/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.management.stats;

import org.mule.runtime.core.api.management.stats.Statistics;

/**
 * Exposes methods required to increment/decrement queue statistics
 */
public interface QueueStatistics extends Statistics {

  void incQueuedEvent();

  void decQueuedEvent();
}
