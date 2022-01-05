package cn.smilex;

import cn.smilex.libhv.jni.log.Logger;

public class T2 {
    public static Logger logger;

    static {
        logger = Logger.build("application", "run.log");
    }

    public static void main(String[] args) {

        logger.info("1");
        logger.flush();
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        logger.info("2");
//        logger.flush();
    }
}
