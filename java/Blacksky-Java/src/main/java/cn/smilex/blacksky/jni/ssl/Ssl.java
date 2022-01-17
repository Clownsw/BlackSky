package cn.smilex.blacksky.jni.ssl;

/**
 * @author smilex
 */
public class Ssl {
    public static MD5 md5;
    public static Base64 base64;
    public static Sha sha;

    static {
        synchronized (Ssl.class) {
            md5 = new MD5();
            base64 = new Base64();
            sha = new Sha();
        }
    }
}
