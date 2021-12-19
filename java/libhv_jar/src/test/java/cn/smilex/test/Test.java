package cn.smilex.test;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.HttpResponse;
import cn.smilex.libhv.jni.Requests;

import java.util.HashMap;

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
        System.out.println(httpResponse.getCookies().size());
//        System.out.println(httpResponse.getStatusCode());
//        System.out.println(httpResponse.getBody());

//        test02();
    }

    @org.junit.Test
    public void test02() {
        Requests requests = new Requests();

        HashMap<String, String> hashTest = requests.getHashTest();
        System.out.println(hashTest.size());
        System.out.println(hashTest);
    }
}
