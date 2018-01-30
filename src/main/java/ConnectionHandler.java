import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


/**
 * Project jsonvalidator Created by Andrei.
 */
public class ConnectionHandler implements HttpHandler {
    private int connectionId;
    private final GsonBuilder builder = new GsonBuilder();
    private Gson gson;

    {
        connectionId = 0;
        builder.setPrettyPrinting().serializeNulls();
        gson = builder.create();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String responseString;
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
            System.out.println(ex.getMessage());
            String[] errorSplittedString = ex.getMessage().split(".+: | at ");
            responseString = gson.toJson(new JsonError(
                    connectionId,
                    ex.hashCode(),
                    errorSplittedString[1].replaceAll("\\u0027", ""),
                    "at " + errorSplittedString[2])
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
}
