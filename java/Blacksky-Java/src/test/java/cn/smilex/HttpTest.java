package cn.smilex;

import cn.smilex.blacksky.jni.http.HttpRequest;
import cn.smilex.blacksky.jni.http.HttpResponse;
import cn.smilex.blacksky.jni.http.Requests;
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
        HttpResponse response = Requests.send(httpRequest);
        System.out.println(response.getBody());
    }

    @Test
    public void asyncSend() {
        HttpRequest httpRequest =
                HttpRequest.build()
                        .setUrl("https://www.baidu.com")
                        .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                        .setBody("123");
        HttpResponse response = Requests.asyncSend(httpRequest);
        System.out.println(response.getBody());
    }

    @Test
    public void sendGetUrlTest() {
        HttpRequest httpRequest = HttpRequest.build()
                .setUrl("https://www.baidu.com")
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                .setParams("a", "1")
                .setParams("b", "2")
                .setParams("c", "3");

        HttpResponse httpResponse = Requests.send(httpRequest);
    }

    @Test
    public void testContentLength() {
        HttpRequest httpRequest = HttpRequest.build()
                .setUrl("https://www.baidu.com")
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id);

        HttpResponse httpResponse = Requests.send(httpRequest);
        if (httpResponse != null) {
            long contentLength = httpResponse.getContentLength();
            System.out.println(contentLength);
        }
    }

}
