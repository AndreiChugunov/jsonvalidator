import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;


/**
 * Project jsonvalidator Created by Andrei.
 */
public class Main {
    public static void main(String[] args) {
        try {
            final HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(80), 0);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting().serializeNulls();
            final Gson gson = builder.create();
            HttpContext context = server.createContext("/", new HttpHandler() {
                int connectionId = 0;
                public void handle(HttpExchange httpExchange) throws IOException {
                    String responseString = null;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
                    String buildString = bufferedReader.readLine();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (buildString != null) {
                        stringBuilder.append(buildString);
                        buildString = bufferedReader.readLine();
                    }
                    String jsonString = stringBuilder.toString();
                    try {
                        Object object = gson.fromJson(jsonString, Object.class);
                        responseString = gson.toJson(object);
                    } catch (JsonSyntaxException ex) {
                        String[] errorSplittedString = ex.getMessage().split(".+: | at ");
                        responseString = gson.toJson(new JsonError(
                                connectionId, ex.hashCode(), errorSplittedString[1], "at " + errorSplittedString[2])
                        );
                    } finally {
                        connectionId += 1;
                    }
                    System.out.println(responseString);
                    httpExchange.sendResponseHeaders(200, responseString.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(responseString.getBytes());
                    os.close();
                }
            });
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
