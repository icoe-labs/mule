/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core;

import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.core.api.MuleContext;
import org.mule.runtime.core.api.MuleEvent;
import org.mule.runtime.core.api.MuleEventContext;
import org.mule.runtime.core.api.MuleException;
import org.mule.runtime.core.api.MuleMessage;
import org.mule.runtime.core.api.MuleSession;
import org.mule.runtime.core.api.construct.FlowConstruct;
import org.mule.runtime.core.api.transaction.Transaction;
import org.mule.runtime.core.api.transformer.TransformerException;
import org.mule.runtime.core.transaction.TransactionCoordination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>DefaultMuleEventContext</code> is the context object for the current request. Using the context, developers can
 * send/dispatch/receive events programmatically as well as manage transactions.
 */
@Deprecated
public class DefaultMuleEventContext implements MuleEventContext {

  /**
   * logger used by this class
   */
  protected static final Logger logger = LoggerFactory.getLogger(DefaultMuleEventContext.class);

  private final MuleEvent event;

  public DefaultMuleEventContext(MuleEvent event) {
    this.event = event;
  }

  /**
   * Returns the message payload for this event
   *
   * @return the message payload for this event
   */
  @Override
  public MuleMessage getMessage() {
    return event.getMessage();
  }

  @Override
  public MuleEvent getEvent() {
    return event;
  }

  /**
   * Returns the message transformed into its recognised or expected format. The transformer used is the one configured on the
   * endpoint through which this event was received.
   *
   * @param dataType The dataType required for the return object. This param just provides a convienient way to manage type
   *        casting of transformed objects
   * @return the message transformed into it's recognised or expected format.
   * @throws org.mule.runtime.core.api.transformer.TransformerException if a failure occurs or if the return type is not the same
   *         as the expected type in the transformer
   * @see org.mule.runtime.core.api.transformer.Transformer
   */
  @Override
  public Object transformMessage(DataType dataType, MuleContext muleContext) throws TransformerException {
    return event.transformMessage(dataType, muleContext);
  }

  /**
   * Returns the message transformed into its recognised or expected format. The transformer used is the one configured on the
   * endpoint through which this event was received.
   *
   * @param expectedType The class type required for the return object. This param just provides a convienient way to manage type
   *        casting of transformed objects
   * @return the message transformed into it's recognised or expected format.
   * @throws org.mule.runtime.core.api.transformer.TransformerException if a failure occurs or if the return type is not the same
   *         as the expected type in the transformer
   * @see org.mule.runtime.core.api.transformer.Transformer
   */
  @Override
  public Object transformMessage(Class expectedType, MuleContext muleContext) throws TransformerException {
    return event.transformMessage(DataType.fromType(expectedType), muleContext);
  }

  /**
   * Returns the message contents as a string
   *
   * @return the message contents as a string
   * @throws org.mule.runtime.core.api.MuleException if the message cannot be converted into a string
   */
  @Override
  public String getMessageAsString(Charset encoding, MuleContext muleContext) throws MuleException {
    return event.getMessageAsString(encoding, muleContext);
  }

  /**
   * Returns the message transformed into it's recognised or expected format and then into a String. The transformer used is the
   * one configured on the endpoint through which this event was received. This method will use the default encoding on the event
   *
   * @return the message transformed into it's recognised or expected format as a Strings.
   * @throws org.mule.runtime.core.api.transformer.TransformerException if a failure occurs in the transformer
   * @see org.mule.runtime.core.api.transformer.Transformer
   */
  @Override
  public String transformMessageToString(MuleContext muleContext) throws TransformerException {
    return event.transformMessageToString(muleContext);
  }

  /**
   * Returns the message contents as a string This method will use the default encoding on the event
   *
   * @param muleContext the Mule node.
   * @return the message contents as a string
   * @throws org.mule.runtime.core.api.MuleException if the message cannot be converted into a string
   */
  @Override
  public String getMessageAsString(MuleContext muleContext) throws MuleException {
    return event.getMessageAsString(muleContext);
  }

  /**
   * Returns the current transaction (if any) for the session
   *
   * @return the current transaction for the session or null if there is no transaction in progress
   */
  @Override
  public Transaction getCurrentTransaction() {
    return TransactionCoordination.getInstance().getTransaction();
  }

  /**
   * @return the service descriptor of the service that received this event
   */
  @Override
  public FlowConstruct getFlowConstruct() {
    return event.getFlowConstruct();
  }

  /**
   * Determines whether the default processing for this event will be executed. By default, the Mule server will route events
   * according to a components configuration. The user can override this behaviour by obtaining a reference to the MuleEvent
   * context, either by implementing <code>org.mule.runtime.core.api.lifecycle.Callable</code> or calling
   * <code>RequestContext.getEventContext</code> to obtain the MuleEventContext for the current thread. The user can
   * programmatically control how events are dispached.
   *
   * @param stopFurtherProcessing the value to set.
   */
  @Override
  public void setStopFurtherProcessing(boolean stopFurtherProcessing) {
    event.setStopFurtherProcessing(stopFurtherProcessing);
  }

  /**
   * An output stream the can optionally be used write response data to an incoming message.
   *
   * @return an output stream if one has been made available by the message receiver that received the message
   */
  @Override
  public OutputStream getOutputStream() {
    return event.getOutputStream();
  }

  @Override
  public URI getEndpointURI() {
    return event.getMessageSourceURI();
  }

  @Override
  public MessageExchangePattern getExchangePattern() {
    return event.getExchangePattern();
  }

  /**
   * Returns the transaction for the current event or null if there is no transaction in progresss
   *
   * @return the transaction for the current event or null if there is no transaction in progresss
   */
  @Override
  public Transaction getTransaction() {
    return TransactionCoordination.getInstance().getTransaction();
  }

  @Override
  public MuleSession getSession() {
    return event.getSession();
  }

  @Override
  public String toString() {
    return event.toString();
  }
}
