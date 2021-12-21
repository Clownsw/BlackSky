package cn.smilex.libhv.jni.fmt;

/**
 * @author smilex
 */
public class Fmt {
    public static Fmt fmt;

    static {
        System.loadLibrary("libhv");
        fmt = new Fmt();
    }

    private Fmt() {}

    public native String format(String first, String... args);
}
