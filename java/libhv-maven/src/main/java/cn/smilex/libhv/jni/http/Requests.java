package cn.smilex.libhv.jni.http;

import cn.smilex.libhv.jni.Info;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author smilex
 */
public class Requests {

    private static final Requests requests;

    static {
        synchronized (Requests.class) {
            requests = new Requests();
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    private Requests() {  }
    public static Requests getRequests() {
        return requests;
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
        return Requests.getRequests().request(httpRequest);
    }

    /**
     * 以Get方式请求网站并返回结果
     * @param url url
     * @param headers headers
     * @return 返回结果
     */
    public HttpResponse get(String url, HashMap<String, String> headers) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                .setHeaders(headers);
        return Requests.getRequests().request(httpRequest);
    }

    /**
     * 以Post方式请求网站并返回结果
     * @param url url
     * @return 返回结果
     *
     */
    public HttpResponse post(String url) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_POST.id);
        return Requests.getRequests().request(httpRequest);
    }

     /**
      * 以Post方式请求网站并返回结果
      * @param url url
      * @param headers header
      * @return 返回结果
     */
     public HttpResponse post(String url, HashMap<String, String> headers) {
        HttpRequest httpRequest = HttpRequest
                .build()
                .setUrl(url)
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_POST.id)
                .setHeaders(headers);
        return Requests.getRequests().request(httpRequest);
     }


     public HttpResponse send(HttpRequest request) {
        urlDealWith(request);
        return request(request);
     }

     public HttpResponse asyncSend(HttpRequest request) {
        urlDealWith(request);
        return asyncRequest(request);
     }

    private void urlDealWith(HttpRequest request) {
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
    private native HttpResponse request(HttpRequest request);

    /**
     * 异步请求指定网站
     * @param request 请求对象
     * @return 响应对象
     */
    private native HttpResponse asyncRequest(HttpRequest request);
}
