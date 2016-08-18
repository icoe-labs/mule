/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.db.integration.update;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mule.extension.db.integration.DbTestUtil.selectData;
import static org.mule.extension.db.integration.TestDbConfig.getDerbyResource;
import static org.mule.extension.db.integration.TestRecordUtil.assertRecords;
import org.mule.extension.db.api.StatementResult;
import org.mule.extension.db.integration.AbstractDbIntegrationTestCase;
import org.mule.extension.db.integration.model.AbstractTestDatabase;
import org.mule.extension.db.integration.model.Field;
import org.mule.extension.db.integration.model.Record;
import org.mule.runtime.api.message.MuleMessage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runners.Parameterized;

public class MergeTestCase extends AbstractDbIntegrationTestCase {

  public MergeTestCase(String dataSourceConfigResource, AbstractTestDatabase testDatabase) {
    super(dataSourceConfigResource, testDatabase);
  }

  @Parameterized.Parameters
  public static List<Object[]> parameters() {
    return getDerbyResource();
  }

  @Override
  protected String[] getFlowConfigurationResources() {
    return new String[] {"integration/update/merge-config.xml"};
  }

  @Test
  public void mergesTables() throws Exception {
    assertMergeResult(flowRunner("merge").run().getMessage());
  }

  private void assertMergeResult(MuleMessage response) throws SQLException {
    StatementResult result = response.getPayload();
    assertThat(result.getUpdateCount(), is(3));

    List<Map<String, String>> data = selectData("select * from PLANET order by ID", getDefaultDataSource());
    assertRecords(data, createRecord(2), createRecord(3), createRecord(4));
  }

  private Record createRecord(int pos) {
    return new Record(new Field("NAME", "merged"), new Field("POSITION", pos));
  }

}
