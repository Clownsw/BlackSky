package cn.smilex;

import cn.smilex.libhv.jni.http.HttpRequest;
import cn.smilex.libhv.jni.http.HttpResponse;
import cn.smilex.libhv.jni.http.Requests;
import cn.smilex.libhv.jni.log.Logger;
import cn.smilex.libhv.jni.ssl.Ssl;
import org.junit.Test;

/**
 * Test Class
 * @author smilex
 */
public class HttpTest {

    @Test
    public void send() {
        HttpRequest httpRequest =
                HttpRequest.build()
                        .setUrl("https://www.baidu.com")
                        .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                        .setBody("123");
        HttpResponse response = Requests.getRequests().request(httpRequest);
        System.out.println(response.getBody());
    }

    @Test
    public void asyncSend() {
        HttpRequest httpRequest =
                HttpRequest.build()
                        .setUrl("https://www.baidu.com")
                        .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                        .setBody("123");
        HttpResponse response = Requests.getRequests().asyncRequest(httpRequest);
        System.out.println(response.getBody());
    }
}
