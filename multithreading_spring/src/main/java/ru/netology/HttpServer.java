package ru.netology;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.SocketHandler;


public class HttpServer {
    private int port;
    private Handler defaultHandler = null;
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();

    public HttpServer(int port)  {
        this.port = port;
    }

    public void addHandler(String method, String path, Handler handler){
        Map<String, Handler> methodHandlers = handlers.get(method);
        if(methodHandlers == null){
            methodHandlers = new HashMap<>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("сервер запущен на порту: " + port);
        Socket client;
        while ((client = socket.accept())!= null){
            Server server = new Server(client, handlers);
            Thread t = new Thread(server);
            t.start();

        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(9999);
        server.addHandler("GET", "/messages", new Handler() {
            @Override
            public void handle(Request request, Response response) throws IOException {
                String html = "IT works, " + request.getParameter("name") + "";
                response.setResponseCode(100, "OK");
                response.addHeader("Content-Type", "test/html");
                response.addBody(html);
            }
        });
        server.addHandler("POST", "/messages", new Handler() {
            @Override
            public void handle(Request request, Response response) throws IOException {
                StringBuffer buffer = new StringBuffer();
                InputStream in = request.getBody();
                int c;
                while ((c = in.read())!=-1){
                    buffer.append((char) c);
                }
                String[] componetns = buffer.toString().split("&");
                Map<String, String> urlParameters = new HashMap<>();
                for( String component : componetns){
                    String[] pieces = component.split("=");
                    urlParameters.put(pieces[0], pieces[1]);
                }
                String html = "<body>Welcome, " + urlParameters.get("username") + "</body>";

                response.setResponseCode(200, "ОК");
                response.addHeader("Content-Type", "text/html");
                response.addBody(html);

            }
        });
        server.start();


    }
}