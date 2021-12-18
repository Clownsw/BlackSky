package cn.smilex.libhv.jni;

public class HttpRequest {
    private String url;
    private int method;

    public int getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
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
}
