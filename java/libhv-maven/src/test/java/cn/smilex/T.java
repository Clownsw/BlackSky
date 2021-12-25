package cn.smilex;

import cn.smilex.libhv.jni.ssl.Ssl;
import org.junit.Test;

/**
 * Test Class
 * @author smilex
 */
public class T {

    @Test
    public void t1() throws InterruptedException {
        for (int i = 0; i < 500000; i++) {
            String s = "0123456789";
            String ret = Ssl.base64.base64_encode(s, s.length());
            System.out.println(ret);

            if (ret == null) {
                ret = "";
            }

            String ret1 = Ssl.base64.base64_decode(ret, ret.length());
            System.out.println(ret1);
        }

        for (int i = 0; i < 5000; i++) {
            Thread.sleep(1000);
        }
    }

}
