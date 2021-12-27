package cn.smilex.libhv.jni.http;

import java.util.HashMap;

/**
 * @author smilex
 */
public class HttpRequest {
    public enum HTTP_METHOD {
        HTTP_METHOD_GET(1),
        HTTP_METHOD_HEAD(2),
        HTTP_METHOD_POST(3),
        HTTP_METHOD_PUT(4),
        HTTP_METHOD_CONNECT(5),
        HTTP_METHOD_OPTIONS(6),
        HTTP_METHOD_TRACE(7),
        HTTP_METHOD_COPY(8),
        HTTP_METHOD_LOCK(9),
        HTTP_METHOD_MKCOL(10),
        HTTP_METHOD_MOVE(11);

        public final int id;

        private HTTP_METHOD(int id) {
            this.id = id;
        }
    };

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

    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest setMethod(int method) {
        this.method = method;
        return this;
    }

    public HttpRequest setCookie(String cookie) {
        this.cookie = cookie;
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
