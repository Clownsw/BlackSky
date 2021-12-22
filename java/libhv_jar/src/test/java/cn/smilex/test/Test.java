package cn.smilex.test;

import cn.smilex.libhv.jni.http.HttpRequest;
import cn.smilex.libhv.jni.http.HttpResponse;
import cn.smilex.libhv.jni.http.Requests;
import cn.smilex.libhv.jni.ssl.Ssl;

/**
 * @author smilex
 */
public class Test {

    @org.junit.Test
    public void test01() {
        Requests requests = new Requests();
        HttpRequest request = HttpRequest
                .build()
                .setUrl("https://www.huya.com/")
                .setMethod(HttpRequest.HTTP_METHOD_GET);
        HttpResponse httpResponse = requests.request(request);
        HttpResponse httpResponse1 = requests.request(request);

        String str = "1234abcd";
        String md5 = Ssl.md5.getMD5(str, str.length());
        System.out.println(md5);
    }
}
