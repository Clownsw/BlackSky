package cn.smilex.libhv.jni.ssl;

/**
 * @author smilex
 */
public class Base64 {

    public native String base64_encode(String data, int len);
    public native String base64_decode(String data, int len);

}
