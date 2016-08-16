/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extension.db.internal.resolver.query;

import org.mule.extension.db.api.param.QueryDefinition;
import org.mule.extension.db.internal.DbConnector;
import org.mule.extension.db.internal.domain.connection.DbConnection;
import org.mule.extension.db.internal.domain.query.Query;

/**
 * Resolves a {@link Query}
 */
public interface QueryResolver {

  /**
   * Resolves a query
   *
   * @param queryDefinition
   * @param connector
   * @param connection      connection to the database. not null
   * @return query resolved for the given event
   */
  Query resolve(QueryDefinition queryDefinition, DbConnector connector, DbConnection connection) throws QueryResolutionException;
}