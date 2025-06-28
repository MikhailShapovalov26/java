package ru.netology;


import java.io.IOException;
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
//        server.addHandler("GET", "/*", (Handler) new FileHandler());
        server.start();


    }
}