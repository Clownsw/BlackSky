package cn.smilex.libhv.jni;

import java.util.HashMap;

/**
 * @author smilex
 */
public class HttpRequest {
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_POST = 2;

    private String url;
    private int method;
    private String cookie;
    private HashMap<String, String> headers;
    private HashMap<String, String> params;

    private HttpRequest() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    public static HttpRequest build() {
        return new HttpRequest();
    }

    public HttpRequest setMethod(int method) {
        this.method = method;
        return this;
    }

    public HttpRequest setCookie(String cookie) {
        this.cookie = cookie;
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

    public HttpRequest setParams(String key, String value) {
        params.put(key, value);
        return this;
    }
}
