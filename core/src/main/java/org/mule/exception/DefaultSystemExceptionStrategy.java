/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.exception;

import org.mule.api.MuleContext;

/**
 * Log exception, fire a notification, and clean up transaction if any.
 */
public class DefaultSystemExceptionStrategy extends AbstractSystemExceptionStrategy
{
    /** 
     * For IoC only 
     * @deprecated Use DefaultSystemExceptionStrategy(MuleContext muleContext) instead
     */
    public DefaultSystemExceptionStrategy()
    {
        super();
    }

    public DefaultSystemExceptionStrategy(MuleContext muleContext)
    {
        super();
        setMuleContext(muleContext);
    }
}
