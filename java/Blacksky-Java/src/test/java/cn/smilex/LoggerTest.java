package cn.smilex;

import cn.smilex.blacksky.jni.log.Logger;
import org.junit.Test;

public class LoggerTest {

    public static Logger logger;

    static {
        logger = Logger.build("application", "run.log");
    }

    /**
     * set_level
     */
    @Test
    public void test02() {
        logger.set_level(true, Logger.LOG_LEVEL.DEBUG.id);
        logger.set_level(false, Logger.LOG_LEVEL.DEBUG.id);

        logger.info("info-aabbccdd");
        logger.flush();

        logger.trace("trace-aabbccdd");
        logger.flush();

        logger.debug("debug-aabbccdd");
        logger.flush();
    }

    /**
     * flush_on
     */
    @Test
    public void test03() {
        logger.set_level(true, Logger.LOG_LEVEL.DEBUG.id);
        logger.set_level(false, Logger.LOG_LEVEL.DEBUG.id);

        logger.flushOn(Logger.LOG_LEVEL.DEBUG.id);

        logger.info("info-aabbccdd");

        logger.trace("trace-aabbccdd");

        logger.debug("debug-aabbccdd");
    }
}
