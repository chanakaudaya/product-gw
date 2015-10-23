/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.wso2.carbon.gateway.internal.transport.common;


import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.gateway.internal.common.ContentChunk;
import org.wso2.carbon.gateway.internal.common.Pipe;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * A class that contains the content of request.
 */
public class PipeImpl implements Pipe {

    private static final Logger LOG = LoggerFactory.getLogger(PipeImpl.class);

    private BlockingQueue<ContentChunk> contentQueue;

    private BlockingQueue<ContentChunk> clonedContentQueue;

    //private ByteBuf messageBuffer;

    //private BlockingQueue<FullHttpRequest> fullHttpRequestQueue;


    public PipeImpl(int blockingQueueSize) {
        this.contentQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        //this.fullHttpRequestQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        this.clonedContentQueue = new LinkedBlockingQueue<>(blockingQueueSize);
    }


    public ContentChunk getContent() {
        try {
            return contentQueue.take();
        } catch (InterruptedException e) {
            LOG.error("Error while retrieving chunk from queue.", e);
            return null;
        }
    }

    @Override
    public ContentChunk getClonedContent() {
        try {
            return this.clonedContentQueue.take();
        } catch (InterruptedException e) {
            LOG.error("Error while retrieving chunk from queue.", e);
            return null;
        }
    }

    @Override
    public BlockingQueue<ContentChunk> getClonedContentQueue() {
        if(!this.contentQueue.isEmpty()) {
            if(!clonedContentQueue.isEmpty()) {
                clonedContentQueue.clear();
            }
            this.clonedContentQueue.addAll(this.contentQueue);
        }
        return this.clonedContentQueue;
    }


    public void addContentChunk(ContentChunk contentChunk) {
        contentQueue.add(contentChunk);
    }

//    @Override
//    public void addFullHttpRequest(FullHttpRequest content) {
//         fullHttpRequestQueue.add(content);
//    }
//
//    @Override
//    public FullHttpRequest getFullHttpRequest() {
//        try {
//            return fullHttpRequestQueue.take();
//        } catch (InterruptedException e) {
//            LOG.error("Error while retrieving chunk from queue.", e);
//            return null;
//        }
//    }

//    @Override
//    public FullHttpRequest getClonedFullHttpRequest() {
//        try {
//            FullHttpRequest clonedContent = null;
//            if(!fullHttpRequestQueue.isEmpty()) {
//                clonedContent = fullHttpRequestQueue.take();
//                fullHttpRequestQueue.add(clonedContent);
//            }
//            return clonedContent;
//        } catch (InterruptedException e) {
//            LOG.error("Error while retrieving chunk from queue.", e);
//            return null;
//        }
//    }

    public void clearContent() {
        this.contentQueue.clear();
        this.clonedContentQueue.clear();
    }

//
//    public ByteBuf getMessageBuffer() {
//        return messageBuffer;
//    }
//
//    public void setMessageBuffer(ByteBuf messageBuffer) {
//        this.messageBuffer = messageBuffer;
//    }
}
