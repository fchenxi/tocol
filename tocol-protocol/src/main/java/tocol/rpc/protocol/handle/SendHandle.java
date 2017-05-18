package tocol.rpc.protocol.handle;

public interface SendHandle<T> {

    /**
     * 发送数据
     *
     * @param channel
     * @param obj
     */
    public void send(T channel, Object obj);

}
