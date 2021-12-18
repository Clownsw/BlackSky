package cn.smilex;

import cn.smilex.libhv.jni.HttpRequest;
import cn.smilex.libhv.jni.Requests;

public class Test {
    public static void main(String[] args) {
        Requests requests = new Requests();
        System.out.println(requests.request(new HttpRequest()
                .setUrl("https://www.smilex.cn/")
                .setMethod(1)));
    }
}
