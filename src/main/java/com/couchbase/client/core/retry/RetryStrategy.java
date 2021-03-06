/**
 * Copyright (c) 2015 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package com.couchbase.client.core.retry;

import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.kv.ObserveRequest;

/**
 * Base interface for all {@link RetryStrategy} implementations.
 *
 * @author Michael Nitschinger
 * @since 1.1.0
 */
public interface RetryStrategy {

    /**
     * Decides whether the given {@link CouchbaseRequest} should be retried or cancelled.
     *
     * @param request the request in question.
     * @parem environment the environment for more context.
     * @return true if it should be retried, false otherwise.
     */
    boolean shouldRetry(CouchbaseRequest request, CoreEnvironment environment);

    /**
     * Decides whether {@link ObserveRequest}s should be retried or cancelled when an error happens.
     *
     * When false is returned, as soon as an error happens (for example one of the nodes that need to be reached
     * does not have an active partition because of a node failure) the whole observe sequence is aborted. If
     * retried, errors are swallowed and the observe cycle will start again.
     *
     * @return true if it should be retried, false otherwise.
     */
    boolean shouldRetryObserve();

}
