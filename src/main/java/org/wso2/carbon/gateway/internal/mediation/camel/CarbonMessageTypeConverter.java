package org.wso2.carbon.gateway.internal.mediation.camel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.converter.jaxp.XmlConverter;
import org.apache.camel.support.TypeConverterSupport;
import org.w3c.dom.Document;
import org.wso2.carbon.gateway.internal.common.CarbonMessage;
import org.wso2.carbon.gateway.internal.common.ContentChunk;
import org.wso2.carbon.gateway.internal.common.Pipe;
import org.wso2.carbon.gateway.internal.transport.common.HTTPContentChunk;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * A type converter which is used to convert to and from array types
 * particularly for derived types of array component types and dealing with
 * primitive array types.
 *
 * @version
 */
@Converter
public class CarbonMessageTypeConverter extends TypeConverterSupport {
//public final class CarbonMessageTypeConverter {

    @SuppressWarnings("unchecked")
    @Converter
    public <T> T convertTo(Class<T> type, Exchange exchange, Object value) {
        if(value instanceof CarbonMessage) {
            List<ByteBuf> listOfContentBuffers = new ArrayList<ByteBuf>();
            Pipe pipe = ((CarbonMessage)value).getPipe();
            ByteBufInputStream byteBufInputStream = null;
            BlockingQueue<ContentChunk> clonedContent = pipe.getClonedContentQueue();
            while (true) {
                HTTPContentChunk httpContentChunk = null;
                try {
                    if (clonedContent.isEmpty()) {
                        break;
                    } else {
                        httpContentChunk = (HTTPContentChunk) clonedContent.take();
                        listOfContentBuffers.add(httpContentChunk.getHttpContent().duplicate().content());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ByteBuf compositeBuffer = Unpooled.wrappedBuffer(listOfContentBuffers.toArray(new ByteBuf[listOfContentBuffers.size()]));
            byteBufInputStream = new ByteBufInputStream(compositeBuffer);
            XmlConverter xmlConverter = new XmlConverter();
            try {
                return (T)xmlConverter.toDOMDocument(byteBufInputStream, exchange);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            //return (T)byteBufInputStream;
        }
        return null;
    }

    @Converter
    public static Document convertTo(Exchange exchange, Object value) {
        if(value instanceof CarbonMessage) {
            List<ByteBuf> listOfContentBuffers = new ArrayList<ByteBuf>();
            Pipe pipe = ((CarbonMessage)value).getPipe();
            ByteBufInputStream byteBufInputStream = null;
            BlockingQueue<ContentChunk> clonedContent = pipe.getClonedContentQueue();
            while (true) {
                HTTPContentChunk httpContentChunk = null;
                try {
                    if (clonedContent.isEmpty()) {
                        break;
                    } else {
                        httpContentChunk = (HTTPContentChunk) clonedContent.take();
                        listOfContentBuffers.add(httpContentChunk.getHttpContent().duplicate().content());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ByteBuf compositeBuffer = Unpooled.wrappedBuffer(listOfContentBuffers.toArray(new ByteBuf[listOfContentBuffers.size()]));
            byteBufInputStream = new ByteBufInputStream(compositeBuffer);
            XmlConverter xmlConverter = new XmlConverter();
            try {
                return xmlConverter.toDOMDocument(byteBufInputStream, exchange);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            //return (T)byteBufInputStream;
        }
        return null;
    }


}

