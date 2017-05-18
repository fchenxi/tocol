package tocol.rpc.server.netty.handle;

import io.netty.channel.Channel;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.handle.AbstractReceivedHandle;
import tocol.rpc.protocol.handle.SendHandle;
import tocol.rpc.protocol.params.Constants;
import tocol.rpc.protocol.params.RequestParams;
import tocol.rpc.protocol.params.ResponseParams;
import tocol.rpc.server.invoker.InvokerResult;

public class ServerReceivedHandle extends AbstractReceivedHandle<Channel> {

    private final SendHandle<Channel> sendHandle;

    @Override
    public void receivedObject(Channel channel, Object obj) {
        if (obj instanceof RequestParams) {
            RequestParams request = (RequestParams) obj;
            ResponseParams response = new ResponseParams(request.getId());
            // request package
            if (Constants.MessageTypeService.equals(request.getMessageType())) {
                Object result = InvokerResult.invoke(request);
                response.setValue(result);
            }
            response.setMessageType(request.getMessageType());
            response.setMethod(request.getMethod());
            response.setVersion(request.getVersion());
            response.setServiceName(request.getServiceName());
            response.setProtocol(request.getProtocol());
            sendHandle.send(channel, response);
        }
    }

    public ServerReceivedHandle(SendHandle sendHandle, Protocol protocol) {
        super(protocol);
        this.sendHandle = sendHandle;
    }

}
