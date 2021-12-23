package cn.smilex.libhv.jni.ssl;

/**
 * @author
 */
public class Ssl {
    public static MD5 md5;

    static {
        synchronized (Ssl.class) {
            md5 = new MD5();
        }
    }
}
