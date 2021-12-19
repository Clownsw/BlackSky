package cn.smilex.test;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.Requests;

/**
 * @author smilex
 */
public class Test {

    @org.junit.Test
    public void test01() {
        Requests requests = new Requests();
        HttpRequest request = HttpRequest
                .build()
                .setUrl("https://www.google.com/")
                .setMethod(HttpRequest.HTTP_METHOD_GET);

        String body = requests.request(request);
        System.out.println(body);
    }
}
