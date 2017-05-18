package tocol.rpc.server.spring;

import tocol.rpc.common.BeanTocol;
import tocol.rpc.server.load.ServiceLoad;

public class SpringServer {

    private BeanTocol beanTool;

    public void init() {
        // start server
        ServiceLoad.init(beanTool);
    }

    public SpringServer(BeanTocol beanTool) {
        super();
        this.beanTool = beanTool;
    }
}
