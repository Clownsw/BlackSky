package cn.smilex.libhv.jni.json;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */
public class Json {

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    private final long address;

    public Json(String jsonStr) {
        address = _create(jsonStr);
    }

    public void close() {
        _close(address);
    }

    public String getString(String name) {
        return (String) _get(Json_Type.STRING.id, name);
    }

    public int getInt(String name) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, name));
    }

    public double getDouble(String name) {
        return Double.parseDouble((String)_get(Json_Type.INTEGER.id, name));
    }

    private enum Json_Type {
        STRING(0),
        INTEGER(1),
        DOUBLE(2);

        int id;
        Json_Type(int id) {
            this.id = id;
        }
    }

    private native long _create(String jsonStr);

    private native Object _get(int type, String name);

    private native void _close(long address);
}
