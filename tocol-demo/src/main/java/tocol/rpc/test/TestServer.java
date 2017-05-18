package tocol.rpc.test;

//import org.springframework.context.support.ClassPathXmlApplicationContext;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext-tocol-server.xml");
        context.start();
    }

}
