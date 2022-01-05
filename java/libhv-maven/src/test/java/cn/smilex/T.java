package cn.smilex;

import cn.smilex.libhv.jni.http.HttpRequest;
import cn.smilex.libhv.jni.http.HttpResponse;
import cn.smilex.libhv.jni.http.Requests;
import cn.smilex.libhv.jni.ssl.Ssl;
import org.junit.Test;

/**
 * Test Class
 * @author smilex
 */
public class T {

    @Test
    public void t1() throws InterruptedException {
        for (int i = 0; i < 500000; i++) {
            String s = "0123456789";
            String ret = Ssl.base64.base64_encode(s, s.length());
            System.out.println(ret);

            if (ret == null) {
                ret = "";
            }

            String ret1 = Ssl.base64.base64_decode(ret, ret.length());
            System.out.println(ret1);
        }

        for (int i = 0; i < 5000; i++) {
            Thread.sleep(1000);
        }
    }

    @Test
    public void t2() {
        HttpRequest httpRequest =
                HttpRequest.build()
                .setUrl("https://www.baidu.com")
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                .setBody("123");

        HttpResponse response = Requests.getRequests().request(httpRequest);

    }

    @Test
    public void t3() {
        HttpRequest httpRequest =
                HttpRequest.build()
                        .setUrl("https://www.baidu.com")
                        .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id)
                        .setBody("123");
        HttpResponse response = Requests.getRequests().asyncRequest(httpRequest);
        System.out.println(response.getBody());
    }

    @Test
    public void t4() {
        // ecb252044b5ea0f679ee78ec1a12904739e2904d
        System.out.println(Ssl.sha.sha1_string("string"));

        // 474b4afcaa4303cfc8f697162784293e812f12e2842551d726db8037
        System.out.println(Ssl.sha.sha224_string("string"));

        // 473287f8298dba7163a897908958f7c0eae733e25d2e027992ea2edc9bed2fa8
        System.out.println(Ssl.sha.sha256_string("string"));

        // 36396a7e4de3fa1c2156ad291350adf507d11a8f8be8b124a028c5db40785803ca35a7fc97a6748d85b253babab7953e
        System.out.println(Ssl.sha.sha384_string("string"));

        // 2757cb3cafc39af451abb2697be79b4ab61d63d74d85b0418629de8c26811b529f3f3780d0150063ff55a2beee74c4ec102a2a2731a1f1f7f10d473ad18a6a87
        System.out.println(Ssl.sha.sha512_string("string"));
    }

}
