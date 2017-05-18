package tocol.rpc.protocol.handle;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tocol.rpc.protocol.Protocol;

public abstract class AbstractReceivedHandle<T> implements ReceivedHandle<T> {

    private Logger LOG = LoggerFactory.getLogger(AbstractReceivedHandle.class);

    protected final Protocol protocol;

    public AbstractReceivedHandle(Protocol protocol) {
        super();
        this.protocol = protocol;
    }

    public abstract void receivedObject(T channel, Object obj);

    @Override
    public Object received(T channel, Object obj) {
        Object o = null;
        if (obj instanceof ByteBuf) {
            try {
                o = protocol.decoder((ByteBuf) obj);
                receivedObject(channel, o);
            } catch (Exception e) {
                LOG.error("Unexpected exception: " + e);
            }
        }
        return o;
    }

}
