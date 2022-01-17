package cn.smilex.libhv.jni.ssl;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */
public class Base64 {

    static {
        synchronized (Base64.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    public native String base64_encode(String data, int len);
    public native String base64_decode(String data, int len);

}
