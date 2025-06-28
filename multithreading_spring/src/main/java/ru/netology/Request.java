package ru.netology;

import lombok.Data;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
public class Request {

    private Map<String, String> queryParameters = new HashMap<String, String>();
    private Map<String, String> headers = new HashMap<String, String>();
    private BufferedReader in;
    private String method;
    private String path;
    private String fullUrl;

    public Request(BufferedReader in)  {
        this.in = in;
    }
    public String getParameter(String paramName)  {
        return queryParameters.get(paramName);
    }
    public InputStream getBody() throws IOException {
        return new HttpInputStream(in, headers);
    }

    public List<String> getQueryParams(String path){
        if(path.isEmpty()){
            return Collections.emptyList();
        }
        final List<String> parameterList = new ArrayList<>();
        CloseableHttpClient httpClient  = HttpClients.createDefault();
        String  queryString  =  "name1=value1";
        List<NameValuePair> params = URLEncodedUtils.parse(queryString, StandardCharsets.UTF_8);


        return null;
    }

    public void parseQueryRequest(String rawRequest) {
        for (String parameter : rawRequest.split("&")) {
            int separator = parameter.indexOf('=');
            if (separator > -1) {
                queryParameters.put(parameter.substring(0, separator),
                        parameter.substring(separator + 1));
            } else {
                queryParameters.put(parameter, null);
            }
        }
        queryParameters.forEach((key, value) ->
                System.out.println(key + value));
    }
    public boolean parse() throws IOException {
        String initialLine = in.readLine();



        StringTokenizer tok = new StringTokenizer(initialLine);
        String[] components = new String[3];
        System.out.println(initialLine);
        for (int i = 0; i < components.length; i++)  {
            if (tok.hasMoreTokens())  {
                components[i] = tok.nextToken();
            } else  {
                return false;
            }
        }
        method = components[0];
        fullUrl = components[1];

        while (true){
            String headerLine = in.readLine();
            if(headerLine.isEmpty()){
                break;
            }
            int separator = headerLine.indexOf(":");
            if(separator ==-1){
                return false;
            }
            headers.put(headerLine.substring(0,separator),
                    headerLine.substring(separator+1));
            if(!components[1].contains("?")){
                path = components[1];
            }else {
                path = components[1].substring(9,components[1].indexOf("?"));
                parseQueryRequest(components[1].substring(
                        components[1].indexOf("?") +1));
                if("/".equals(path)){
                    path = "/index.html";
                }
                System.out.println(true);

            }
        }
        queryParameters.forEach((key,value) ->
                System.out.println(key + ": " + value));
        return true;

    }
}
