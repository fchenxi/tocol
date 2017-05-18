package tocol.rpc.protocol.handle;


public interface ReceivedHandle<T> {

    /**
     * 接收数据
     *
     * @param channel
     * @param obj
     */
    public Object received(T channel, Object obj);

}
