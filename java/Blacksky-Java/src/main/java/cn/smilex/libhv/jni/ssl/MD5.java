package cn.smilex.libhv.jni.ssl;

import cn.smilex.libhv.jni.Info;
import cn.smilex.libhv.jni.http.Requests;

/**
 * @author smilex
 */
public class MD5 {

    static {
        synchronized (Requests.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    public native String getMD5(String data, int length);
}
