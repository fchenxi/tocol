package tocol.rpc.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.protocol.handle.ReceivedHandle;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final String hostName;
    private final ReceivedHandle<Channel> receivedHandle;

    public ClientHandler(String hostName, ReceivedHandle<Channel> receivedHandle) {
        super();
        this.hostName = hostName;
        this.receivedHandle = receivedHandle;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         // do heartbeat
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // read timeout
                System.out.println("READER_IDLE 读超时");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                // write timeout
                System.out.println("write timeout:" + ctx.channel() + "\t" + ChannelManagerClientSingle
						.getChannelManagerMap().get(hostName).size());
                ChannelManagerClientSingle.remove(hostName, ctx.channel());

            } else if (event.state() == IdleState.ALL_IDLE) {
                // timeout
                System.out.println("ALL_IDLE timeout");

            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            receivedHandle.received(ctx.channel(), msg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
        ChannelManagerClientSingle.remove(hostName, ctx.channel());
    }

}
