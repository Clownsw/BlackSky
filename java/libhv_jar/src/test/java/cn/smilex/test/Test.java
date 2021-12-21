package cn.smilex.test;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.HttpResponse;
import cn.smilex.libhv.jni.Requests;
import cn.smilex.libhv.jni.fmt.Fmt;

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
        System.out.println(Fmt.fmt.format("", ""));
    }

    @org.junit.Test
    public void test02() {
        System.out.println(Fmt.fmt.format("a={}, b={}, c={}", "1", "2", "3"));

    }
}
