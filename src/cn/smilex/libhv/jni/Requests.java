package cn.smilex.libhv.jni;

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

    public native String request(HttpRequest request);
}
