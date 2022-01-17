package cn.smilex.blacksky.jni.ssl;

import cn.smilex.blacksky.jni.Info;
import cn.smilex.blacksky.jni.http.Requests;

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
