package cn.smilex.libhv.jni;

import java.util.HashMap;

/**
 * @author smilex
 */
public class HttpResponse {
    private String body;
    private int statusCode;
    private HashMap<String, String> cookies;
    private HashMap<String, String> headers;

    public HttpResponse() {
        cookies = new HashMap<>();
        headers = new HashMap<>();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
