package cn.smilex.libhv.jni.ssl;

import cn.smilex.libhv.jni.Info;
import cn.smilex.libhv.jni.http.Requests;

/**
 * @author smilex
 */
public class MD5 {

    static {
        synchronized (Requests.class) {
            System.loadLibrary(Info.LIBRARY_NAME);
        }
    }

    public native String getMD5(String data, int length);
}
