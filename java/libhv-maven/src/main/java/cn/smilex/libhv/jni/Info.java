package cn.smilex.libhv.jni;

/**
 * @author smilex
 */
public class Info {
    public static boolean isInit;

    static {
        synchronized (Info.class) {
            isInit = false;
        }
    }

    public static void init() {
        synchronized (Info.class) {
            String osName = System.getProperty("os.name");
            String libraryName;

            if (osName.contains("Windows")) {
                libraryName = "libhv";
            } else {
                libraryName = "hv";
            }

            System.loadLibrary(libraryName);
            isInit = true;
        }
    }
}
