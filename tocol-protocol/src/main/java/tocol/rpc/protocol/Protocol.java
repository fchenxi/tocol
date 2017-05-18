package tocol.rpc.protocol;

public interface Protocol<T> {

    /**
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public void encoder(T arg0, Object arg1) throws Exception;

    /**
     * @param arg0
     * @param arg1
     * @return
     * @throws Exception
     */
    public Object decoder(T arg0) throws Exception;
}
