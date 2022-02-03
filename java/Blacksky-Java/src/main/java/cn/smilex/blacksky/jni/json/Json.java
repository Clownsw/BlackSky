package cn.smilex.blacksky.jni.json;

import cn.smilex.blacksky.jni.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smilex
 */
public class Json {

    private static final String key = "__acJjzPifrs__";
    private long _address;
    private long rootAddress;
    private long pointAddress;

    static {
        synchronized (Json.class) {
            if (!Info.isInit) {
                Info.init();
            }
        }
    }

    private void _parse(String s) {
        String a = s.substring(0, s.indexOf("+"));
        _address = Long.parseLong(a);
        String b = s.substring(a.length() + 1);
        rootAddress = Long.parseLong(b);
    }

    private Json() {  }

    public Json(String jsonStr) {
        synchronized (Json.class) {
            _parse(_create(jsonStr));
        }
    }

    public void close() {
        synchronized (Json.class) {
            _close(_address);
        }
    }

    public Json getPoint(String point) {
        synchronized (Json.class) {
            pointAddress = _getPoint(rootAddress, point);
            return this;
        }
    }

    public int asPointerInt() {
        return Integer.parseInt((String) _get(Json_Type.INTEGER.id, null, JSON_GET_METHOD.ADDRESS.id, pointAddress));
    }

    public String asPointerString() {
        return (String) _get(Json_Type.STRING.id, null, JSON_GET_METHOD.ADDRESS.id, pointAddress);
    }

    public double asPointerDouble() {
        return Double.parseDouble((String) _get(Json_Type.DOUBLE.id, null, JSON_GET_METHOD.ADDRESS.id, pointAddress));
    }

    public long asPointerLong() {
        return Long.parseLong((String) _get(Json_Type.LONG.id, null, JSON_GET_METHOD.ADDRESS.id, pointAddress));
    }

    public boolean asPointerBoolean() {
        return Boolean.parseBoolean((String) _get(Json_Type.BOOLEAN.id, null, JSON_GET_METHOD.ADDRESS.id, pointAddress));
    }

    public JsonObject asPointerObject() {
        return new JsonObject(pointAddress);
    }

    /**
     * 通过K 获取 V(String)
     * @param name K
     * @return V
     */
    public String getString(String name) {
        return (String) _get(Json_Type.STRING.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress);
    }

    protected String _asString(long address) {
        return (String) _get(Json_Type.STRING.id, null, JSON_GET_METHOD.ADDRESS.id, address);
    }

    protected String _getString(String name, long address) {
        if (address == 0) {
            return null;
        }
        return (String) _get(Json_Type.STRING.id, name,JSON_GET_METHOD.NAME_ADDRESS.id, address);
    }

