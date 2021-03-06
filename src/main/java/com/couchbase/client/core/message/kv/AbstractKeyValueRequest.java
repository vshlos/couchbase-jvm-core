/**
 * Copyright (C) 2014 Couchbase, Inc.
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
package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;

/**
 * Default implementation of a {@link BinaryRequest}.
 *
 * @author Michael Nitschinger
 * @since 1.0
 */
public abstract class AbstractKeyValueRequest extends AbstractCouchbaseRequest implements BinaryRequest {

    /**
     * The opaque identifier used in the binary protocol to track requests/responses.
     *
     * No overflow control is applied, since once it overflows it starts with negative values again.
     */
    private static volatile int GLOBAL_OPAQUE = 0;

    protected static final short DEFAULT_PARTITION = -1;

    /**
     * The key of the document, should be null if not tied to any.
     */
    private final String key;

    /**
     * The partition (vbucket) of the document.
     */
    private short partition = DEFAULT_PARTITION;

    private final int opaque;

    /**
     * Creates a new {@link AbstractKeyValueRequest}.
     *
     * @param key the key of the document.
     * @param bucket the bucket of the document.
     * @param password the optional password of the bucket.
     */
    protected AbstractKeyValueRequest(String key, String bucket, String password) {
        super(bucket, password);
        this.key = key;
        opaque = GLOBAL_OPAQUE++;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public short partition() {
        if (partition == -1) {
            throw new IllegalStateException("Partition requested but not set beforehand");
        }
        return partition;
    }

    @Override
    public BinaryRequest partition(short partition) {
        if (partition < 0) {
            throw new IllegalArgumentException("Partition must be larger than or equal to zero");
        }
        this.partition = partition;
        return this;
    }

    @Override
    public int opaque() {
        return opaque;
    }
}
