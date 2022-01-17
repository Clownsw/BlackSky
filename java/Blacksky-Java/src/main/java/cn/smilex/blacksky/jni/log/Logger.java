package cn.smilex.blacksky.jni.log;

import cn.smilex.blacksky.jni.Info;

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
        }
    }

    public static Logger build(String loggerName, String fileName) {
        if (logger == null) {
            logger = new Logger();
            logger.createFileLogger(loggerName, fileName);
        }

        return logger;
    }

    public enum LOG_LEVEL {
        TRACE(0),
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        CRITICAL(5);

        public int id;
        LOG_LEVEL(int id) {
            this.id = id;
        }
    };

    public enum PATTERN_TIME_TYPE {
        LOCAL(0),
        UTC(1);

        public int id;
        PATTERN_TIME_TYPE(int id) {
            this.id = id;
        }
    }

    public void trace(String message) { log(LOG_LEVEL.TRACE.id, message); }

    public void debug(String message) { log(LOG_LEVEL.DEBUG.id, message); }

    public void info(String message) {
        log(LOG_LEVEL.INFO.id, message);
    }

    public void warn(String message) {
        log(LOG_LEVEL.WARN.id, message);
    }

    public void error(String message) { log(LOG_LEVEL.ERROR.id, message); }

    public void critical(String message) { log(LOG_LEVEL.CRITICAL.id, message); }

    public void setPattern(boolean isFileLogger, String pattern) {
        set_pattern(isFileLogger, pattern, PATTERN_TIME_TYPE.LOCAL.id);
    }

    public void setPattern(boolean isFileLogger, String pattern, int pattern_type) {
        set_pattern(isFileLogger, pattern, pattern_type);
    }

    public void flushOn(int level) {
        flush_on(false, level);
    }

    public void flushOn(boolean isFileLogger, int level) {
        flush_on(isFileLogger, level);
    }

    public native void log(int logLevel, String message);
    public native void flush();
    private native void createFileLogger(String loggerName, String fileName);
    private native void set_pattern(boolean isFileLogger, String pattern, int pattern_type);
    public native void set_level(boolean isFileLogger, int level);

    // 设置全局级别刷新
    private native void flush_on(boolean isFileLogger, int level);
}
