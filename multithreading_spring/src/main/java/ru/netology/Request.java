package ru.netology;

import javafx.beans.binding.StringBinding;
import lombok.Data;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.beans.Introspector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Request {

    private Map<String, String> queryParameters = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private BufferedReader in;
    private String method;
    private String path;
    private String fullUrl;

    public Request(BufferedReader in) {
        this.in = in;
    }

    public String getParameter(String paramName) {
        return queryParameters.get(paramName);
    }

    public InputStream getBody() throws IOException {
        return new HttpInputStream(in, headers);
    }

    public List<String> getQueryParamValues(String paramName) {
        return queryParameters.entrySet().stream()
                .filter(e -> e.getKey().equals(paramName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public String getQueryParam(String name) {
        return queryParameters.get(name);
    }

    public Map<String, String> getQueryParams() {
        return Collections.unmodifiableMap(queryParameters); // Защищаем от изменений
    }

    public void parseQueryRequest(String rawRequest) {
        if (rawRequest == null || rawRequest.isEmpty()) return;
        List<NameValuePair> params = URLEncodedUtils.parse(rawRequest, StandardCharsets.UTF_8);
        for (NameValuePair param : params) {
            queryParameters.put(param.getName(), param.getValue());
        }
    }

    public boolean parse() throws IOException, ServletException {
        String initialLine = in.readLine();
        if (initialLine == null || initialLine.isEmpty()) {
            return false;
        }
        StringTokenizer tok = new StringTokenizer(initialLine);
        String[] components = new String[3];
        for (int i = 0; i < components.length; i++) {
            if (tok.hasMoreTokens()) {
                components[i] = tok.nextToken();
            } else {
                return false;
            }
        }
        method = components[0];
        System.out.println(method);
        for (String component : components) {
            System.out.println(component);

        }
        fullUrl = components[1];


        if(fullUrl.contains("?")){
            path = fullUrl.substring(0, fullUrl.indexOf("?"));
            parseQueryRequest(fullUrl.substring(fullUrl.indexOf("?")));
        }else {
            path = fullUrl;
        }

        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
            int separator = headerLine.indexOf(":");
            if (separator > 0) {
                headers.put(
                        headerLine.substring(0, separator).trim(),
                        headerLine.substring(separator + 1).trim()
                );
            }
            System.out.println(headerLine);
        }

        if ("POST".equals(method) && headers.get("Content-Type").contains("x-www-form-urlencoded")) {
           int contentLenght = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
           if(contentLenght > 0){
               char[] body = new char[contentLenght];
               in.read(body, 0 , contentLenght);
               System.out.println(new String(body));
               parseQueryRequest(new String(body));
           }
        }else if ("POST".equals(method) && headers.get("Content-Type").contains("multipart/form-data")){
            int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
            if(contentLength > 0) {
                String contentType = headers.get("Content-Type");
                String boundary = "--" + contentType.split("boundary=")[1];
                pareMultipartData(contentLength, boundary);
            }
        }
        return true;

    }

    private void pareMultipartData(int contentLength, String boundary) throws IOException {
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[contentLength];
        in.read(buffer, 0, contentLength);
        String multipartData = new String(buffer);

        String[] parts = multipartData.split(boundary);

        for (String part: parts){
            if(part.contains("filename=")){
                System.out.println("test");
                String filename = part.substring(
                        part.indexOf("filename=\"") + 10,
                        part.indexOf("\"", part.indexOf("filename=\"") + 10)
                );
                int start = part.indexOf("\r\n\r\n") + 4;
                int end = part.lastIndexOf("\r\n--");
                byte[] fileContent = part.substring(start, end).getBytes(StandardCharsets.UTF_8);

                // Сохраняем файл
                Files.write(Paths.get("./" + filename), fileContent);
            }
        }
    }
}
