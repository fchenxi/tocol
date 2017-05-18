package tocol.rpc.server;

public abstract class AbstractServer implements Server {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stop();
    }

}
