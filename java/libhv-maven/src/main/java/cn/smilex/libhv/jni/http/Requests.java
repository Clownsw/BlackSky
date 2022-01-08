package cn.smilex.libhv.jni.http;

import cn.smilex.libhv.jni.Info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author smilex
 */
public class Requests {

    static {
        synchronized (Requests.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    private Requests() {  }

    /**
     * 以Get方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     */
    public static HttpResponse get(String url) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id);
        return request(httpRequest);
    }

    /**
     * 以Get方式请求网站并返回结果
     * @param url url
     * @param headers headers
     * @return 返回结果
     */
    public static HttpResponse get(String url, HashMap<String, String> headers) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                .setHeaders(headers);
        return request(httpRequest);
    }

    /**
     * 以Post方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     *
     */
    public static HttpResponse post(String url) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_POST.id);
        return request(httpRequest);
    }

     /**
      * 以Post方式请求网站并返回结果
      * @param url url
      * @param headers header
      * @return 返回结果
     */
     public static HttpResponse post(String url, HashMap<String, String> headers) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_POST.id)
                .setHeaders(headers);
        return request(httpRequest);
     }


     public static HttpResponse send(HttpRequest request) {
        urlDealWith(request);
        return request(request);
     }

     public static HttpResponse asyncSend(HttpRequest request) {
        urlDealWith(request);
        return asyncRequest(request);
     }

    private static void urlDealWith(HttpRequest request) {
        if (request.getMethod() == HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id) {
            HashMap<String, String> params = request.getParams();

            if (params.size() >= 1) {
                int i = 0;
                char s;
                Set<String> keySet = params.keySet();
                Iterator<String> keySetIter = keySet.iterator();

                while (keySetIter.hasNext()) {
                    String key = keySetIter.next();
                    String value = params.get(key);

                    if (i == 0) {
                        s = '?';
                    } else {
                        s = '&';
                    }
                    request.setUrl(request.getUrl() + s + key + "=" + value);
                    i++;
                }
            }
        }
    }

    /**
     * 同步请求指定网站
     * @param request 请求对象
     * @return 响应对象
     */
    private static native HttpResponse request(HttpRequest request);

    /**
     * 异步请求指定网站
     * @param request 请求对象
     * @return 响应对象
     */
    private static native HttpResponse asyncRequest(HttpRequest request);
}
