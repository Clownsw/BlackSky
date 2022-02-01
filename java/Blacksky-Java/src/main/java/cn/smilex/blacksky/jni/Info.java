package cn.smilex.blacksky.jni;

/**
 * @author smilex
 */
public class Info {
    public static boolean isInit;
    public static final String LIBRARY_NAME = "blacksky";

    static {
        synchronized (Info.class) {
            isInit = false;
        }
    }

    public static void init() {
        synchronized (Info.class) {
            if (!isInit) {
                System.loadLibrary(LIBRARY_NAME);
                isInit = true;
            }
        }
    }
}