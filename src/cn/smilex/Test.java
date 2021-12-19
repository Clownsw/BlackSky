package cn.smilex;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.Requests;

public class Test {
    public static void main(String[] args) {
        Requests requests = new Requests();
        HttpRequest req = new HttpRequest()
                .setUrl("https://www.smilex.cn/")
                .setMethod(HttpRequest.HTTP_METHOD_GET)
                .setHeaders("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                .setHeaders("Accept", "*/*");

        while (true) {
            requests.request(req);
        }
    }
}
