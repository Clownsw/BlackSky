package cn.smilex;

import cn.smilex.blacksky.jni.ssl.Ssl;
import org.junit.Test;

/**
 * @author smilex
 */
public class SslTest {

    @Test
    public void sha() {
        // ecb252044b5ea0f679ee78ec1a12904739e2904d
        assert Ssl.sha.sha1_string("string").equals("ecb252044b5ea0f679ee78ec1a12904739e2904d");

        // 474b4afcaa4303cfc8f697162784293e812f12e2842551d726db8037
        assert Ssl.sha.sha224_string("string").equals("474b4afcaa4303cfc8f697162784293e812f12e2842551d726db8037");

        // 473287f8298dba7163a897908958f7c0eae733e25d2e027992ea2edc9bed2fa8
        assert Ssl.sha.sha256_string("string").equals("473287f8298dba7163a897908958f7c0eae733e25d2e027992ea2edc9bed2fa8");

        // 36396a7e4de3fa1c2156ad291350adf507d11a8f8be8b124a028c5db40785803ca35a7fc97a6748d85b253babab7953e
        assert Ssl.sha.sha384_string("string").equals("36396a7e4de3fa1c2156ad291350adf507d11a8f8be8b124a028c5db40785803ca35a7fc97a6748d85b253babab7953e");

        // 2757cb3cafc39af451abb2697be79b4ab61d63d74d85b0418629de8c26811b529f3f3780d0150063ff55a2beee74c4ec102a2a2731a1f1f7f10d473ad18a6a87
        assert Ssl.sha.sha512_string("string").equals("2757cb3cafc39af451abb2697be79b4ab61d63d74d85b0418629de8c26811b529f3f3780d0150063ff55a2beee74c4ec102a2a2731a1f1f7f10d473ad18a6a87");
    }
}
