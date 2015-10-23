package org.wso2.carbon.gateway.internal.mediation.camel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultMessage;
import org.wso2.carbon.gateway.internal.common.CarbonMessage;
import org.wso2.carbon.gateway.internal.common.ContentChunk;
import org.wso2.carbon.gateway.internal.common.Pipe;
import org.wso2.carbon.gateway.internal.transport.common.HTTPContentChunk;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.SequenceInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CamelHttpMessage extends DefaultMessage {

    private final transient CarbonMessage carbonMessage;


//    @Override
//    public DefaultMessage newInstance() {
//        return new CamelHttpMessage();
//    }

    public CamelHttpMessage(CarbonMessage carbonMessage) {
        this.carbonMessage = carbonMessage;
    }

    @Override
    public DefaultMessage newInstance() {
        return new CamelHttpMessage(carbonMessage);
    }

//    public CamelHttpMessage() {
//    }

    @Override
    protected Object createBody() {
        ByteBufInputStream byteBufInputStream = null;
        if (carbonMessage != null) {
            byteBufInputStream = new ByteBufInputStream(((HTTPContentChunk) carbonMessage.getPipe().getClonedContent()).getHttpContent().duplicate().content());
        }
        return byteBufInputStream;
//        if (this.carbonMessage != null) {
//            byteBufInputStream = convertContentToInputStream(this.carbonMessage);
//        }
//        return byteBufInputStream;
//          if(carbonMessage != null) {
//              return convertCarbonMessageCotenttoSequenceInputStream(carbonMessage);
//          }
//        return null;
    }

    public CarbonMessage getCarbonMessage() {
        return this.carbonMessage;
    }

//    public void setCarbonMessage(CarbonMessage carbonMessage) {
//        this.carbonMessage = carbonMessage;
//    }

//    private SequenceInputStream convertCarbonMessageCotenttoSequenceInputStream(CarbonMessage carbonMessage) {
//        Pipe pipe = carbonMessage.getPipe();
//        SequenceInputStream outputConcatSequenceStream = null;
//        ByteBufInputStream byteBufInputStream = null;
//
//        //Take the first chunk from the pipe
//        byteBufInputStream = new ByteBufInputStream(((HTTPContentChunk) carbonMessage.getPipe().getContent()).getHttpContent().duplicate().content());
//        outputConcatSequenceStream = new SequenceInputStream(byteBufInputStream, null);
//        while(true) {
//           HTTPContentChunk httpContentChunk = (HTTPContentChunk)pipe.getContent();
//            if(httpContentChunk == null) {
//                break;
//            } else {
//                ByteBufInputStream byteBufInputStreamInternal = new ByteBufInputStream(httpContentChunk.getHttpContent().duplicate().content());
//                outputConcatSequenceStream =  new SequenceInputStream(outputConcatSequenceStream, byteBufInputStreamInternal);
//            }
//
//        }
//
//        try {
//            byte[] bytes = IOUtils.readFully(outputConcatSequenceStream, 0, true);
//            ByteBuffer.wrap(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return outputConcatSequenceStream;
//    }

//    private ByteBufInputStream convertContentToInputStream(CarbonMessage carbonMessage) {
//        List<ByteBuf> listOfContentBuffers = new ArrayList<ByteBuf>();
//        Pipe pipe = carbonMessage.getPipe();
//        ByteBufInputStream byteBufInputStream = null;
//        BlockingQueue<ContentChunk> clonedContent = pipe.getClonedContentQueue();
//        while(true) {
//            HTTPContentChunk httpContentChunk = null;
//            try {
//                httpContentChunk = (HTTPContentChunk)clonedContent.take();
//                if(httpContentChunk == null) {
//                    break;
//                } else {
//                    listOfContentBuffers.add(httpContentChunk.getHttpContent().duplicate().content());
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        ByteBuf compositeBuffer = Unpooled.wrappedBuffer(listOfContentBuffers.toArray(new ByteBuf[listOfContentBuffers.size()]));
//        byteBufInputStream = new ByteBufInputStream(compositeBuffer);
//        return byteBufInputStream;
//    }

}

