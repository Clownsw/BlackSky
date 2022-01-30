package cn.smilex.blacksky.jni.http;

import java.util.HashMap;

/**
 * @author smilex
 */
public class HttpResponse {
    private String body;
    private int statusCode;
    private HashMap<String, String> cookies;
    private HashMap<String, String> headers;
    private String contentType;
    private long contentLength;

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "body='" + body + '\'' +
                ", statusCode=" + statusCode +
                ", cookies=" + cookies +
                ", headers=" + headers +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                '}';
    }
}
