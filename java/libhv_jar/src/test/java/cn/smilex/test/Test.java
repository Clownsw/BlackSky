package cn.smilex.test;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.HttpResponse;
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
                .setUrl("https://www.baidu.com/")
                .setMethod(HttpRequest.HTTP_METHOD_GET);
        HttpResponse httpResponse = requests.request(request);
        HttpResponse httpResponse1 = requests.request(request);
    }
}
