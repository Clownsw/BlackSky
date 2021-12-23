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
                .setUrl("https://www.baidu.com/")
                .setMethod(HttpRequest.HTTP_METHOD.HTTP_METHOD_GET.id);

        HttpResponse httpResponse = requests.request(request);
        System.out.println(httpResponse.getBody());
        HttpResponse httpResponse1 = requests.request(request);
        System.out.println(httpResponse1.getBody());

        String str = "admin";
        String md5 = Ssl.md5.getMD5(str, str.length());
        System.out.println(md5);
    }

    @org.junit.Test
    public void test02() {
        Requests requests = new Requests();
        HttpResponse httpResponse = requests.get("https://tenapi.cn/video/?url=https://www.douyin.com/video/7044802663178865956?previous_page=follow&tab_name=follow");
        System.out.println(httpResponse.getBody());
    }

    @org.junit.Test
    public void test03() {
        Requests requests = new Requests();
        HttpResponse post = requests.post("https://www.baidu.com/");
        System.out.println(post.getBody());
    }
}
