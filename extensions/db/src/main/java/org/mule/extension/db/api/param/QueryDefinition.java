/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.db.api.param;

import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.mule.runtime.core.config.i18n.MessageFactory.createStaticMessage;
import org.mule.extension.db.internal.operation.QuerySettings;
import org.mule.runtime.core.api.MuleRuntimeException;
import org.mule.runtime.core.util.collection.ImmutableMapCollector;
import org.mule.runtime.extension.api.annotation.Parameter;
import org.mule.runtime.extension.api.annotation.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.Optional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryDefinition {

  @Parameter
  @Optional
  //@Text
  private String sql;

  @Parameter
  @Optional
  private List<QueryParameter> parameters = new LinkedList<>();

  @ParameterGroup
  private QuerySettings settings = new QuerySettings();

  //@Parameter
  //@Optional
  //@XmlHints(allowInlineDefinition = false)
  private QueryDefinition template;

  public QueryDefinition resolveFromTemplate() {
    final QueryDefinition template = getTemplate();

    if (template == null) {
      return this;
    }

    QueryDefinition resolvedDefinition = copy();

    if (isBlank(resolvedDefinition.getSql())) {
      resolvedDefinition.setSql(template.getSql());
    }

    resolveTemplateParameters(template, resolvedDefinition);

    return resolvedDefinition;
  }

  private void resolveTemplateParameters(QueryDefinition template, QueryDefinition resolvedDefinition) {
    Map<String, Object> templateParamValues = null;
    if (template != null) {
      templateParamValues = template.getParameterValues();
    }

    Map<String, Object> resolvedParameterValues = new HashMap<>();
    if (templateParamValues != null) {
      resolvedParameterValues.putAll(templateParamValues);
    }

    resolvedParameterValues.putAll(getParameterValues());
    resolvedDefinition.getParameters().stream()
        .filter(p -> p instanceof InputParameter)
        .forEach(p -> {
          InputParameter inputParameter = (InputParameter) p;
          final String paramName = inputParameter.getParamName();
          if (resolvedParameterValues.containsKey(paramName)) {
            inputParameter.setValue(resolvedParameterValues.get(paramName));
            resolvedParameterValues.remove(paramName);
          }
        });

    resolvedParameterValues.entrySet().stream()
        .map(entry -> {
          InputParameter inputParameter = new InputParameter();
          inputParameter.setParamName(entry.getKey());
          inputParameter.setValue(entry.getValue());

          return inputParameter;
        }).forEach(p -> resolvedDefinition.parameters.add(p));
  }

  public java.util.Optional<QueryParameter> getParameter(String name) {
    return parameters.stream().filter(p -> p.getParamName().equals(name)).findFirst();
  }

  protected QueryDefinition copy() {
    QueryDefinition copy;
    try {
      copy = getClass().newInstance();
    } catch (Exception e) {
      throw new MuleRuntimeException(createStaticMessage("Could not create instance of " + getClass().getName()), e);
    }

    copy.sql = sql;
    copy.parameters = new LinkedList<>(parameters);

    return copy;
  }

  public Map<String, Object> getParameterValues() {
    return parameters.stream()
        .filter(p -> p instanceof InputParameter)
        .collect(new ImmutableMapCollector<>(QueryParameter::getParamName, p -> ((InputParameter) p).getValue()));
  }

  public String getSql() {
    return sql;
  }

  public List<QueryParameter> getParameters() {
    return unmodifiableList(parameters);
  }

  public QuerySettings getSettings() {
    return settings;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public QueryDefinition getTemplate() {
    return template;
  }

}