    /*
    * Int
    * */
    public int getInt(String name) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress));
    }

    protected int _asInt(long address) {
        return Integer.parseInt((String)_get(Json_Type.INTEGER.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected int _getInt(String name, long address) {
        return Integer.parseInt((String) _get(Json_Type.INTEGER.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
    }

    /*
    * Double
    * */
    public double getDouble(String name) {
        return Double.parseDouble((String)_get(Json_Type.DOUBLE.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress));
    }

    protected double _asDouble(long address) {
        return Double.parseDouble((String)_get(Json_Type.DOUBLE.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected double _getDouble(String name, long address) {
        return Double.parseDouble((String) _get(Json_Type.DOUBLE.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
    }

    /*
    * Long
    * */
    public long getLong(String name) {
        return Long.parseLong((String) _get(Json_Type.LONG.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress));
    }

    protected long _asLong(long address) {
        return Long.parseLong((String)_get(Json_Type.LONG.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected long _getLong(String name, long address) {
        return Long.parseLong((String)_get(Json_Type.LONG.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
    }

    /*
    * Boolean
    * */
    public boolean getBoolean(String name) {
        return Boolean.parseBoolean((String) _get(Json_Type.BOOLEAN.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress));
    }

    protected boolean _asBoolean(long address) {
        return Boolean.parseBoolean((String)_get(Json_Type.BOOLEAN.id, null, JSON_GET_METHOD.ADDRESS.id, address));
    }

    protected boolean _getBoolean(String name, long address) {
        return Boolean.parseBoolean((String)_get(Json_Type.BOOLEAN.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address));
    }

    public List<JsonObject> asRootArrJsonObject() {
        long[] _addresses = _getArray(null, true, _address);

        List<JsonObject> jsonObjects = new ArrayList<>();
        for (long address : _addresses) {
            jsonObjects.add(new JsonObject(address));
        }
        return jsonObjects;
    }

    protected List<JsonObject> _asPointArrJsonObject(long address) {
        long[] _addresses = _getArray(key, true, address);

        List<JsonObject> jsonObjects = new ArrayList<>();
        for (long _address : _addresses) {
            jsonObjects.add(new JsonObject(_address));
        }
        return jsonObjects;
    }

    public List<JsonObject> asPointArrJsonObject() {
        return _asPointArrJsonObject(pointAddress);
    }

    public JsonObject getObject(String name) {
        String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.DEFAULT.id, rootAddress);
        if (_address == null) {
            return null;
        }
        return new JsonObject(Long.parseLong(_address));
    }

    public List<JsonObject> getArrJsonObject(String name) {
        synchronized (Json.class) {
            long[] _addresses = _getArray(name, true, _address);
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

    protected List<JsonObject> _getArrJsonObject(String name, long address) {
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

    protected JsonObject _getObject(String name, long address) {
        String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address);
        if (_address == null) {
            return null;
        }
        return new JsonObject(Long.parseLong(_address));
    }
    
    public enum Json_Type {
        STRING(0),
        INTEGER(1),
        DOUBLE(2),
        LONG(3),
        BOOLEAN(4),
        OBJECT(5);

        int id;
        Json_Type(int id) {
            this.id = id;
        }
    }

    protected enum JSON_GET_METHOD {
        DEFAULT(0),
        NAME_ADDRESS(1),
        ADDRESS(2);

        int id;
        JSON_GET_METHOD(int id) {
            this.id = id;
        }
    }

    protected String getType(long address) {
        switch (_getType(address)) {
            case 0: {
                return "NONE";
            }

            case 2: {
                return "NULL";
            }

            case 3: {
                return "BOOL";
            }

            case 4: {
                return "NUM";
            }

            case 5: {
                return "STR";
            }

            case 6: {
                return "ARR";
            }

            case 7: {
                return "OBJ";
            }

            default: {
                return "null";
            }
        }
    }

    public void printType() {
        System.out.println("address-type: " + getType(rootAddress));
        System.out.println("pointAddress-type: " + getType(pointAddress));
    }

    public class JsonObject {
        private long address;
        private long pointerAddress;

        public JsonObject(long address) {
            synchronized (JsonObject.class) {
                this.address = address;
                this.pointerAddress = address;
            }
        }

        public JsonObject getPoint(String point) {
            synchronized (JsonObject.class) {
                pointerAddress = _getPoint(address, point);
                return this;
            }
        }

        public JsonObject getPointerObject(String name) {
            synchronized (JsonObject.class) {
                String _address = (String) _get(Json_Type.OBJECT.id, name, JSON_GET_METHOD.NAME_ADDRESS.id, address);
                pointerAddress = Long.parseLong(_address);
                return this;
            }
        }

        public String asPointerString() {
            return _asString(pointerAddress);
        }

        public int asPointerInt() {
            return _asInt(pointerAddress);
        }

        public double asPointerDouble() {
            return _asDouble(pointerAddress);
        }

        public long asPointerLong() {
            return _asLong(pointerAddress);
        }

        public boolean asPointerBoolean() {
            return _asBoolean(pointerAddress);
        }

        public String getPointString(String name) {
            return _getString(name, pointerAddress);
        }

        public String getString(String name) {
            return _getString(name, address);
        }

        public String asString() {
            return _asString(address);
        }

        public int getInt(String name) {
            return _getInt(name, address);
        }

        public int asInt() {
            return _asInt(address);
        }

        public double getDouble(String name) {
            return _getDouble(name, address);
        }

        public double asDouble() {
            return _asDouble(address);
        }

        public long getLong(String name) {
            return _getLong(name, address);
        }

        public long asLong() {
            return _asLong(address);
        }

        public boolean getBoolean(String name) {
            return _getBoolean(name, address);
        }

        public boolean asBoolean() {
            return _asBoolean(address);
        }

        public JsonObject getObject(String name) {
            return _getObject(name, address);
        }

        public List<JsonObject> asPointArrJsonObject() {
            return _asPointArrJsonObject(pointerAddress);
        }

        public List<JsonObject> getArrJsonObject(String name) {
            return _getArrJsonObject(name, address);
        }

    }

    private native String _create(String jsonStr);
    private native Object _get(int type, String name, int isRoot, long address);
    private native long[] _getArray(String name, boolean isRoot, long address);
    private native long _getPoint(long address, String point);
    private native void _close(long address);
    private native int _getType(long address);
}