package cn.smilex.libhv.jni.log;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */

public class Logger {
    private static Logger logger;

    static {
        synchronized (Logger.class) {
            if (!Info.isInit) {
                Info.init();
            }
            logger = new Logger();
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public enum LOG_LEVEL {
        INFO(1),
        WARN(2);
        private int id;

        LOG_LEVEL(int id) {
            this.id = id;
        }
    };

    public void info(String message) {
        log(LOG_LEVEL.INFO.id, message);
    }

    public void warn(String message) {
        log(LOG_LEVEL.WARN.id, message);
    }

    public native void log(int logLevel, String message);
    public native void flush();
    public native void createFileLogger(String loggerName, String fileName);
}
