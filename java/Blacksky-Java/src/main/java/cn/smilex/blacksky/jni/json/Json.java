package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;

import java.util.ArrayList;
import java.util.List;

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
        return (String) _get(Json_Type.STRING.id, name, JSON_GET_METHOD.DEFAULT.id, 0);
    }

    protected String asString(long address) {
        return (String) _get(Json_Type.STRING.id, null, JSON_GET_METHOD.ADDRESS.id, address);
    }

    protected String getString(String name, long address) {
        synchronized (Json.class) {
            if (address == 0) {
                return null;
            }
            return (String) _get(Json_Type.STRING.id, name,JSON_GET_METHOD.NAME_ADDRESS.id, address);
        }
    }

    public int getInt(String name) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, name, JSON_GET_METHOD.DEFAULT.id, 0));
    }

    protected int asInt(long address) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected int getInt(String name, long address) {
        synchronized (Json.class) {
            return Integer.parseInt((String) _get(Json_Type.INTEGER.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
        }
    }

    public double getDouble(String name) {
        return Double.parseDouble((String)_get(Json_Type.INTEGER.id, name, JSON_GET_METHOD.DEFAULT.id, 0));
    }

    protected double asDouble(long address) {
        return Double.parseDouble((String)_get(Json_Type.DOUBLE.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected double getDouble(String name, long address) {
        synchronized (Json.class) {
            return Double.parseDouble((String) _get(Json_Type.DOUBLE.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
        }
    }

    public long getLong(String name) {
        return Long.parseLong((String) _get(Json_Type.LONG.id, name, JSON_GET_METHOD.DEFAULT.id, 0));
    }

    protected long asLong(long address) {
        return Long.parseLong((String)_get(Json_Type.LONG.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected long getLong(String name, long address) {
        synchronized (Json.class) {
            return Long.parseLong((String)_get(Json_Type.LONG.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
        }
    }

    public JsonObject getObject(String name) {
        synchronized (Json.class) {
            String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.DEFAULT.id, 0);
            if (_address == null) {
                return null;
            }
            return new JsonObject(Long.parseLong(_address));
        }
    }

    public List<JsonObject> getArrJsonObject(String name) {
        synchronized (Json.class) {
            long[] _addresses = _getArray(name, true, 0);
            if (_addresses == null) {
                return null;
            }

            List<JsonObject> jsonObjects = new ArrayList<>();
            for (long address : _addresses) {
                jsonObjects.add(new JsonObject(address));
            }
            return jsonObjects;
        }
    }

    protected List<JsonObject> getArrJsonObject(String name, long address) {
        synchronized (Json.class) {
            long[] _addresses = _getArray(name, false, address);
            if (_addresses == null) {
                return null;
            }

            List<JsonObject> jsonObjects = new ArrayList<>();
            for (long _address : _addresses) {
                jsonObjects.add(new JsonObject(_address));
            }
            return jsonObjects;
        }
    }

    protected JsonObject getObject(String name, long address) {
        synchronized (Json.class) {
            String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address);
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
        LONG(3),
        OBJECT(4);

        int id;
        Json_Type(int id) {
            this.id = id;
        }
    }

    private enum JSON_GET_METHOD {
        DEFAULT(0),
        NAME_ADDRESS(1),
        ADDRESS(2);

        int id;
        JSON_GET_METHOD(int id) {
            this.id = id;
        }
    }

    private native long _create(String jsonStr);

    private native Object _get(int type, String name, int isRoot, long address);

    private native long[] _getArray(String name, boolean isRoot, long address);

    private native void _close(long address);
}
