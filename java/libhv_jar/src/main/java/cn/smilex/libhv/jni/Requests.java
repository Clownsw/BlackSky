package cn.smilex.libhv.jni;

/**
 * @author smilex
 */
public class Requests {

    static {
        System.loadLibrary("libhv");
    }

    /**
     * 以Get方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     */
    public native String get(String url);

    /**
     * 以Post方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     */
    public native String post(String url);

    /**
     * 以Post方式请求网站并返回结果
     * @param request 请求对象
     * @return 返回响应对象
     */
    public native HttpResponse request(HttpRequest request);
}
