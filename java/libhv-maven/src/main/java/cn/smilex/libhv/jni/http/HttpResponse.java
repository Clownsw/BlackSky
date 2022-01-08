package cn.smilex.libhv.jni.http;

import lombok.Data;

import java.util.HashMap;

/**
 * @author smilex
 */
@Data
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
}
