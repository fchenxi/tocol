package tocol.rpc.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.protocol.handle.ReceivedHandle;
import tocol.rpc.server.Server;
import tocol.rpc.server.conf.ChannelManagerServerSingle;

public class ServerHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("Client disconnect...");
        ChannelManagerServerSingle.remove(hostName, ctx.channel());

    }

    private final ReceivedHandle<Channel> receivedHandle;
    private final String hostName;
    private final Server server;

    public ServerHandlerAdapter(Server server, ReceivedHandle<Channel> receivedHandle, String
            hostName) {
        super();
        this.server = server;
        this.receivedHandle = receivedHandle;
        this.hostName = hostName;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        // do heartbeat
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // read timeout
                System.out.println("read timeout: " + ctx.channel() + "\t" +
                        ChannelManagerServerSingle
                        .getChannelManagerMap().get(hostName).size());
                ChannelManagerServerSingle.remove(hostName, ctx.channel());
            } else if (event.state() == IdleState.WRITER_IDLE) {
                // write timeout
                System.out.println("WRITER_IDLE timeout");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // timeout
                System.out.println("ALL_IDLE timeout");

            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager manager = new ServerChannelManager(server, ctx.channel(), hostName);
        ChannelManagerServerSingle.put(hostName, manager);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        receivedHandle.received(ctx.channel(), msg);
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
        ChannelManagerServerSingle.remove(hostName, ctx.channel());
        System.out.println("close connection: " + ctx.channel() + "\t" +
                ChannelManagerServerSingle.getChannelManagerMap().get(hostName).size());

    }
}
