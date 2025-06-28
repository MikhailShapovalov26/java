package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable {
    private Socket socket;
    private boolean run = true;
    private List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

    private Handler defaultHandler;
    private Map<String, Map<String, Handler>> handlers;

    public Server(Socket socket,
                  Map<String, Map<String, Handler>> handlers
    ) throws IOException {
        this.socket = socket;
        this.handlers = handlers;
    }

    private void respond(int statusCode, String msg, OutputStream out) throws IOException {
        String responseLine = "HTTP/1.1 " + statusCode + " " + msg + "\r\n\r\n";
        out.write(responseLine.getBytes());
    }

    @Override
    public void run() {
        BufferedReader in = null;
        OutputStream out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();
            Request request = new Request(in);
            if (!request.parse()) {
                respond(500, "Не смогли распарсить", out);
                return;
            }
            AtomicBoolean foundHandler = new AtomicBoolean(false);
            Response response = new Response(out);
            Map<String, Handler> methodHanlers = handlers.get(request.getMethod());
            if (methodHanlers == null) {
                respond(405, "Метод не поддерживается", out);
                return;
            }

            methodHanlers.keySet().forEach(handlerPath -> {
                if (handlerPath.equals(request.getPath())) {
                    try {
                        methodHanlers.get(request.getPath()).handle(request, response);
                        response.send();
                        foundHandler.set(true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            if(!foundHandler.get()){
                if(methodHanlers.get("/*") !=null){
                    methodHanlers.get("/*").handle(request, response);
                    response.send();
                }else {
                    respond(404, "Not Found", out);
                }
            }
        } catch (IOException e) {
            try {
                respond(500, e.toString(), out);
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                try{
                    if(out !=null){
                        out.close();
                    }
                    if(in != null){
                        in.close();
                    }
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }

    public void shutdown() {
        this.run = false;
    }


}
