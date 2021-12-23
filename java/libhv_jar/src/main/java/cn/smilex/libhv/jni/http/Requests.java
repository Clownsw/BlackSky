package cn.smilex.libhv.jni.http;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */
public class Requests {

    static {
        synchronized (Requests.class) {
            System.loadLibrary(Info.LIBRARY_NAME);
        }
    }

    /**
     * 以Get方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     */
    public HttpResponse get(String url) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id);
        return new Requests().request(httpRequest);
    }

    /**
     * 以Post方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     */
    public HttpResponse post(String url) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_POST.id);
        return new Requests().request(httpRequest);
    }

    /**
     * 以Post方式请求网站并返回结果
     * @param request 请求对象
     * @return 返回响应对象
     */
    public native HttpResponse request(HttpRequest request);
}
