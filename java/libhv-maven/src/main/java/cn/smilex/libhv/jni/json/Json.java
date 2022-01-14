package cn.smilex.libhv.jni.json;

import cn.smilex.libhv.jni.Info;

/**
 * @author smilex
 */
public class Json {

    private long root_address;

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    protected Json() {  }

    public Json(String jsonStr) {
        root_address = _create(jsonStr);
    }

    public void close() {
        _close(root_address);
    }

    public String getString(String name) {
        return (String) _get(Json_Type.STRING.id, name, true,0);
    }

    protected String getString(String name, long address) {
        synchronized (Json.class) {
            if (address == 0) {
                return null;
            }
            return (String) _get(Json_Type.STRING.id, name,false, address);
        }
    }

    public int getInt(String name) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, name, true, 0));
    }

    protected int getInt(String name, long address) {
        synchronized (Json.class) {
            return Integer.parseInt((String) _get(Json_Type.INTEGER.id, name, false, address));
        }
    }

    public double getDouble(String name) {
        return Double.parseDouble((String)_get(Json_Type.INTEGER.id, name, true, 0));
    }

    protected double getDouble(String name, long address) {
        synchronized (Json.class) {
            return Double.parseDouble((String) _get(Json_Type.DOUBLE.id, name, false, address));
        }
    }

    public JsonObject getObject(String name) {
        synchronized (Json.class) {
            String _address = (String) _get(Json_Type.OBJECT.id, name, true, 0);
            if (_address == null) {
                return null;
            }
            return new JsonObject(Long.parseLong(_address));
        }
    }

    protected JsonObject getObject(String name, long address) {
        synchronized (Json.class) {
            String _address = (String) _get(Json_Type.OBJECT.id, name, false, address);
            if (_address == null) {
                return null;
            }
            return new JsonObject(Long.parseLong(_address));
        }
    }
    
    private enum Json_Type {
        STRING(0),
        INTEGER(1),
        DOUBLE(2),
        OBJECT(3);

        int id;
        Json_Type(int id) {
            this.id = id;
        }
    }

    private native long _create(String jsonStr);

    private native Object _get(int type, String name, boolean isRoot, long address);

    private native void _close(long address);
}
