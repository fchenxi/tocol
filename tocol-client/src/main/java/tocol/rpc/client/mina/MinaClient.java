package tocol.rpc.client.mina;

import tocol.rpc.client.Client;
import tocol.rpc.client.load.Hosts;

public class MinaClient implements Client {

    private final Hosts host;

    public MinaClient(Hosts host) {
        super();
        this.host = host;
    }

    @Override
    public void connect() {
    }

    @Override
    public void doConnect() {
    }

    @Override
    public void stop() {
    }

}
