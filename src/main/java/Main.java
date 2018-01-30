import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Project jsonvalidator Created by Andrei.
 */
public class Main {
    public static void main(String[] args) {
        try {
            final HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/", new ConnectionHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
