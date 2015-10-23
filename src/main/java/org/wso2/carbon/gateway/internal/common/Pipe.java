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

package org.wso2.carbon.gateway.internal.common;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Interface for create Content Holders.
 */
public interface Pipe {

    ContentChunk getContent();

    ContentChunk getClonedContent();

    BlockingQueue<ContentChunk> getClonedContentQueue();

    void addContentChunk(ContentChunk chunk);

    //ByteBuf getBuffer();

    //void addFullHttpRequest(FullHttpRequest content);

    //FullHttpRequest getFullHttpRequest();

    //FullHttpRequest getClonedFullHttpRequest();

}
