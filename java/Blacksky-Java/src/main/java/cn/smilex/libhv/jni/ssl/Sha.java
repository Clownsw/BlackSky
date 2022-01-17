package cn.smilex.libhv.jni.ssl;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */
public class Sha {
    static {
        synchronized (Base64.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    enum SHA_TYPE {
        SHA1(0),
        SHA224(1),
        SHA256(2),
        SHA384(3),
        SHA512(4);

        int id;
        SHA_TYPE(int id) {
            this.id = id;
        }
    }

    public String sha1_string(String data) {
        return sha(SHA_TYPE.SHA1.id, data);
    }

    public String sha224_string(String data) {
        return sha(SHA_TYPE.SHA224.id, data);
    }

    public String sha256_string(String data) {
        return sha(SHA_TYPE.SHA256.id, data);
    }

    public String sha384_string(String data) {
        return sha(SHA_TYPE.SHA384.id, data);
    }

    public String sha512_string(String data) {
        return sha(SHA_TYPE.SHA512.id, data);
    }

    private native String sha(int type, String data);
}
