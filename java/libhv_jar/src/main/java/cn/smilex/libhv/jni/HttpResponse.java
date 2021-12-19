package cn.smilex.libhv.jni;

/**
 * @author smilex
 */
public class HttpResponse {
    private String body;
    private int statusCode;

    public HttpResponse() {}

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
}
