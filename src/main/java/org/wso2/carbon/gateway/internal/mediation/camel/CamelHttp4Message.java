package org.wso2.carbon.gateway.internal.mediation.camel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import org.apache.camel.impl.DefaultMessage;
import org.wso2.carbon.gateway.internal.common.CarbonMessage;
import org.wso2.carbon.gateway.internal.common.ContentChunk;
import org.wso2.carbon.gateway.internal.common.Pipe;
import org.wso2.carbon.gateway.internal.transport.common.HTTPContentChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CamelHttp4Message extends DefaultMessage {


    private CarbonMessage carbonMessage;
    //private Class<ByteBufInputStream> type;

//    public CamelHttp4Message(CarbonMessage carbonMessage) {
//        this.carbonMessage = carbonMessage;
//    }

    public CarbonMessage getCarbonMessage() {
        return this.carbonMessage;
    }

    public void setCarbonMessage(CarbonMessage carbonMessage) {
        this.carbonMessage = carbonMessage;
    }

    @Override
    public DefaultMessage newInstance() {
        return new CamelHttp4Message();
    }

    @Override
    public String toString() {
        if (carbonMessage != null) {
            return "CarbonMessage: " + carbonMessage;
        } else {
            return "CarbonMessage: " + getBody();
        }
    }

//    @Override
//    public <T> void setBody(Object value, Class<T> type) {
//        ByteBufInputStream byteBufInputStream = null;
//        if (this.carbonMessage != null) {
//            byteBufInputStream = convertContentToInputStream(this.carbonMessage);
//        }
//        super.setBody(byteBufInputStream, ByteBufInputStream.class);
//    }

//    @Override
//    protected Object createBody() {
//        ByteBufInputStream byteBufInputStream = null;
//        if (this.carbonMessage != null) {
//            byteBufInputStream = convertContentToInputStream(this.carbonMessage);
//        }
//        return byteBufInputStream;
//    }
//
//        private ByteBufInputStream convertContentToInputStream(CarbonMessage carbonMessage) {
//        List<ByteBuf> listOfContentBuffers = new ArrayList<ByteBuf>();
//        Pipe pipe = carbonMessage.getPipe();
//        ByteBufInputStream byteBufInputStream = null;
//        BlockingQueue<ContentChunk> clonedContent = pipe.getClonedContentQueue();
//        while(true) {
//            HTTPContentChunk httpContentChunk = null;
//            try {
//                if(clonedContent.isEmpty()) {
//                    break;
//                } else {
//                    httpContentChunk = (HTTPContentChunk) clonedContent.take();
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

    @Override
    protected Object createBody() {
        return this.carbonMessage;
    }

}
