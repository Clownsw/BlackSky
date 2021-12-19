package cn.smilex.libhv.jni;

import java.util.HashMap;

public class HttpRequest {
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_POST = 2;

    private String url;
    private int method;
    private HashMap<String, String> headers;

    public HttpRequest() {
        headers = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public int getMethod() {
        return method;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public static int getHttpMethodGet() {
        return HTTP_METHOD_GET;
    }

    /**
     * 1 = GET
     * 2 = POST
     * @param method 请求方式
     */
    public HttpRequest setMethod(int method) {
        this.method = method;
        return this;
    }

    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest setHeaders(String key, String value) {
        headers.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", headers=" + headers +
                '}';
    }
}
